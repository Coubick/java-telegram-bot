package com.example.service;

import com.example.bot.CasinychBot;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Component
public class BotSendMessageService {
    private final DeleteMessageService deleteMessageService;
    @Setter
    private CasinychBot bot;

    public BotSendMessageService(DeleteMessageService deleteMessageService) {
        this.deleteMessageService = deleteMessageService;
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

    public void sendMessage(String chatId, String text, InlineKeyboardMarkup inlineKeyboardMarkup) {
        if (bot == null) {
            throw new IllegalStateException("Bot not initialized yet");
        }

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        message.setReplyMarkup(inlineKeyboardMarkup);

        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageAndDelete(String chatId, String text, Integer slotId) {
        if (bot == null) {
            throw new IllegalStateException("Bot not initialized yet");
        }

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        try {
            Message msg = bot.execute(message);

            deleteMessageService.scheduleDeletion(chatId, bot, msg.getMessageId(), slotId);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}