package kz.almaty.telegrambotspringboot.service;

import kz.almaty.telegrambotspringboot.dto.PageDtoAppUser;
import kz.almaty.telegrambotspringboot.dto.PageDtoTelegramUserMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface TelegramUserMessageService {
   void addMessage(Message message);
   void addChatGptResponseText(Message message);
   PageDtoTelegramUserMessage getAllUMessagesByPages(int pageNumber, int pageSize, String sortBy, String sortDirection);
}
