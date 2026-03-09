package com.example.bot;

import com.example.command.Command;
import com.example.config.CasinychBotConfig;
import com.example.command.CommandMap;
import com.example.service.DepositService;
import com.example.slot.SlotDecoder;
import com.example.slot.SlotWinCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Dice;
import org.telegram.telegrambots.meta.api.objects.Update;
import com.example.service.BotSendMessageService;

import java.util.List;
import java.util.logging.Logger;

@Component
public class CasinychBot extends TelegramLongPollingBot {
    private final CasinychBotConfig botConfig;
    private final CommandMap commandMap;
    private final BotSendMessageService messageSender;
    private final CallbackHandler callbackHandler;
    private final SlotDecoder slotDecoder;
    private final DepositService depositService;
    private final SlotWinCalculator winCalculator;
    private final Logger logger = Logger.getLogger("casynychbot_logger");

    @Autowired
    public CasinychBot(CasinychBotConfig botConfig,
                       CommandMap commandMap,
                       BotSendMessageService messageSender, CallbackHandler callbackHandler, SlotDecoder slotDecoder, DepositService depositService, SlotWinCalculator winCalculator
    ){

        this.botConfig = botConfig;
        this.commandMap = commandMap;
        this.messageSender = messageSender;
        this.callbackHandler = callbackHandler;
        this.slotDecoder = slotDecoder;
        this.depositService = depositService;
        this.winCalculator = winCalculator;
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
        if (update.hasCallbackQuery()) {
            logger.info("Got callback: " + update.getCallbackQuery().getData());
            callbackHandler.handle(update);
            return;
        }

        if (update.hasMessage()) {
            if (update.getMessage().hasDice()) {
                Dice dice = update.getMessage().getDice();
                if ("🎰".equals(dice.getEmoji())) {
                    handleSlotSpin(update, dice);
                    return;
                }
            }

            if (update.getMessage().hasText()) {
                String messageText = update.getMessage().getText().trim();

                if (messageText.startsWith("/")) {
                    String commandIdentifier = messageText.split(" ")[0].toLowerCase();
                    logger.info("got command: " + messageText);

                    Command command = commandMap.retrieveCommand(commandIdentifier);
                    if (command != null) {
                        command.execute(update, messageSender);
                    } else {
                        logger.warning("unknown command: " + commandIdentifier);
                    }
                }
            }
        }
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

    private void handleSlotSpin(Update update, Dice dice) {
        Long telegramId = update.getMessage().getFrom().getId();
        String chatId = update.getMessage().getChatId().toString();

        // Расшифровываем результат слота
        List<String> symbols = slotDecoder.decodeSlot(dice.getValue());
        logger.info("Slot spin: " + symbols + " (value: " + dice.getValue() + ")");

        // Передаем в DepositService для расчета выигрыша
        String result = depositService.processSpinResult(telegramId, symbols);
        messageSender.sendMessage(chatId, result);
    }
}