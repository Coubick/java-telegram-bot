package com.example.slot;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class SlotDecoder {

    private static final String[] VALUES = {"BAR", "🍇", "🍋", "7️⃣"};

    /**
     * Преобразует числовое значение дайса в массив эмодзи
     * @param diceValue значение от 1 до 64 (как в Telegram)
     * @return список из 3 эмодзи
     */
    public List<String> decodeSlot(int diceValue) {
        int value = diceValue - 1;
        List<String> result = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            result.add(VALUES[value % 4]);
            value /= 4;
        }

        return result;
    }
}