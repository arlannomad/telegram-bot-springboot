package kz.almaty.telegrambotspringboot.service;

import kz.almaty.telegrambotspringboot.dto.AppUserDto;
import kz.almaty.telegrambotspringboot.dto.PageDto;
import kz.almaty.telegrambotspringboot.model.AppUser;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

public interface AppUserService {
    void deleteById(Long id);
    PageDto getAllUsersByPages(int pageNumber, int pageSize, String sortBy, String sortDirection);
    AppUserDto findById(Long id);
    AppUserDto findAppUserByTelegramUserId(Long id);
    void register(Message message);
    public void deleteByChatId(Long chatId);
}
