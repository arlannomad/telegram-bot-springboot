package kz.almaty.telegrambotspringboot.service.impl;

import kz.almaty.telegrambotspringboot.dto.AppUserDto;
import kz.almaty.telegrambotspringboot.dto.PageDtoAppUser;
import kz.almaty.telegrambotspringboot.enums.UserState;
import kz.almaty.telegrambotspringboot.exception.GlobalApiException;
import kz.almaty.telegrambotspringboot.mapper.AppUserMapper;
import kz.almaty.telegrambotspringboot.model.AppUser;
import kz.almaty.telegrambotspringboot.repository.AppUserRepository;
import kz.almaty.telegrambotspringboot.service.AppUserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;


@Getter
@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository userRepository;

    @Override
    public AppUserDto findAppUserByTelegramUserId(Long id) {
        AppUser appUser = userRepository.findAppUserByTelegramUserId(id).orElseThrow(()
                -> new GlobalApiException(HttpStatus.BAD_REQUEST, "NOT FOUND"));
        return AppUserMapper.mapToDto(appUser);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.findById(id).orElseThrow(()
                -> new GlobalApiException(HttpStatus.BAD_REQUEST, "NOT FOUND"));
        userRepository.deleteById(id);
    }

    @Override
    public PageDtoAppUser getAllUsersByPages(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<AppUser> appUsers = userRepository.findAll(pageable);
        List<AppUser> content = userRepository.findAll();
        return PageDtoAppUser.builder()
                .content(content)
                .pageNumber(appUsers.getNumber())
                .pageSize(appUsers.getSize())
                .pageSize(appUsers.getSize())
                .totalElements(appUsers.getTotalElements())
                .totalPages(appUsers.getTotalPages())
                .last(appUsers.isLast())
                .build();
    }

    @Override
    public AppUserDto findById(Long id) {
        AppUser appUser = userRepository.findById(id).orElseThrow(()
                -> new GlobalApiException(HttpStatus.BAD_REQUEST, "NOT FOUND"));
        return AppUserMapper.mapToDto(appUser);
    }

    @Override
    public void register(Message message) {
        if (userRepository.findAppUserByTelegramUserId(message.getChatId()).isPresent()) {
            throw new GlobalApiException(HttpStatus.BAD_REQUEST, "User " + message.getFrom() + " already registered");
        } else {
            var chatId = message.getChatId();
            var chat = message.getChat();

            AppUser appUser = new AppUser();
            appUser.setTelegramUserId(chatId);
            appUser.setFirstName(chat.getFirstName());
            appUser.setLastName(chat.getLastName());
            appUser.setUsername(chat.getUserName());
            appUser.setIsActive(true);
            appUser.setState(UserState.SUBSCRIBED);
            userRepository.save(appUser);
        }
    }

    @Override
    public void deleteByChatId(Long chatId) {
        boolean isChatIdExists = userRepository.existsAppUsersByTelegramUserId(chatId);
        if (!isChatIdExists) {
            throw new GlobalApiException(HttpStatus.BAD_REQUEST, "User " + chatId + " NOT FOUND");
        }
        AppUser appUser = userRepository.findAppUserByTelegramUserId(chatId).get();
        userRepository.delete(appUser);
    }
}
