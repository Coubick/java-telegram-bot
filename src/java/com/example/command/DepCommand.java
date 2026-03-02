package com.example.command;

import com.example.service.BotSendMessageService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class DepCommand implements Command{

    @Override
    public void execute(Update update, BotSendMessageService messageSender) {
        //TODO: логика добавления денег на баланс
    }
}
