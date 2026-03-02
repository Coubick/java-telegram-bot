package com.example.user_dao;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class User {
    @Column
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

    // TODO: возможно, надо будет добавить currentWin для отслеживания текущего выигрыша
}
