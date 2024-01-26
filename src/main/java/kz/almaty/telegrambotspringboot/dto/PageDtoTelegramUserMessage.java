package kz.almaty.telegrambotspringboot.dto;

import kz.almaty.telegrambotspringboot.enums.UserState;
import kz.almaty.telegrambotspringboot.model.AppUser;
import kz.almaty.telegrambotspringboot.model.TelegramUserMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageDtoTelegramUserMessage {
    private List<TelegramUserMessage> content;
    private int pageNumber;
    private int pageSize;
    private Long totalElements;
    private int totalPages;
    private boolean last;
}
