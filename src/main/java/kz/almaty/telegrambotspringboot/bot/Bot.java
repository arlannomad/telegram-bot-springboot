package kz.almaty.telegrambotspringboot.bot;


import kz.almaty.telegrambotspringboot.dto.ChatGptRequest;
import kz.almaty.telegrambotspringboot.dto.ChatGptResponse;
import kz.almaty.telegrambotspringboot.model.TelegramUserMessage;
import kz.almaty.telegrambotspringboot.service.AppUserService;
import kz.almaty.telegrambotspringboot.service.TelegramUserMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Slf4j
@Component
public class Bot extends TelegramLongPollingBot {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private TelegramUserMessageService telegramUserMessageService;

    @Autowired
    private RestTemplate restTemplate;

    public Bot(@Value("${bot.token}") String token) {
        super(token);
    }

    @Value("${bot.username}")
    private String username;

    @Override
    public String getBotUsername() {
        return username;
    }

    @Value("${openai.model}")
    private String model;

    @Value("${open.api.url}")
    private String apiURL;

    private String chatGptResponse(String prompt) {
        ChatGptRequest chatGptRequest = new ChatGptRequest(model, prompt);
        ChatGptResponse chatGptResponse = restTemplate.postForObject(apiURL, chatGptRequest, ChatGptResponse.class);
        assert chatGptResponse != null;
        return chatGptResponse.getChoices().get(0).getMessage().getContent();
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
//                    sendMessage.setText(message.getFrom().getUserName() +
//                    " you are subscribed and if you want to unsubscribe from our service press to unsubscribe button ");
                    sendMessage.setText(message.getFrom().getUserName() + " type your question");
                    sendMessage.setParseMode(ParseMode.MARKDOWN);
                    sendMessage.setChatId(message.getChatId());
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        log.info(e + "Message");
                    }
                } else  if (message.hasText()) {
                    TelegramUserMessage userMessage = new TelegramUserMessage();
//                    appUserService.deleteByChatId(message.getChatId());
                    telegramUserMessageService.addMessage(message);
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setText(chatGptResponse(text));
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
