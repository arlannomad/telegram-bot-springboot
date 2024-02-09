package kz.almaty.telegrambotspringboot.service.impl;

import kz.almaty.telegrambotspringboot.service.AIService;
import org.springframework.ai.client.AiClient;
import org.springframework.ai.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AIServiceImpl implements AIService {

    @Autowired
    AiClient aiClient;
    @Override
    public String getPrompt(String prompt) {
        PromptTemplate promptTemplate = new PromptTemplate("""
                You are an interpreter
                You will be provided with a prompt {prompt}
                You will translate it to Arabic
               """);
        promptTemplate.add("prompt", prompt);
        return this.aiClient.generate(promptTemplate.create()).getGeneration().getText();
    }
}
