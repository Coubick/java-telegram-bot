package com.example.service;

import com.example.user_dao.User;
import com.example.user_dao.UserRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(String nickname, Long telegramId) {
        Timestamp stamp = new Timestamp(System.currentTimeMillis());
        Date last_salary_date = new Date(stamp.getTime());
        User newUser = new User(
                nickname,
                0,
                last_salary_date,
                0,
                telegramId);

        userRepository.save(newUser);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public boolean existsByTelegramId(Long telegramId) {
        return userRepository.existsByTelegramId(telegramId);
    }

    public boolean existsByNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public String register(String nickname, Long telegramId) {
        if (existsByTelegramId(telegramId)) {
            return "Ты уже зарегистрирован";

        } else {
            if (existsByNickname(nickname)) {
                return "Такой никнейм занят";
            } else {
                addUser(nickname, telegramId);
                return "Поздравляю, ты прошёл регистрацию";
            }
        }
    }
}