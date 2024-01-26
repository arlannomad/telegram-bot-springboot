package kz.almaty.telegrambotspringboot.service.impl;

import kz.almaty.telegrambotspringboot.exception.GlobalApiException;
import kz.almaty.telegrambotspringboot.model.AppUser;
import kz.almaty.telegrambotspringboot.model.TelegramUserMessage;
import kz.almaty.telegrambotspringboot.repository.AppUserRepository;
import kz.almaty.telegrambotspringboot.repository.TelegramUserMessageRepository;
import kz.almaty.telegrambotspringboot.service.TelegramUserMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
@RequiredArgsConstructor
public class TelegramUserMessageServiceImpl implements TelegramUserMessageService {
    private final AppUserRepository appUserRepository;
    private final TelegramUserMessageRepository telegramUserMessageRepository;

    @Override
    public void addMessage(Message message) {
        boolean isChatIdExists = appUserRepository.existsAppUsersByTelegramUserId(message.getChatId());
        if (!isChatIdExists) {
            throw new GlobalApiException(HttpStatus.BAD_REQUEST, "User " + message.getChatId() + " NOT FOUND");
        }
        appUserRepository.findAppUserByTelegramUserId(message.getChatId()).get();
        TelegramUserMessage userMessage = new TelegramUserMessage();
        userMessage.setTelegramUserMessageRequest(message.getText());
        userMessage.setTelegramUserId(message.getChatId());
        userMessage.setTelegramUserId(message.getFrom().getId());
        telegramUserMessageRepository.save(userMessage);
    }

    @Override
    public void addChatGptResponseText(Message message) {
        boolean isChatIdExists = appUserRepository.existsAppUsersByTelegramUserId(message.getChatId());
        if (!isChatIdExists) {
            throw new GlobalApiException(HttpStatus.BAD_REQUEST, "User " + message.getChatId() + " NOT FOUND");
        }
        appUserRepository.findAppUserByTelegramUserId(message.getChatId()).get();
        TelegramUserMessage userMessage = new TelegramUserMessage();
        userMessage.setTelegramUserMessageRequest(message.getText());
        userMessage.setTelegramUserId(message.getChatId());
        userMessage.setTelegramUserId(message.getFrom().getId());
        telegramUserMessageRepository.save(userMessage);
    }

}
