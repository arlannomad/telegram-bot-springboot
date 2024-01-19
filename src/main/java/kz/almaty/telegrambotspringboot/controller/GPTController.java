package kz.almaty.telegrambotspringboot.controller;

import kz.almaty.telegrambotspringboot.dto.ChatGptRequest;
import kz.almaty.telegrambotspringboot.dto.ChatGptResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/bot")
public class GPTController {
    @Value("${openai.model}")
    private String model;

    @Value("${open.api.url}")
    private String apiURL;

    @Value("${openai,api.key}")
    private String key;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/chat")
    public String chatGptResponse(@RequestParam("prompt") String prompt) {
        ChatGptRequest chatGptRequest = new ChatGptRequest(model, prompt);
        ChatGptResponse chatGptResponse = restTemplate.postForObject(apiURL, chatGptRequest, ChatGptResponse.class);
        assert chatGptResponse != null;
        return chatGptResponse.getChoices().get(0).getMessage().getContent();
    }
}
