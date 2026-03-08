package com.example.service;

import com.example.game_session_dao.GameSession;
import com.example.user_dao.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Service
public class DepositService {
    private final UserService userService;
    private final GameSessionService gameSessionService;
    private User user;
    private Optional<GameSession> usersGameSessionOptional;
    private GameSession usersGameSession;

    @Autowired
    public DepositService(UserService userService, GameSessionService gameSessionService) {
        this.userService = userService;
        this.gameSessionService = gameSessionService;
    }

    // логика при ответе на /dep
    public String addMoneyAndSpinsToSession(Double depMoney, Integer spinsAmount, Update update) {
        Long telegramId = update.getMessage().getForwardFrom().getId();
        Optional<User> userOptional = userService.findByTelegramId(telegramId);

        if (userOptional.isPresent()) {
            if (depMoney <= 0 || spinsAmount <= 0) {
                return "Ты ввёл неверное количество денег или спинов. Их количества могут быть только положительными.";
            }

            user = userOptional.get();
            Double usersCapital = user.getCapital();
            if (usersCapital < depMoney) {
                return "Опааа, а денег то у тебя не хватает на такой деп:). У тебя всего лишь: " + usersCapital;
            }

            if (spinsAmount > 30) {
                return "Максимальное количество спинов: 30.";
            }

            Double oneSpinPrice = depMoney / spinsAmount;
            double newCapital = usersCapital - depMoney;
            int spinsAvailableUpdated = gameSessionService.getSpinsAvailable(update) + spinsAmount;

            user.setCapital(newCapital);
            userService.updateUser(user);

            usersGameSessionOptional = gameSessionService.findByTelegramId(user.getUserId());

            if (usersGameSessionOptional.isPresent()) { // если найдена игровая сессия, то добавить спины
                usersGameSession = usersGameSessionOptional.get();
                usersGameSession.setSpinsAvailable(spinsAvailableUpdated);
                gameSessionService.addGameSession(usersGameSession);

            } else { // создать новую сессию, если пользователь начинает новую игру
                GameSession newGameSession = new GameSession(user, spinsAvailableUpdated, 0d);
                gameSessionService.addGameSession(newGameSession);
            }

            return "Отлично, ты депнул " + depMoney + " и взял" + spinsAmount + " спинов.\n" +
                    "Стоимость одного спина: " + String.format("%.2f", oneSpinPrice) + "\n" +
                    "Твой текущий баланс: " + newCapital + "\n" +
                    "Спинов доступно: " + spinsAvailableUpdated;
        }

        return "Похоже, у тебя нет аккаунта. Введи /register nickname, где nickname - " +
                "твоё игровое имя, чтобы зарегистрироваться.";
    }

    // TODO "ответ" на значок слотов
    private void winCalculation(Double winFromSlot) {
        // TODO winFromSlot - надо реализовать расчет на основе выпавшего слота. Может быть как + так и -
        // TODO в CasinychBot - принимается слот, далее возвращается [] с выпавшими картинками

        Double currentSessionWinUpdated = usersGameSession.getCurrentSessionWin() + winFromSlot;
        Integer spinsAvailableUpdated = usersGameSession.getSpinsAvailable() - 1;

        usersGameSession.setSpinsAvailable(spinsAvailableUpdated);
        usersGameSession.setCurrentSessionWin(currentSessionWinUpdated);

        gameSessionService.addGameSession(usersGameSession);
    }
}