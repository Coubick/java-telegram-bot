package com.example.service;

import com.example.game_session_dao.GameSession;
import com.example.game_session_dao.GameSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Service
public class GameSessionService {
    private final GameSessionRepository gameSessionRepository;

    @Autowired
    public GameSessionService(GameSessionRepository gameSessionRepository) {
        this.gameSessionRepository = gameSessionRepository;
    }

    public Integer getSpinsAvailable(Update update){
        return gameSessionRepository.findByTelegramId(update.getMessage().getFrom().getId()).get().getSpinsAvailable();
    }

    public Optional<GameSession> findByTelegramId(Long telegramId) {
        return gameSessionRepository.findByTelegramId(telegramId);
    }

    public void addGameSession(GameSession gameSession){
        gameSessionRepository.save(gameSession);
    }
}
