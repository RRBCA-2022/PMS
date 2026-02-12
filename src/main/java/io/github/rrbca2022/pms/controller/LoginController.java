package io.github.rrbca2022.pms.controller;

import io.github.rrbca2022.pms.entity.User;
import io.github.rrbca2022.pms.services.LoginService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @RequestMapping("/")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            RedirectAttributes redirectAttributes,
            HttpSession session

    ) {
        User user = loginService.authenticate(username, password);

        if (user == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid username or password");
            return "redirect:/"; // back to login
        }
        System.out.println("Logged In : " + user);

        session.setAttribute("LOGGED_USER", user);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Successfully logged in as '" + user.getName() + "'"
        );

        return "redirect:/dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }


}
