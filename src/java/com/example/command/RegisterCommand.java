package com.example.command;

import com.example.service.BotSendMessageService;
import com.example.service.UserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class RegisterCommand implements Command {
    private final UserService userService;

    public RegisterCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(Update update, BotSendMessageService messageSender) {
        Long telegramId = update.getMessage().getFrom().getId();
        String chatId = update.getMessage().getChatId().toString();

        try {
            String nickname = update.getMessage().getText().split(" ")[1];
            String registrationResult = userService.register(nickname, telegramId);
            messageSender.sendMessage(chatId, registrationResult);
        } catch (Exception ArrayIndexOutOfBoundsException){
            String nicknameIsNotEnteredMessage = "Ты не ввёл ник, повтори команду /register nickname, " +
                    "где nickname - твоё игровое имя";
            messageSender.sendMessage(chatId, nicknameIsNotEnteredMessage);
        }
    }
}