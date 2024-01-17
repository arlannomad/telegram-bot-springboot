package kz.almaty.telegrambotspringboot.service.impl;

import kz.almaty.telegrambotspringboot.exception.GlobalApiException;
import kz.almaty.telegrambotspringboot.model.AppUser;
import kz.almaty.telegrambotspringboot.repository.AppUserRepository;
import kz.almaty.telegrambotspringboot.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository userRepository;
    @Override
    public void deleteByChatId(Long chatId) {
        userRepository.findAppUserByTelegramUserId(chatId)
                .orElseThrow(()-> new GlobalApiException(HttpStatus.BAD_REQUEST, "NOT FOUND"));
        userRepository.deleteById(chatId);
    }

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
    public List<AppUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public AppUser findById(Long id) {
            return userRepository.findById(id).orElseThrow(()
                    -> new GlobalApiException(HttpStatus.BAD_REQUEST, "NOT FOUND"));
    }
}
