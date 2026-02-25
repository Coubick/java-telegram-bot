package com.example.command;

import com.example.bot.CasinychBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramMessageSender {

    private CasinychBot bot;

    public void setBot(CasinychBot bot) {
        this.bot = bot;
    }

    public void sendMessage(String chatId, String text) {
        if (bot == null) {
            throw new IllegalStateException("Bot not initialized yet");
        }

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}