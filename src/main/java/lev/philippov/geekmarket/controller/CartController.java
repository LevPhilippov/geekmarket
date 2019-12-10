package lev.philippov.geekmarket.controller;

import lev.philippov.geekmarket.service.ItemService;
import lev.philippov.geekmarket.utils.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CartController {

    private Cart cart;
    private ItemService itemService;

    @Autowired
    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    @Autowired
    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @GetMapping(value = "/cart")
    public String show(Model model) {
        model.addAttribute("cartItemsMap", cart.getCartItemsMap());
//
//        iter
        model.addAttribute("items",cart.getItems());
        return "cart";
    }

    @GetMapping("/cart/delete")
    public String deleteFromCart (@RequestParam(name = "id") Long id) {
        cart.delete(id);
        return "redirect:/cart";
    }


}
