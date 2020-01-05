package lev.philippov.geekmarket.controller;

import lev.philippov.geekmarket.Model.User;
import lev.philippov.geekmarket.errorHandlers.UserNotFoundException;
import lev.philippov.geekmarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;
import java.util.function.Supplier;

@Controller
public class SignInController {
    UserService userService;

    @Autowired
    public SignInController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/signin")
    public String showSignInForm(@RequestParam(name = "phone") String phone, @RequestParam(name = "password") String password, Model model){
        User user = new User();
        user.setPhone(phone);
        user.setUsername(phone);
        user.setPassword(password);
        userService.saveUser(user);
        model.addAttribute(user);
        return "signin";
    }

    @PostMapping(value = "/validateAndSaveUser")
    public String validateAndSaveUser(@Valid @RequestAttribute(name = "user") User user) {
        user = userService.findByUsername(user.getUsername()).orElseThrow(() -> new UserNotFoundException("User not found"));
        userService.saveUser(user);
        return "redirect:/shop";
    }
}
