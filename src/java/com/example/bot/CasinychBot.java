package com.example.bot;

import com.example.command.Command;
import com.example.config.CasinychBotConfig;
import com.example.command.CommandMap;
import com.example.service.SendBotMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import com.example.command.TelegramMessageSender;

import java.util.List;
import java.util.logging.Logger;

@Component
public class CasinychBot extends TelegramLongPollingBot {
    private final CasinychBotConfig botConfig;
    private final CommandMap commandMap;
    private final TelegramMessageSender messageSender;  // Добавляем
    private final Logger logger = Logger.getLogger("casynychbot_logger");

    @Autowired
    public CasinychBot(CasinychBotConfig botConfig,
                       CommandMap commandMap,
                       TelegramMessageSender messageSender
                       ){

        this.botConfig = botConfig;
        this.commandMap = commandMap;
        this.messageSender = messageSender;
        this.messageSender.setBot(this);
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken(){
        return botConfig.getToken();
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

    // прием сообщений
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();

            if (message.startsWith("/")) {
                String commandIdentifier = message.split(" ")[0].toLowerCase();
                logger.info("got command: " + message);

                Command command = commandMap.retrieveCommand(commandIdentifier);
                if (command != null) {
                    command.execute(update, messageSender);
                } else {
                    logger.warning("unknown command: " + commandIdentifier);
                }
            }
        }
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }
}