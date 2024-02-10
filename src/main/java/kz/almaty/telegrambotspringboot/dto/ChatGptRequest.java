package kz.almaty.telegrambotspringboot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChatGptRequest {
    private String model;
    private List<Message> messages;

    private double temperature = 0.9;
    @JsonProperty(value = "max_tokens")
    private int maxTokens = 256;

    public ChatGptRequest(String model, String prompt) {
        this.model = model;
        this.messages = new ArrayList<>();
        this.messages.add(new Message("user", prompt));
    }
}
