package com.example.bot;

import com.example.service.BotSendMessageService;
import com.example.service.UserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class CallbackHandler {
    private final UserService userService;
    private final BotSendMessageService messageSender;

    public CallbackHandler(UserService userService, BotSendMessageService messageSender) {
        this.userService = userService;
        this.messageSender = messageSender;
    }

    public void handle(Update update) {
        String callbackData = update.getCallbackQuery().getData();
        String chatId = update.getCallbackQuery().getMessage().getChatId().toString();
        Long telegramId = update.getCallbackQuery().getFrom().getId();

        if (callbackData.equals("YES")) {
            handleDeleteYes(chatId, telegramId);
        } else if (callbackData.equals("NO")) {
            handleDeleteNo(chatId);
        }
    }

    private void handleDeleteYes(String chatId, Long telegramId){
        userService.deleteUserByTelegramId(telegramId);
        String deletedAccountNotificationMessage = "Ваш аккаунт удалён... чтобы создать новый, введите /register nickname";
        messageSender.sendMessage(chatId, deletedAccountNotificationMessage);
    }

    private void handleDeleteNo(String chatId){
        String accountDeleteCanceled = "Удаление аккаунта отменено.";
        messageSender.sendMessage(chatId, accountDeleteCanceled);
    }
}