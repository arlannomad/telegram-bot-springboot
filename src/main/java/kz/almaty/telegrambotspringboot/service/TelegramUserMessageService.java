package kz.almaty.telegrambotspringboot.service;

import kz.almaty.telegrambotspringboot.dto.AppUserDto;
import kz.almaty.telegrambotspringboot.dto.PageDtoAppUser;
import kz.almaty.telegrambotspringboot.dto.PageDtoTelegramUserMessage;
import kz.almaty.telegrambotspringboot.model.AppUser;
import kz.almaty.telegrambotspringboot.model.TelegramUserMessage;
import org.springframework.data.domain.Page;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;
import java.util.Optional;

public interface TelegramUserMessageService {
   void addMessage(Message message);
   void addChatGptResponseText(Message message);
   PageDtoTelegramUserMessage getAllUMessagesByPages(int pageNumber, int pageSize, String sortBy, String sortDirection);
   List<TelegramUserMessage> findAllByTelegramUserId(Long id);
   Page<TelegramUserMessage> findMessagePaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
