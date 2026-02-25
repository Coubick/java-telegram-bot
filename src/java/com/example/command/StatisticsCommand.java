package com.example.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class StatisticsCommand implements Command{
    @Override
    public void execute(Update update, TelegramMessageSender messageSender) {
        //        // TODO: сделать реализацию запроса в БД и сбор статистики
    }
}
