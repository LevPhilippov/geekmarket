package lev.philippov.geekmarket.controller;

import lev.philippov.geekmarket.Model.User;
import lev.philippov.geekmarket.errorHandlers.UserNotFoundException;
import lev.philippov.geekmarket.service.UserService;
import org.hibernate.NonUniqueResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.validation.Valid;

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
        //TODO: при подобной регистьрации необходимо отправлять код подтверждения но сот. тел. для подтверждения личности реристрируемого
        return "signin";
    }

    //TODO: Проблема с сохранением юзеров по номеру телефона, который уже существует (хорошо бы проверять в модальном окне)

    @PostMapping(value = "/validateAndSaveUser")
    public String validateAndSaveUser(@Valid @ModelAttribute(name = "user") User user, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute(user);
            return "signin";
        } else {
            try {
                user = userService.findByUsername(user.getUsername()).orElseThrow(() -> new UserNotFoundException("User not found"));
                userService.saveUser(user);
            } catch (NonUniqueResultException e) {
                bindingResult.addError(new ObjectError("username", "User with such username already exists!"));
                model.addAttribute(user);
                return "signin";
            }
            return "redirect:/shop";
        }
    }
    //TODO: добавить защиту от уже существующих профилей!
}
