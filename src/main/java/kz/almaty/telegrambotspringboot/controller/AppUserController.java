package kz.almaty.telegrambotspringboot.controller;


import kz.almaty.telegrambotspringboot.dto.AppUserDto;
import kz.almaty.telegrambotspringboot.dto.PageDtoAppUser;
import kz.almaty.telegrambotspringboot.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import static kz.almaty.telegrambotspringboot.utils.AppConstants.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping("/findAppUserByTelegramUserId/{id}")
    public AppUserDto findAppUserByTelegramUserId(@PathVariable("id") Long id) {
        return appUserService.findAppUserByTelegramUserId(id);
    }

    @DeleteMapping("/deleteById/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        appUserService.deleteById(id);
    }

    @DeleteMapping("/deleteByChatId/{id}")
    public void deleteByChatId(@PathVariable("id") Long id) {
        appUserService.deleteByChatId(id);
    }

    @GetMapping("/getAllUsersByPages")
    public ResponseEntity<PageDtoAppUser> getAllUsersByPages(@RequestParam(value = "pageNumber", defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNumber,
                                                             @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                             @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
                                                             @RequestParam(value = "sortDirection", defaultValue = DEFAULT_SORT_DIRECTION, required = false) String sortDirection) {
        return new ResponseEntity<>(appUserService.getAllUsersByPages(pageNumber, pageSize, sortBy, sortDirection), HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    public AppUserDto findById(@PathVariable("id") Long id) {
        return appUserService.findById(id);
    }

}
