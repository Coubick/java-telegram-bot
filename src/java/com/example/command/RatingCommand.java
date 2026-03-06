package com.example.command;

import com.example.service.BotSendMessageService;
import com.example.service.UserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;


@Component
public class RatingCommand implements Command{
    private final UserService userService;

    public RatingCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(Update update, BotSendMessageService messageSender) {
        // Вывод в формате 1. Nickname: общий_капитал
        String chatId = update.getMessage().getChatId().toString();
        String topUsers = userService.findTop10ByOrderByCapitalDescInString();
        messageSender.sendMessage(chatId, topUsers);
    }
}
