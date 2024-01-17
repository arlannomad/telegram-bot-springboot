package kz.almaty.telegrambotspringboot.bot;

import kz.almaty.telegrambotspringboot.enums.UserState;
import kz.almaty.telegrambotspringboot.exception.GlobalApiException;
import kz.almaty.telegrambotspringboot.model.AppUser;
import kz.almaty.telegrambotspringboot.repository.AppUserRepository;
import kz.almaty.telegrambotspringboot.service.AppUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

@Slf4j
@Component
public class Bot extends TelegramLongPollingBot {

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private AppUserService appUserService;

    public Bot(@Value("${bot.token}") String token) {
        super(token);
    }

    @Value("${bot.username}")
    private String username;

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()) {
                String text = message.getText();
                if (text.equals("/start")) {
                    register(update.getMessage());
                    log.info("We've got a message: "
                            + "MessageId: " + message.getMessageId()
                            + " ChatId " + message.getChatId());
                    SendMessage sendMessage = new SendMessage();
//                    sendMessage.setText("Response for /start you can put here yoy text message");
                    sendMessage.setText(message.getFrom().getUserName() + " you are subscribed and if you want to unsubscribe from our service press to unsubscribe button ");
                    sendMessage.setParseMode(ParseMode.MARKDOWN);
                    sendMessage.setChatId(message.getChatId());
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        log.info(e + "Message");
                    }
                } else  if (text.equals("/unsubscribe")) {
                    unsubscribe(update.getMessage());
                    log.info("We've got a message: "
                            + "MessageId: " + message.getMessageId()
                            + " ChatId " + message.getChatId());
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setText("Response for /start you can put here yoy text message");
                    sendMessage.setParseMode(ParseMode.MARKDOWN);
                    sendMessage.setChatId(message.getChatId());
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        log.info(e + "Message");
                    }
                }
            }
        }
    }

    private void unsubscribe(Message message) {
        if (userRepository.findAppUserByTelegramUserId(message.getChatId()).isPresent()) {
            throw new GlobalApiException(HttpStatus.BAD_REQUEST, "User with chatId " + message.getChatId() + " NOT FOUND");
        } else {
            userRepository.findById(message.getChatId());
        }
    }

    private void register(Message message) {
        if (userRepository.findAppUserByTelegramUserId(message.getChatId()).isPresent()) {
            throw new GlobalApiException(HttpStatus.BAD_REQUEST, "User " + message.getFrom() + " already registered");
        } else {
            var chatId = message.getChatId();
            var chat = message.getChat();

            AppUser appUser = new AppUser();
            appUser.setTelegramUserId(chatId);
            appUser.setFirstname(chat.getFirstName());
            appUser.setLastName(chat.getLastName());
            appUser.setUsername(chat.getUserName());
            appUser.setIsActive(true);
            appUser.setState(UserState.SUBSCRIBED);
            userRepository.save(appUser);
        }
    }
}
