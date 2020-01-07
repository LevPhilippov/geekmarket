package lev.philippov.geekmarket.controller;

import lev.philippov.geekmarket.Model.Order;
import lev.philippov.geekmarket.Model.User;
import lev.philippov.geekmarket.errorHandlers.UserNotFoundException;
import lev.philippov.geekmarket.service.OrderService;
import lev.philippov.geekmarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class ProfileController {

    UserService userService;
    OrderService orderService;

    @Autowired
    public ProfileController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping(value = "/profile")
    public String getUserProfile(Model model, Principal principal){
        User user = userService.findByUsername(principal.getName()).orElseThrow(()-> new UserNotFoundException("User not found"));
        List<Order> orderList = orderService.findAllByUser(user);
        model.addAttribute(user);
        model.addAttribute(orderList);
        return "profile_page";
    }
}
