package com.example.game_session_dao;

import com.example.user_dao.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "game_session")
@Data
@NoArgsConstructor
public class GameSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    @Column(name = "telegram_id", unique = true, nullable = false)
    private Long telegramId;

    @Column(name = "spins_available")
    private Integer spinsAvailable = 0;

    @Column(name = "current_win")
    private Double currentSessionWin = 0.0;

    @Column(name = "total_spins")
    private Integer totalSpins = 0;

    @Column(name = "deposit_amount")
    private Double depositAmount = 0.0;

    public GameSession(User user, int spinsAvailable, double currentWin) {
        this.user = user;
        this.telegramId = user.getTelegramId();
        this.spinsAvailable = spinsAvailable;
        this.currentSessionWin = currentWin;
    }

    public double getCurrentBet() {
        if (totalSpins == 0) return 0;
        return depositAmount / totalSpins;
    }
}