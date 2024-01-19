package kz.almaty.telegrambotspringboot.dto;


import kz.almaty.telegrambotspringboot.enums.UserState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


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
}
