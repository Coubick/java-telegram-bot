package com.example.command;

import com.example.service.SendBotMessageService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class EntranceCommand implements Command{
    @Override
    public void execute(Update update, TelegramMessageSender messageSender) {
        //TODO: сделать логику входа (/entrance Nickname) - распрасить ник
    }
}
