package kz.almaty.telegrambotspringboot.controller;


import kz.almaty.telegrambotspringboot.dto.PageDtoTelegramUserMessage;
import kz.almaty.telegrambotspringboot.service.TelegramUserMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static kz.almaty.telegrambotspringboot.utils.AppConstants.*;
import static kz.almaty.telegrambotspringboot.utils.AppConstants.DEFAULT_SORT_DIRECTION;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/messages")
public class TelegramUserMessageController {
    private final TelegramUserMessageService telegramUserMessageService;

    @GetMapping("/getAllMessagesByPages")
    public ResponseEntity<PageDtoTelegramUserMessage> getAllUMessagesByPages(@RequestParam(value = "pageNumber", defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNumber,
                                                             @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                             @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
                                                             @RequestParam(value = "sortDirection", defaultValue = DEFAULT_SORT_DIRECTION, required = false) String sortDirection) {
        return new ResponseEntity<>(telegramUserMessageService.getAllUMessagesByPages(pageNumber, pageSize, sortBy, sortDirection), HttpStatus.OK);
    }

}
