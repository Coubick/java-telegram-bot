package com.example.command;

import com.example.service.BotSendMessageService;
import com.example.service.DepositService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class DepCommand implements Command {
    private final DepositService depositService;

    public DepCommand(DepositService depositService) {
        this.depositService = depositService;
    }

    @Override
    public void execute(Update update, BotSendMessageService messageSender) {
        //TODO: логика добавления денег на баланс и ожидания ботом депа
        String chatId = update.getMessage().getChatId().toString();
        try {
            double moneyToDep = Double.parseDouble(update.getMessage().getText().split(" ")[1]);
            int spins = Integer.parseInt(update.getMessage().getText().split(" ")[2]);

            String depResult = depositService.addMoneyAndSpinsToSession(moneyToDep, spins, update);
            messageSender.sendMessage(chatId, depResult);
        } catch (Exception ArrayIndexOutOfBoundsException) {
            String notAllParametersEntered = "Ты не ввёл все значения правильно. Нужно вводить в таком формате: \n" +
                    "/dep 1000 10, где 1000 - деньги, которые хочешь поставить, 10 - количество прокрутов.";

            messageSender.sendMessage(chatId, notAllParametersEntered);
        }

    }
}