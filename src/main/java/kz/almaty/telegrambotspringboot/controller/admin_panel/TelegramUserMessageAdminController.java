package kz.almaty.telegrambotspringboot.controller.admin_panel;

import kz.almaty.telegrambotspringboot.model.AppUser;
import kz.almaty.telegrambotspringboot.model.TelegramUserMessage;
import kz.almaty.telegrambotspringboot.repository.TelegramUserMessageRepository;
import kz.almaty.telegrambotspringboot.service.TelegramUserMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TelegramUserMessageAdminController {

    private final TelegramUserMessageService telegramUserMessageService;

    //http://localhost:8080/
    @GetMapping("/pages/{pageNo}")
    public String findMessagePaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 10;

        Page<TelegramUserMessage> page = telegramUserMessageService.findMessagePaginated(pageNo, pageSize, sortField, sortDir);
        List<TelegramUserMessage> messages = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("messages", messages);
        return "messages";
    }

    //display list of users
    @GetMapping("/messages")
    public String viewMessageHomePage(Model model) {
        return findMessagePaginated(1, "id", "asc", model);
    }
}
