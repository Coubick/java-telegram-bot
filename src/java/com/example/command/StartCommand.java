package com.example.command;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Data
public class StartCommand implements Command {

    private final String START_MESSAGE =
            "       ===Общая инфа===\n" +
                    "1) у тебя есть капитал - все твои деньги, накопленные с ЗП\n" +
                    "2) есть счёт в казино, на который ты депаешь деньги из зп и играешь. нет денег в капитале, но депнуть хочется? " +
                    "тогда задонать админу или жди следующего дня, т.к. твоя ЗП приходит раз в сутки. (система" +
                    " 'взять в долг' пока не реализована)\n" +
                    "3) какие команды доступны:\n" +
                    "   /entrance - регистрация, надо вызвать, получить никнейм и начать игру (если никнейм не понравится, можно переделать)\n" +
                    "   /regenerate - создать новый никнейм\n" +
                    "   /my_stat — вывод статы по депам, выигрышам и доступным деньгам на счете и полный капитал\n" +
                    "   /rating — рейтинг всех пользователей с выигрышами\n" +
                    "   /dep — занести деньги в казик из своего капитала и крутануть слот\n" +
                    "   /mechanics - правила игры (`когда захочешь рискнуть своими накоплениями, введи /dep `\n" +
                    "\n" +
                    "приятного пользования! и помни, 99% лудоманов сдаются перед выигрышем ;)";

    @Override
    public void execute(Update update, TelegramMessageSender messageSender) {
        String chatId = update.getMessage().getChatId().toString();
        messageSender.sendMessage(chatId, START_MESSAGE);
    }
}
