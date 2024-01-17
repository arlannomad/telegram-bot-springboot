package kz.almaty.telegrambotspringboot.controller;

import kz.almaty.telegrambotspringboot.model.AppUser;
import kz.almaty.telegrambotspringboot.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    @DeleteMapping("/deleteByChatId/{chatId}")
    public String deleteByChatId(@PathVariable("chatId") Long chatId) {
        appUserService.deleteByChatId(chatId);
        return "User with chatId: " + chatId + " deleted!";
    }

    @GetMapping("/findAppUserByTelegramUserId/{id}")
    public AppUser findAppUserByTelegramUserId(@PathVariable("id") Long id) {
        return appUserService.findAppUserByTelegramUserId(id);
    }

    @DeleteMapping("/deleteById/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        appUserService.deleteById(id);
    }

    @GetMapping
    public List<AppUser> getAllUsers() {
        return appUserService.getAllUsers();
    }

    @GetMapping("/findById/{id}")
    public AppUser findById(@PathVariable("id") Long id) {
        return appUserService.findById(id);
    }
}
