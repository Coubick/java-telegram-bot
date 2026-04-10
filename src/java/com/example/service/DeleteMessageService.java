package com.example.service;

import com.example.bot.CasinychBot;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessages;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
public class DeleteMessageService {
    private final List<Integer> messageIds = new CopyOnWriteArrayList<>();
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> currentTask;
    private String currentChatId;
    private CasinychBot currentBot;

    public synchronized void scheduleDeletion(String chatId, CasinychBot bot, Integer... newIds) {
        for (Integer id : newIds) {
            if (id != null) {
                messageIds.add(id);
            }
        }
        this.currentChatId = chatId;
        this.currentBot = bot;

        if (currentTask != null && !currentTask.isDone()) {
            currentTask.cancel(false);
        }

        currentTask = scheduler.schedule(() -> {
            deleteMessages();
        }, 5, TimeUnit.SECONDS);
    }

    private synchronized void deleteMessages() {
        if (currentBot == null || currentChatId == null || messageIds.isEmpty()) {
            return;
        }

        try {
            List<Integer> idsToDelete = new ArrayList<>(messageIds);
            messageIds.clear();

            DeleteMessages deleteMessages = new DeleteMessages(currentChatId, idsToDelete);
            currentBot.execute(deleteMessages);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}