package kz.almaty.telegrambotspringboot.repository;

import kz.almaty.telegrambotspringboot.model.TelegramUserMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TelegramUserMessageRepository extends JpaRepository<TelegramUserMessage, Long> {
    Optional<TelegramUserMessage> findTelegramUserMessageByTelegramUserId(Long id);
    boolean existsByTelegramUserId(Long id);
    void deleteByTelegramUserId(Long chatId);
    void deleteTelegramUserMessageByTelegramUserId(TelegramUserMessage message);
}
