package com.example.user_dao;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "users")
public class User {
    @Column
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long userId;

    @Column
    private String nickname;

    @Column
    private Integer capital;

    @Column
    private Date lastSalaryDate;

    @Column
    private Integer spinsAvailable;

    @Column
    private String telegramId;

    // TODO: возможно, надо будет добавить currentWin для отслеживания текущего выигрыша
}
