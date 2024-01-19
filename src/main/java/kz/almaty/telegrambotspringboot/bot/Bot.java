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
                    appUserService.register(update.getMessage());
                    log.info("We've got a message: "
                            + "MessageId: " + message.getMessageId()
                            + " ChatId " + message.getChatId());
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setText(message.getFrom().getUserName() + " you are subscribed and if you want to unsubscribe from our service press to unsubscribe button ");
                    sendMessage.setParseMode(ParseMode.MARKDOWN);
                    sendMessage.setChatId(message.getChatId());
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        log.info(e + "Message");
                    }
                } else  if (text.equals("/unsubscribe")) {
                    appUserService.deleteByChatId(message.getChatId());
                    log.info("We've got a message: "
                            + "MessageId: " + message.getMessageId()
                            + " ChatId " + message.getChatId());
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setText("Response for /unsubscribe you can put here yoy text message");
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

}
