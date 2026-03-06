package com.example.command;

import com.example.service.BotSendMessageService;
import com.example.service.UserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class DeleteAccountCommand implements Command{
    private final UserService userService;

    public DeleteAccountCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(Update update, BotSendMessageService messageSender) {
        String chatId = update.getMessage().getChatId().toString();
        Long telegramId = update.getMessage().getFrom().getId();
        if (userService.findByTelegramId(telegramId).isPresent()) {
            String deleteAccountMessage = "Точно хочешь удалить аккаунт? При удалении сгорят все твои деньги!";
            InlineKeyboardMarkup inlineKeyboard = createInlineKeyboard();
            messageSender.sendMessage(chatId, deleteAccountMessage, inlineKeyboard);
        } else {
            String notFoundAccountMessage = "У тебя нет аккаунта";
            messageSender.sendMessage(chatId, notFoundAccountMessage);
        }
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