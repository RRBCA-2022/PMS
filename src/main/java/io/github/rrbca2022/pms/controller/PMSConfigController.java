package io.github.rrbca2022.pms.controller;

import io.github.rrbca2022.pms.entity.AccountType;
import io.github.rrbca2022.pms.entity.PMSConfig;
import io.github.rrbca2022.pms.entity.User;
import io.github.rrbca2022.pms.services.PMSConfigService;
import io.github.rrbca2022.pms.services.UserService;
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
    public String setting(@RequestParam(value = "id", defaultValue = "1") Long id,ModelMap model) {
        PMSConfig config=pmsConfigService.getPMSById(1L);
        User user=userService.getUserById(id);
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
    public String saveUser(@ModelAttribute("user") User user, RedirectAttributes ra) {
      //  System.out.println(user);
        userService.saveUser(user);
        ra.addFlashAttribute("toastMessage", "Profile Security Updated!");
        ra.addFlashAttribute("toastType", "success");
        return "redirect:/settings";
    }


}
