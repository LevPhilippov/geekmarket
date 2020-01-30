package lev.philippov.geekmarket.controller;

import lev.philippov.geekmarket.Model.Item;
import lev.philippov.geekmarket.Model.User;
import lev.philippov.geekmarket.Model.UserComment;
import lev.philippov.geekmarket.errorHandlers.UserNotFoundException;
import lev.philippov.geekmarket.service.ItemService;
import lev.philippov.geekmarket.service.UserCommentService;
import lev.philippov.geekmarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;

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

    @GetMapping(value = "/showItemCard/{id}")
    public String showItemCard(@PathVariable(name = "id") Long id, Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Throwable{
        Item item = itemService.findItemById(id);
        Float rating = itemService.getItemRating(item);
        itemService.updateHistory(request,response,id,session);
        model.addAttribute(item);
        model.addAttribute("rating", rating);
        model.addAttribute("comments", userCommentService.findUserCommentsByItem_Id(item.getId()));
        return "item_card";
    }

    @PostMapping(value="/insertItemComment")
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

    //TODO метод не работает
    @GetMapping(value = "/cookie/remove")
    public String deleteAllCookies(HttpServletRequest request) {
        for(Cookie c: request.getCookies()){
            c.setMaxAge(0);
        }
        return "redirect:/shop";
    }
}
