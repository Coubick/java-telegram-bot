package com.example.command;

import com.example.service.BotSendMessageService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class HelpCommand implements Command{
    private final String HELP_MESSAGE = "пока помощи не будэ";
    @Override
    public void execute(Update update, BotSendMessageService messageSender) {
        messageSender.sendMessage(update.getMessage().getChatId().toString(), HELP_MESSAGE);
    }
}
