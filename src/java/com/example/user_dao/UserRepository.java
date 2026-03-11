package com.example.user_dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findTop10ByOrderByCapitalDesc();

    @Query(value = """
    SELECT COUNT (*) + 1
    FROM users
    WHERE capital > :capital
    """, nativeQuery = true)
    Optional<Integer> findUserRank(@Param("capital") Double capital);

    Optional<User> findByTelegramId(Long id);

    boolean existsByNickname(String nickname);

    void deleteUserByTelegramId(Long telegramId);
}