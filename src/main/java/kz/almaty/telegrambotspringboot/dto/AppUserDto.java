package kz.almaty.telegrambotspringboot.dto;


import kz.almaty.telegrambotspringboot.enums.UserState;
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
public class AppUserDto {
    private Long id;
    private Long telegramUserId;
    private String firstName;
    private String lastName;
    private String username;
    private Boolean isActive;
    private UserState state;
    private List<TelegramUserMessage> telegramUserMessages;
}
