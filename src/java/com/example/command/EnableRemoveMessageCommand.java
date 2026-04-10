package com.example.command;

import com.example.service.BotSendMessageService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class EnableRemoveMessageCommand implements Command {

    @Override
    public void execute(Update update, BotSendMessageService messageSender) {
        String chatId = update.getMessage().getChatId().toString();
        String messageText = "Удаление сообщений включено";

        messageSender.sendMessage(chatId, messageText);
    }
}
