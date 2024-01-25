package kz.almaty.telegrambotspringboot.controller;

import jakarta.ws.rs.Path;
import kz.almaty.telegrambotspringboot.service.TelegramUserMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/messages")
public class TelegramUserMessageController {
    private final TelegramUserMessageService telegramUserMessageService;

}
