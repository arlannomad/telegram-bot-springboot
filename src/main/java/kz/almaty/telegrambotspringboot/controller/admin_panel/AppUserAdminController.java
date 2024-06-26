package kz.almaty.telegrambotspringboot.controller.admin_panel;


import kz.almaty.telegrambotspringboot.model.AppUser;
import kz.almaty.telegrambotspringboot.repository.TelegramUserMessageRepository;
import kz.almaty.telegrambotspringboot.service.AppUserService;
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
public class AppUserAdminController {

    private final AppUserService appUserService;

    //http://localhost:8080/
    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 5;

        Page<AppUser> page = appUserService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<AppUser> users = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("users", users);
        return "users";
    }

     //display list of users
    @GetMapping("/")
    public String viewHomePage(Model model) {
        return findPaginated(1, "username", "asc", model);
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteEmployee(@PathVariable (value = "id") long id) {

        // call delete user method
        this.appUserService.deleteByChatId(id);
        return "redirect:/";
    }

}
