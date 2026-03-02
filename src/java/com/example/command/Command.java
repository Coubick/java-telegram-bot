package com.example.command;

import com.example.service.BotSendMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command {
    void execute(Update update, BotSendMessageService messageSender);
}