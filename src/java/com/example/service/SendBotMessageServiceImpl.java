//package com.example.service;
//
//import com.example.bot.CasinychBot;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//@Component
//public class SendBotMessageServiceImpl implements SendBotMessageService {
//    private final CasinychBot bot;
//
//    @Autowired
//    public SendBotMessageServiceImpl(CasinychBot bot) {
//        this.bot = bot;
//    }
//
//    @Override
//    public void sendMessage(String chatId, String message) {
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.setChatId(chatId);
//        sendMessage.setText(message);
//
//        try {
//            bot.execute(sendMessage);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//    }
//}