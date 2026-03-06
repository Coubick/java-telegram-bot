package com.example.command;

import com.example.service.BotSendMessageService;
import com.example.user_dao.User;
import com.example.user_dao.UserRepository;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;


@Component
public class RatingCommand implements Command{
    private final UserRepository userRepository;

    public RatingCommand(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void execute(Update update, BotSendMessageService messageSender) {
        // Вывод в формате 1. Nickname: общий_капитал
        String chatId = update.getMessage().getChatId().toString();
        List<User> topUsers = userRepository.findTop10ByOrderByCapitalDesc();
        if (topUsers.size() > 0) {
            StringBuilder usersString = new StringBuilder();
            for (int i = 0; i < topUsers.size(); i++) {
                User currentUser = topUsers.get(i);
                usersString
                        .append(i + 1)
                        .append(". ")
                        .append(currentUser.getNickname())
                        .append(": ").append(currentUser.getCapital())
                        .append("\n");
            }

            String messageText = "\uD83C\uDFC6 Лучшие деперы \uD83C\uDFC6\n" + usersString;
            messageSender.sendMessage(chatId, messageText);
        } else {
            String messageText = "Деперы не найдены";
            messageSender.sendMessage(chatId, messageText);
        }
    }
}
