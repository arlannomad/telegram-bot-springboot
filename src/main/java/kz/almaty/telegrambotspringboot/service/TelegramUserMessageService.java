package kz.almaty.telegrambotspringboot.service;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface TelegramUserMessageService {
   void addMessage(Message message);
   void addChatGptResponseText(Message message);
}
