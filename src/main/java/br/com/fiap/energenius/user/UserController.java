package br.com.fiap.energenius.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String home(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> users = userService.getAll();
        model.addAttribute("users", users);
        return "index";
    }

    @GetMapping("/signup")
    public String signUp(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String addUser(@ModelAttribute User user, Model model) {
        try {
            userService.addUser(user);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "signup";
        }
    }

}
