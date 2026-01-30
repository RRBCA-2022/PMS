package io.github.rrbca2022.pms.controller;

import io.github.rrbca2022.pms.entity.AccountType;
import io.github.rrbca2022.pms.entity.PMSConfig;
import io.github.rrbca2022.pms.entity.User;
import io.github.rrbca2022.pms.services.PMSConfigService;
import io.github.rrbca2022.pms.services.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
@RequestMapping("/settings")
public class PMSConfigController {

    private final PMSConfigService pmsConfigService;
    private final UserService userService;

    @GetMapping
    public String setting(ModelMap model, HttpSession session) {
        PMSConfig config = pmsConfigService.getConfig();
        User user = (User) session.getAttribute("LOGGED_USER");

        model.addAttribute("config", config);
        model.addAttribute("user", user);
        model.addAttribute("accountTypes", AccountType.values());
        return "settings";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute PMSConfig config, RedirectAttributes redirectAttributes) {
        pmsConfigService.saveConfig(config);
        redirectAttributes.addFlashAttribute(
                "toastMessage",
                "PMSInfo Updated successfully"
        );
        redirectAttributes.addFlashAttribute("toastType", "success");
        return "redirect:/settings";
    }
    @PostMapping("/save-user")
    public String saveUser(@ModelAttribute("user") User user, RedirectAttributes ra, HttpSession session) {
        // get old user data
        User oldUser = (User) session.getAttribute("LOGGED_USER");
        // set the account type
        user.setAccountType(oldUser.getAccountType());
        //if form has blank, use previously saved password
        if (user.getPassword().isEmpty()) { user.setPassword(oldUser.getPassword()); }

        //save
        userService.saveUser(user);
        // update it in session
        session.setAttribute("LOGGED_USER", user);

        ra.addFlashAttribute("toastMessage", "Profile Security Updated!");
        ra.addFlashAttribute("toastType", "success");
        return "redirect:/settings";
    }


}
