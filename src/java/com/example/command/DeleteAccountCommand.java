package com.example.command;

import com.example.service.BotSendMessageService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class DeleteAccountCommand implements Command{
    @Override
    public void execute(Update update, BotSendMessageService messageSender) {
        String chatId = update.getMessage().getChatId().toString();
        String deleteAccountMessage = "Точно хочешь удалить аккаунт? При удалении сгорят все твои деньги!";
        InlineKeyboardMarkup inlineKeyboard = createInlineKeyboard();

        messageSender.sendMessage(chatId, deleteAccountMessage, inlineKeyboard);
    }

    private InlineKeyboardMarkup createInlineKeyboard(){
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline1 = new ArrayList<>();

        InlineKeyboardButton inlineKeyboardButtonYes = new InlineKeyboardButton();
        inlineKeyboardButtonYes.setText("Да");
        inlineKeyboardButtonYes.setCallbackData("YES");

        InlineKeyboardButton inlineKeyboardButtonNo = new InlineKeyboardButton();
        inlineKeyboardButtonNo.setText("Нет");
        inlineKeyboardButtonNo.setCallbackData("NO");

        rowInline1.add(inlineKeyboardButtonYes);
        rowInline1.add(inlineKeyboardButtonNo);

        rowsInline.add(rowInline1);

        inlineKeyboard.setKeyboard(rowsInline);

        return inlineKeyboard;
    }
}
