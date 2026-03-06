package com.example.user_dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findTop10ByOrderByCapitalDesc();

    Optional<User> findByTelegramId(Long id);

    boolean existsByNickname(String nickname);

    void deleteUserByTelegramId(Long telegramId);
}