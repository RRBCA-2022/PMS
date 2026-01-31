package io.github.rrbca2022.pms.controller;

import io.github.rrbca2022.pms.entity.AccountType;
import io.github.rrbca2022.pms.entity.User;
import io.github.rrbca2022.pms.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public String listSupplier(Model model){
        model.addAttribute("users",userService.getAllUsers());
        return "/user";
    }

    @GetMapping("/new")
    public String newUser(Model model){
        model.addAttribute("user",new User());
        model.addAttribute("action","Add");

        model.addAttribute("accountTypes", AccountType.values());
        return "/add_user";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute User user){
        userService.saveUser(user);
        return "redirect:/user";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable String id , ModelMap model){
        User user=userService.getUserById(id);
        model.addAttribute("user",user);
        model.addAttribute("action","Edit");
        model.addAttribute("accountTypes", AccountType.values());
        return"/add_user";

    }

    @GetMapping("/delete")
    public String deleteUser(@PathVariable String id){
        userService.deleteUser(id);
        return "redirect:/user";
    }


}
