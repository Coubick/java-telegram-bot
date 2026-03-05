package com.example.command;

import com.example.service.BotSendMessageService;
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
                    "   /register nickname - регистрация, надо вызвать и вторым словом ввести никнейм (пока что пробелы не поддерживаются)\n" +
                    "   /statistics — вывод статы по депам, выигрышам и доступным деньгам на счете и полный капитал\n" +
                    "   /rating — рейтинг всех пользователей с выигрышами\n" +
                    "   /dep <money> <slots amount> — занести деньги в казик из своего капитала и крутануть слот. money" +
                    " - деньги с твоего капитала, slots amount - количество слотов, которые хочешь крутануть (не больше 30)\n" +
                    "   /rules - правила игры`\n" +
                    "\n" +
                    "приятного пользования! и помни, 99% лудоманов сдаются перед выигрышем ;)";

    @Override
    public void execute(Update update, BotSendMessageService messageSender) {
        String chatId = update.getMessage().getChatId().toString();
        messageSender.sendMessage(chatId, START_MESSAGE);
    }
}
