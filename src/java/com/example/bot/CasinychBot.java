package com.example.bot;

import com.example.config.CasinychBotConfig;
import com.example.service.SendBotMessageServiceImpl;
import com.example.command.CommandMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.logging.Logger;

@Component
public class CasinychBot extends TelegramLongPollingBot {
    private final CasinychBotConfig botConfig;
    public static String COMMAND_PREFIX = "/";
    private final CommandMap commandMap;

    @Autowired
    public CasinychBot(CasinychBotConfig botConfig){
        this.botConfig = botConfig;
        commandMap = new CommandMap(new SendBotMessageServiceImpl(this));
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
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            if (message.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = message.split(" ")[0].toLowerCase();
                System.out.println("This work");
                commandMap.retrieveCommand(commandIdentifier).execute(update);
            }
            else {
                commandMap.retrieveCommand(" NO").execute(update);
            }
        }
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }
}