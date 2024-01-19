package kz.almaty.telegrambotspringboot.mapper;

import kz.almaty.telegrambotspringboot.dto.AppUserDto;
import kz.almaty.telegrambotspringboot.enums.UserState;
import kz.almaty.telegrambotspringboot.model.AppUser;

public class AppUserMapper {
    public static AppUserDto mapToDto(AppUser appUser) {
        return AppUserDto.builder()
                .id(appUser.getId())
                .telegramUserId(appUser.getTelegramUserId())
                .firstName(appUser.getFirstName())
                .lastName(appUser.getLastName())
                .username(appUser.getUsername())
                .isActive(true)
                .state(UserState.SUBSCRIBED)
                .build();
    }

    public static AppUser mapToDto(AppUserDto appUserDto) {
        return AppUser.builder()
                .id(appUserDto.getId())
                .telegramUserId(appUserDto.getTelegramUserId())
                .firstName(appUserDto.getFirstName())
                .lastName(appUserDto.getLastName())
                .username(appUserDto.getUsername())
                .isActive(true)
                .state(UserState.SUBSCRIBED)
                .build();
    }
}
