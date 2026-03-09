package com.example.slot;

import lombok.Data;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class SlotWinCalculator {

    /**
     * Рассчитывает выигрыш для одного прокрута
     * @param symbols выпавшие символы
     * @param spinNumber номер прокрута (начинается с 1)
     * @param totalSpins общее количество спинов в сессии
     * @param betAmount ставка на этот спин
     * @param isFullBudget флаг, что это вся сумма депозита (1 спин)
     * @return результат прокрута
     */
    public SpinResult calculateWin(List<String> symbols, int spinNumber, int totalSpins,
                                   double betAmount, boolean isFullBudget) {

        String s1 = symbols.get(0);
        String s2 = symbols.get(1);
        String s3 = symbols.get(2);

        double multiplier = getMultiplier(s1, s2, s3, spinNumber, totalSpins, isFullBudget);
        double winAmount = betAmount * multiplier;
        double profit = winAmount - betAmount;

        return new SpinResult(symbols, multiplier, winAmount, profit);
    }

    private double getMultiplier(String s1, String s2, String s3, int spinNumber,
                                 int totalSpins, boolean isFullBudget) {

        boolean isFirstSpin = (spinNumber == 1);
        boolean isSmallSession = totalSpins < 6;

        if (s1.equals(s2) && s2.equals(s3)) {
            return getTripleMultiplier(s1, totalSpins, isFirstSpin && isFullBudget);
        }

        if (s1.equals(s3)) {
            return getMirrorMultiplier(s1, isSmallSession);
        }

        if (s1.equals(s2) || s2.equals(s3)) {
            String pairSymbol = s1.equals(s2) ? s1 : s2;
            return getPairMultiplier(pairSymbol, isSmallSession);
        }

        return getDifferentMultiplier(s1, isFirstSpin && isFullBudget);
    }

    private double getTripleMultiplier(String symbol, int totalSpins, boolean isFullBudgetFirstSpin) {
        if (isFullBudgetFirstSpin) {
            switch (symbol) {
                case "7️⃣": return 1000.0;
                case "BAR": return 100.0;
                case "🍋": return 50.0;
                case "🍇": return 30.0;
            }
        }

        switch (symbol) {
            case "7️⃣":
                if (totalSpins == 2) return 50.0;
                if (totalSpins <= 5) return 10.0;
                if (totalSpins <= 10) return 5.0;
                return 2.0;

            case "BAR":
                if (totalSpins <= 5) return 3.0;
                return 1.7;

            case "🍋":
                if (totalSpins <= 5) return 2.0;
                return 1.5;

            case "🍇":
                if (totalSpins <= 5) return 1.5;
                return 1.2;

            default: return 1.0;
        }
    }

    private double getMirrorMultiplier(String symbol, boolean isSmallSession) {
        switch (symbol) {
            case "7️⃣": return isSmallSession ? 1.5 : 1.2;
            case "BAR": return isSmallSession ? 1.4 : 1.15;
            case "🍋": return isSmallSession ? 1.3 : 1.1;
            case "🍇": return isSmallSession ? 1.2 : 1.05;
            default: return 1.0;
        }
    }

    private double getPairMultiplier(String symbol, boolean isSmallSession) {
        switch (symbol) {
            case "7️⃣": return isSmallSession ? 1.45 : 1.15;
            case "BAR": return isSmallSession ? 1.15 : 1.05;
            case "🍋": return isSmallSession ? 1.05 : 1.0;
            case "🍇": return isSmallSession ? 1.0 : 0.98;
            default: return 1.0;
        }
    }

    private double getDifferentMultiplier(String firstSymbol, boolean isFullBudgetFirstSpin) {
        if (isFullBudgetFirstSpin) {
            switch (firstSymbol) {
                case "7️⃣": return 0.7;
                case "BAR": return 0.5;
                case "🍋": return 0.4;
                case "🍇": return 0.35;
            }
        }

        switch (firstSymbol) {
            case "7️⃣": return 0.9;
            case "BAR": return 0.8;
            case "🍋": return 0.7;
            case "🍇": return 0.65;
            default: return 1.0;
        }
    }

    @Data
    public static class SpinResult {
        private final List<String> symbols;
        private final double multiplier;
        private final double winAmount;
        private final double profit;

        public String formatMessage() {
            return String.format(
                    "🎰 %s %s %s\n" +
                            "Множитель: x%.2f\n" +
                            "Выигрыш: %+.2f",
                    symbols.get(0), symbols.get(1), symbols.get(2),
                    multiplier, profit
            );
        }
    }
}