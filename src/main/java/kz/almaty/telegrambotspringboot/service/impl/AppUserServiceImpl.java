package kz.almaty.telegrambotspringboot.service.impl;

import kz.almaty.telegrambotspringboot.dto.PageDto;
import kz.almaty.telegrambotspringboot.enums.UserState;
import kz.almaty.telegrambotspringboot.exception.GlobalApiException;
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
    public AppUser findAppUserByTelegramUserId(Long id) {
        return userRepository.findAppUserByTelegramUserId(id).orElseThrow(()
                -> new GlobalApiException(HttpStatus.BAD_REQUEST, "NOT FOUND"));
    }

    @Override
    public void deleteById(Long id) {
        userRepository.findById(id).orElseThrow(()
                -> new GlobalApiException(HttpStatus.BAD_REQUEST, "NOT FOUND"));
        userRepository.deleteById(id);
    }

    @Override
    public PageDto getAllUsersByPages(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<AppUser> posts = userRepository.findAll(pageable);
        List<AppUser> content = userRepository.findAll();
        return PageDto.builder()
                .content(content)
                .pageNumber(posts.getNumber())
                .pageSize(posts.getSize())
                .pageSize(posts.getSize())
                .totalElements(posts.getTotalElements())
                .totalPages(posts.getTotalPages())
                .last(posts.isLast())
                .build();
    }


    @Override
    public AppUser findById(Long id) {
            return userRepository.findById(id).orElseThrow(()
                    -> new GlobalApiException(HttpStatus.BAD_REQUEST, "NOT FOUND"));
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
            appUser.setFirstname(chat.getFirstName());
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
