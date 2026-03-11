package com.example.command;

import com.example.service.BotSendMessageService;
import com.example.service.UserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class StatisticsCommand implements Command{
    private final UserService userService;

    public StatisticsCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(Update update, BotSendMessageService messageSender) {
        Long telegramId = update.getMessage().getFrom().getId();
        String chatId = update.getMessage().getChatId().toString();
        String response = userService.collectStats(telegramId);

        messageSender.sendMessage(chatId, response);
    }
}
