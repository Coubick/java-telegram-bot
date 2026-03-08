package com.example.user_dao;
import com.example.game_session_dao.GameSession;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Table(name = "users")
@NoArgsConstructor
public class User {
    @Column (name = "id")
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long userId;

    @Column (name = "nickname", nullable = false)
    private String nickname;

    @Column (name = "capital")
    private Double capital;

    @Column (name = "last_salary_date")
    private Date lastSalaryDate;

    @Column (name = "telegram_id", unique = true, nullable = false)
    private Long telegramId;

    @Column (name = "registration_date")
    private Date registrationDate;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private GameSession gameSession;

    public User(String nickname, Double capital, Date lastSalaryDate, Integer spinsAvailable, Long telegramId) {
        this.nickname = nickname;
        this.capital = capital;
        this.lastSalaryDate = lastSalaryDate;
        this.telegramId = telegramId;
    }
}
