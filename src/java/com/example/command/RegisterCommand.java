package com.example.command;

import com.example.service.BotSendMessageService;
import com.example.user_dao.User;
import com.example.user_dao.UserRepository;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.sql.Timestamp;
import java.util.Date;

@Component
public class RegisterCommand implements Command {
    private final UserRepository userRepository;

    public RegisterCommand(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void execute(Update update, BotSendMessageService messageSender) {
        //TODO: сделать логику входа (/register Nickname) - распарсить ник
        Long telegramId = update.getMessage().getFrom().getId();
        String chatId = update.getMessage().getChatId().toString();

        if (userRepository.existsByTelegramId(telegramId)) {
            String registerRejectedMessage = "Ты уже зарегистрирован";
            messageSender.sendMessage(chatId, registerRejectedMessage);

        } else {
            try {
                String nickname = update.getMessage().getText().split(" ")[1];
                if (userRepository.existsByNickname(nickname)) {
                    String registerRejectedMessage = "Такой никнейм занят";
                    messageSender.sendMessage(chatId, registerRejectedMessage);
                } else {
                    Timestamp stamp = new Timestamp(System.currentTimeMillis());
                    Date last_salary_date = new Date(stamp.getTime());

                    User newUser = new User(
                            nickname,
                            0,
                            last_salary_date,
                            0,
                            telegramId);

                    userRepository.save(newUser);
                    String successfullyRegisteredMessage = "Поздравляю, ты прошёл регистрацию";
                    messageSender.sendMessage(chatId, successfullyRegisteredMessage);
                }
            } catch (Exception ArrayIndexOutOfBoundsException) {
                String nicknameIsNotEnteredMessage = "Ты не ввёл ник, повтори команду /register nickname, " +
                        "где nickname - твоё игровое имя";
                messageSender.sendMessage(chatId, nicknameIsNotEnteredMessage);
            }
        }
    }
}