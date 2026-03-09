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

    public Optional<GameSession> findByTelegramId(Long telegramId) {
        return gameSessionRepository.findByTelegramId(telegramId);
    }

    public void addGameSession(GameSession gameSession){
        gameSessionRepository.save(gameSession);
    }

    public GameSession saveGameSession(GameSession gameSession) {
        return gameSessionRepository.save(gameSession);
    }

    public void delete(GameSession gameSession) {
        if (gameSession != null && gameSession.getId() != null) {
            if (gameSession.getUser() != null) {
                gameSession.getUser().setGameSession(null);
            }
            
            gameSessionRepository.delete(gameSession);
        }
    }
}
