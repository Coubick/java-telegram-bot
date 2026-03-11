package com.example.service;

import com.example.game_session_dao.GameSession;
import com.example.slot.SlotWinCalculator;
import com.example.user_dao.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DepositService {
    private final UserService userService;
    private final GameSessionService gameSessionService;
    private final SlotWinCalculator winCalculator;

    @Autowired
    public DepositService(UserService userService, GameSessionService gameSessionService, SlotWinCalculator winCalculator) {
        this.userService = userService;
        this.gameSessionService = gameSessionService;
        this.winCalculator = winCalculator;
    }

    // логика при ответе на /dep
    public String addMoneyAndSpinsToSession(Double depMoney, Integer spinsAmount, Update update) {
        Long telegramId = update.getMessage().getFrom().getId();
        Optional<User> userOptional = userService.findByTelegramId(telegramId);

        if (userOptional.isPresent()) {
            if (depMoney <= 0 || spinsAmount <= 0) {
                return "Ты ввёл неверное количество денег или спинов. Их количества могут быть только положительными.";
            }

            User user = userOptional.get();
            double usersCapital = Math.round(user.getCapital() * 100.0) / 100.0; // чтобы было 2 знака после запятой

            if (usersCapital < depMoney) {
                return "Опааа, а денег то у тебя не хватает на такой деп :). У тебя всего лишь: " + usersCapital;
            }

            if (spinsAmount > 30) {
                return "Максимальное количество спинов: 30.";
            }

            double newCapital = usersCapital - depMoney;
            user.setCapital(newCapital);
            userService.updateUser(user);

            Optional<GameSession> existingSessionOpt = gameSessionService.findByTelegramId(telegramId);
            GameSession gameSession;
            if (existingSessionOpt.isPresent()) {
                // если сессия существует - обновляем
                gameSession = existingSessionOpt.get();

                // Важно: проверка, не закончилась ли предыдущая сессия
                if (gameSession.getSpinsAvailable() == 0) {
                    // если предыдущая сессия закончилась, создаётся новая
                    gameSession = new GameSession(user, spinsAmount, 0.0);
                    gameSession.setTelegramId(telegramId);
                    gameSession.setTotalSpins(spinsAmount);
                    gameSession.setDepositAmount(depMoney);
                } else {
                    // добавляем к существующей сессии
                    gameSession.setSpinsAvailable(gameSession.getSpinsAvailable() + spinsAmount);
                    gameSession.setTotalSpins(gameSession.getTotalSpins() + spinsAmount);
                    gameSession.setDepositAmount(gameSession.getDepositAmount() + depMoney);
                }
            } else {
                // Создаем новую сессию
                gameSession = new GameSession(user, spinsAmount, 0.0);
                gameSession.setTelegramId(telegramId);
                gameSession.setTotalSpins(spinsAmount);
                gameSession.setDepositAmount(depMoney);
            }

            // 3. Сохраняем сессию
            gameSessionService.saveGameSession(gameSession);

            // 4. Получаем актуальные данные для ответа
            int currentSpinsAvailable = gameSession.getSpinsAvailable();


            return "Отлично, ты депнул " + String.format("%.2f", depMoney) + " и взял " + spinsAmount + " спинов.\n" +
                    "Стоимость одного спина: " + String.format("%.2f", depMoney / spinsAmount) + "\n" +
                    "Твой текущий баланс: " + String.format("%.2f", newCapital) + "\n" +
                    "Спинов доступно: " + currentSpinsAvailable;
        }

        return "Похоже, у тебя нет аккаунта. Введи /register nickname, где nickname - " +
                "твоё игровое имя, чтобы зарегистрироваться.";
    }

    /**
     * Обработка результата прокрута слота
     * @param telegramId ID пользователя в Telegram
     * @param symbols выпавшие символы
     * @return сообщение с результатом
     */
    public String processSpinResult(Long telegramId, List<String> symbols) {
        Optional<GameSession> sessionOpt = gameSessionService.findByTelegramId(telegramId);

        GameSession session = sessionOpt.get();

        if (session.getSpinsAvailable() <= 0) {
            return "У тебя закончились прокруты! Сделай новый депозит.";
        }

        // Получаем информацию о текущем спине
        int totalSpins = session.getTotalSpins();
        int spinsUsed = session.getTotalSpins() - session.getSpinsAvailable();
        int spinNumber = spinsUsed + 1;

        double betAmount = session.getCurrentBet();
        boolean isFullBudget = (totalSpins == 1 && spinNumber == 1);

        SlotWinCalculator.SpinResult result = winCalculator.calculateWin(
                symbols, spinNumber, totalSpins, betAmount, isFullBudget
        );

        double newCurrentWin = session.getCurrentSessionWin() + result.getProfit();
        session.setCurrentSessionWin(newCurrentWin);
        session.setSpinsAvailable(session.getSpinsAvailable() - 1);

        gameSessionService.addGameSession(session);

        StringBuilder response = new StringBuilder();
        response.append(result.formatMessage()).append("\n\n");

        response.append(String.format("💰 Текущий выигрыш: %.2f\n", newCurrentWin));
        response.append(String.format("🎲 Осталось прокрутов: %d", session.getSpinsAvailable()));

        if (session.getSpinsAvailable() == 0) {
            double totalProfit = session.getCurrentSessionWin();
            response.append(String.format("\n\n🎮 Игра окончена! Итоговый выигрыш: %+.2f", totalProfit));

            returnMoneyToCapital(telegramId, session);
            try {
                gameSessionService.delete(session);
                System.out.println("Сессия успешно удалена для пользователя " + telegramId);
            } catch (Exception e) {
                System.err.println("Ошибка при удалении сессии: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return response.toString();
    }

    /**
     * Возврат выигрыша на основной счет после окончания игры
     */
    private void returnMoneyToCapital(Long telegramId, GameSession session) {
        userService.findByTelegramId(telegramId).ifPresent(user -> {
            double newCapital = session.getCurrentSessionWin() + user.getCapital() + session.getDepositAmount();
            user.setCapital(newCapital);
            userService.updateUser(user);
        });
    }
}