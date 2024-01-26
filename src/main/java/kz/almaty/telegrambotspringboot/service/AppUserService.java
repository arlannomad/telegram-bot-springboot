package kz.almaty.telegrambotspringboot.service;

import kz.almaty.telegrambotspringboot.dto.AppUserDto;
import kz.almaty.telegrambotspringboot.dto.PageDtoAppUser;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface AppUserService {
    void deleteById(Long id);
    PageDtoAppUser getAllUsersByPages(int pageNumber, int pageSize, String sortBy, String sortDirection);
    AppUserDto findById(Long id);
    AppUserDto findAppUserByTelegramUserId(Long id);
    void register(Message message);
    void deleteByChatId(Long chatId);
}
