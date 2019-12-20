package lev.philippov.geekmarket.controller;

import lev.philippov.geekmarket.Model.OrderInfo;
import lev.philippov.geekmarket.Model.User;
import lev.philippov.geekmarket.errorHandlers.UserNotFoundException;
import lev.philippov.geekmarket.service.ItemService;
import lev.philippov.geekmarket.service.OrderService;
import lev.philippov.geekmarket.service.UserService;
import lev.philippov.geekmarket.utils.Cart;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class CartController {

    private Cart cart;
    private ItemService itemService;
    private UserService userService;
    private OrderService orderService;

    public CartController(Cart cart, ItemService itemService, UserService userService, OrderService orderService) {
        this.cart = cart;
        this.itemService = itemService;
        this.userService = userService;
        this.orderService = orderService;
    }


    @GetMapping(value = "/cart")
    public String show(Model model) {
        model.addAttribute("cartItemsMap", cart.getCartItemsMap());
        return "cart";
    }

    @GetMapping("/cart/delete")
    public String deleteFromCart (@RequestParam(name = "id") Long id) {
        cart.delete(id);
        return "redirect:/cart";
    }

    @PostMapping("/cart/save")
    public String saveOrder(@ModelAttribute(name = "orderInfo") OrderInfo orderInfo, Principal principal){
        User user = userService.findByUsername(principal.getName()).orElseThrow(()-> new UserNotFoundException("User not found!"));
        orderService.saveOrder(user, cart, orderInfo);
        return "redirect:/shop";
    }

    @GetMapping("/cart/save")
    public String verifyOrderDetails(Model model,Principal principal ) {
        User user = userService.findByUsername(principal.getName()).orElseThrow(()-> new UserNotFoundException("User not found!"));
        OrderInfo orderInfo = new OrderInfo(user);
        model.addAttribute(orderInfo);
        model.addAttribute("cartPrice",cart.getCartPrice());
        model.addAttribute("cartItemsMap",cart.getCartItemsMap());
        return "orderInfo";
    }



}
