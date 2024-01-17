package kz.almaty.telegrambotspringboot.service;

import kz.almaty.telegrambotspringboot.model.AppUser;

import java.util.List;

public interface AppUserService {
    void deleteByChatId(Long chatId);
    void deleteById(Long id);
    List<AppUser> getAllUsers();
    AppUser findById(Long id);
    AppUser findAppUserByTelegramUserId(Long id);
}
