package com.example.command;

import com.example.service.BotSendMessageService;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Data
public class StartCommand implements Command {

    private final String START_MESSAGE =
            "       === Команды ===\n" +
                    "   /register nickname - регистрация: вызываешь и вторым словом вводишь никнейм (пока что пробелы не поддерживаются)\n" +
                    "   /statistics — вывод статы по депам, выигрышам и доступным деньгам на счете и полный капитал\n" +
                    "   /rating — рейтинг всех пользователей с выигрышами\n" +
                    "   /dep <money> <slots_amount> — занести деньги в казик из своего капитала и крутануть слот. money" +
                    " - деньги с твоего капитала, slots_amount - количество слотов, которые хочешь крутануть (не больше 30)\n" +
                    "   /delete - удалить свой аккаунт\n" +
                    "   /rules - правила игры\n" +
                    "\n" +
                    "приятного пользования! и помни, 99% лудоманов сдаются перед выигрышем ;)";

    @Override
    public void execute(Update update, BotSendMessageService messageSender) {
        String chatId = update.getMessage().getChatId().toString();
        messageSender.sendMessage(chatId, START_MESSAGE);
    }
}
