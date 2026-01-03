package io.github.rrbca2022.pms.controller;

import io.github.rrbca2022.pms.services.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @RequestMapping("/")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            RedirectAttributes redirectAttributes
    ) {
        boolean authenticated = loginService.authenticate(username, password);

        if (!authenticated) {
            redirectAttributes.addFlashAttribute("loginStatusFail", "Invalid username or password");
            return "redirect:/"; // back to login
        }

        redirectAttributes.addFlashAttribute("loginStatusSuccess", "Successfully logged in as '" + username + "'");
        return "redirect:/dashboard";
    }
}
