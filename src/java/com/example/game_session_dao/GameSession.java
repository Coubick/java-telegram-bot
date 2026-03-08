package com.example.game_session_dao;

import com.example.user_dao.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private User user;

    @Column(name = "telegram_id", unique = true, nullable = false)
    private Long telegramId;

    @Column(name = "spins_available")
    private Integer spinsAvailable = 0;

    @Column(name = "current_win")
    private Double currentSessionWin = 0.0;

    public GameSession(User user, int spinsAvailable, double currentWin) {
        this.user = user;
        this.telegramId = user.getTelegramId();
        this.spinsAvailable = spinsAvailable;
        this.currentSessionWin = currentWin;
    }
}