package com.example.service;

import com.example.user_dao.User;
import com.example.user_dao.UserRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    public void deleteUserByTelegramId(Long telegramId) {
        try {
            Optional<User> user = userRepository.findByTelegramId(telegramId);
            if (user.isPresent()) {
                userRepository.delete(user.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Optional<User> findByTelegramId(Long telegramId) {
        return userRepository.findByTelegramId(telegramId);
    }

    public boolean existsByNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public String register(String nickname, Long telegramId) {
        if (findByTelegramId(telegramId).isPresent()) {
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

    public String findTop10ByOrderByCapitalDescInString(){
        List<User> topUsers = userRepository.findTop10ByOrderByCapitalDesc();
        if (topUsers.size() > 0) {
            StringBuilder usersString = new StringBuilder();
            for (int i = 0; i < topUsers.size(); i++) {
                User currentUser = topUsers.get(i);
                usersString
                        .append(i + 1)
                        .append(". ")
                        .append(currentUser.getNickname())
                        .append(": ").append(currentUser.getCapital())
                        .append("\n");
            }

            return "\uD83C\uDFC6 Лучшие деперы \uD83C\uDFC6\n" + usersString;
        } else {
            return "Деперы не найдены";
        }
    }
}