package com.example.service;

import com.example.user_dao.User;
import com.example.user_dao.UserRepository;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.swing.text.html.Option;
import javax.xml.crypto.Data;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
        Date date = new Date(stamp.getTime());
        User newUser = new User(
                nickname,
                10000d,
                date,
                telegramId);

        userRepository.save(newUser);
    }

    public void deleteUserByTelegramId(Long telegramId) {
        try {
            Optional<User> user = userRepository.findByTelegramId(telegramId);
            user.ifPresent(userRepository::delete);
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
        if (!topUsers.isEmpty()) {
            StringBuilder usersString = new StringBuilder();
            for (int i = 0; i < topUsers.size(); i++) {
                User currentUser = topUsers.get(i);
                usersString
                        .append(i + 1)
                        .append(". ")
                        .append(currentUser.getNickname())
                        .append(": ").append(String.format("%.2f", currentUser.getCapital()))
                        .append("\n");
            }

            return "\uD83C\uDFC6 Лучшие деперы \uD83C\uDFC6\n" + usersString;
        } else {
            return "Деперы не найдены";
        }
    }

    public void updateUser(User user){
        userRepository.save(user);
    }

    public String collectStats(Long telegramId){
        Optional<User> userOpt = userRepository.findByTelegramId(telegramId);
        if (userOpt.isPresent()){
            User user = userOpt.get();

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

            String responseString = "Статистика для " + user.getNickname() +": \n" +
                    "Дата регистрации: " + dateFormat.format(user.getRegistrationDate()) + "\n" +
                    "Баланс: " + user.getCapital() + "\n" +
                    "Слотов прокручено: " + user.getTotalSpins() + "\n" +
                    "Место в общем рейтинге: " + userRepository.findUserRank(user.getCapital()).get();
            return responseString;
        }
        return "У тебя нет аккаунта";
    }
}