package com.example.command;

import com.example.service.BotSendMessageService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TurnOffRemoveMessageCommand implements Command{

    @Override
    public void execute(Update update, BotSendMessageService messageSender) {
        String chatId = update.getMessage().getChatId().toString();
        String messageText = "Удаление сообщений выключено";

        messageSender.sendMessage(chatId, messageText);
    }
}
