package com.example.command;

import com.example.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class HelpCommand implements Command{
    private final SendBotMessageService sendBotMessageService;

    public HelpCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        String HELP_MESSAGE = "сам себе помоги";
        sendBotMessageService.sendMessage(chatId, HELP_MESSAGE);
    }
}
