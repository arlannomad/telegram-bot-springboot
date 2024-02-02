package kz.almaty.telegrambotspringboot.service.impl;

import kz.almaty.telegrambotspringboot.dto.PageDtoAppUser;
import kz.almaty.telegrambotspringboot.dto.PageDtoTelegramUserMessage;
import kz.almaty.telegrambotspringboot.enums.UserState;
import kz.almaty.telegrambotspringboot.exception.GlobalApiException;
import kz.almaty.telegrambotspringboot.model.AppUser;
import kz.almaty.telegrambotspringboot.model.TelegramUserMessage;
import kz.almaty.telegrambotspringboot.repository.AppUserRepository;
import kz.almaty.telegrambotspringboot.repository.TelegramUserMessageRepository;
import kz.almaty.telegrambotspringboot.service.TelegramUserMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;
import java.util.Optional;

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

    @Override
    public PageDtoTelegramUserMessage getAllUMessagesByPages(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<TelegramUserMessage> messages = telegramUserMessageRepository.findAll(pageable);
        List<TelegramUserMessage> content = telegramUserMessageRepository.findAll();
        return PageDtoTelegramUserMessage.builder()
                .content(content)
                .pageNumber(messages.getNumber())
                .pageSize(messages.getSize())
                .pageSize(messages.getSize())
                .totalElements(messages.getTotalElements())
                .totalPages(messages.getTotalPages())
                .last(messages.isLast())
                .build();
    }

    @Override
    public List<TelegramUserMessage> findAllByTelegramUserId(Long id) {
        return telegramUserMessageRepository.findAllByTelegramUserId(id);
    }

    @Override
    public Page<TelegramUserMessage> findMessagePaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.telegramUserMessageRepository.findAll(pageable);
    }

}
