package kz.almaty.telegrambotspringboot.service;

import kz.almaty.telegrambotspringboot.dto.AppUserDto;
import kz.almaty.telegrambotspringboot.dto.PageDtoAppUser;
import kz.almaty.telegrambotspringboot.model.AppUser;
import org.springframework.data.domain.Page;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface AppUserService {
    void deleteById(Long id);
    PageDtoAppUser getAllUsersByPages(int pageNumber, int pageSize, String sortBy, String sortDirection);
    AppUserDto findById(Long id);
    AppUserDto findAppUserByTelegramUserId(Long id);
    void register(Message message);
    void deleteByChatId(Long chatId);
    Page<AppUser> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
