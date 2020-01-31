package lev.philippov.geekmarket.controller;

import lev.philippov.geekmarket.Model.Item;
import lev.philippov.geekmarket.Model.User;
import lev.philippov.geekmarket.Model.UserComment;
import lev.philippov.geekmarket.errorHandlers.UserNotFoundException;
import lev.philippov.geekmarket.service.ItemService;
import lev.philippov.geekmarket.service.UserCommentService;
import lev.philippov.geekmarket.service.UserService;
import lev.philippov.geekmarket.utils.CookieHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;

@Controller
public class ItemCardController {
    ItemService itemService;
    UserService userService;
    UserCommentService userCommentService;

    @Autowired
    public ItemCardController(ItemService itemService, UserService userService, UserCommentService userCommentService) {
        this.itemService = itemService;
        this.userService = userService;
        this.userCommentService = userCommentService;
    }

    @GetMapping(value = "/shop/{id}")
    public String showItemCard(@PathVariable(name = "id") Long id, Model model, HttpServletRequest request, HttpServletResponse response) throws Throwable{
        Item item = itemService.findItemById(id);
        Float rating = itemService.getItemRating(item);
        model.addAttribute(item);
        model.addAttribute("rating", rating);
        model.addAttribute("comments", userCommentService.findUserCommentsByItem_Id(item.getId()));
        model.addAttribute("lastViewedItemsList", itemService.updateLastVieweditemsList(request,response,id));
        return "item_card";
    }

    @PostMapping(value="/comment")
    @Secured("ROLE_USER")
    public String insertItemComment (@ModelAttribute(name = "item_id") Long id,
                                     @ModelAttribute(name = "rating") Integer score,
                                     @ModelAttribute(name="comment") String comment, Principal principal) throws Throwable {
        Item item = itemService.findItemById(id);
        User user = userService.findByUsername(principal.getName()).orElseThrow(()->new UserNotFoundException("User not found!"));
        UserComment userComment = new UserComment(score, comment, item, user);
        userCommentService.saveComment(userComment);
        return "redirect:/showItemCard/" + id;
    }

    //TODO метод не работает (все еще не работает 31.01.20)
    @GetMapping(value = "/cookie/remove")
    @ResponseBody
    public String deleteAllCookies(HttpServletRequest request, HttpServletResponse response) {
        CookieHelper.removeCookies(request, response);
//        return "redirect:/shop";
        return "Cookie is empty";
    }
}
