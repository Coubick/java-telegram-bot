package com.example.user_dao;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Table(name = "users")
@NoArgsConstructor
public class User {
    @Column (name = "user_id")
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long userId;

    @Column (name = "nickname", nullable = false)
    private String nickname;

    @Column (name = "capital")
    private Integer capital;

    @Column (name = "last_salary_date")
    private Date lastSalaryDate;

    @Column (name = "spins_available")
    private Integer spinsAvailable;

    @Column (name = "telegram_id", unique = true, nullable = false)
    private Long telegramId;

    public User(String nickname, Integer capital, Date lastSalaryDate, Integer spinsAvailable, Long telegramId) {
        this.nickname = nickname;
        this.capital = capital;
        this.lastSalaryDate = lastSalaryDate;
        this.spinsAvailable = spinsAvailable;
        this.telegramId = telegramId;
    }


    // TODO: возможно, надо будет добавить currentWin для отслеживания текущего выигрыша
}
