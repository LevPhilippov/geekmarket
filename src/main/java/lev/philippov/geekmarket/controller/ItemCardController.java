package lev.philippov.geekmarket.controller;

import lev.philippov.geekmarket.Model.Item;
import lev.philippov.geekmarket.Model.User;
import lev.philippov.geekmarket.Model.UserComment;
import lev.philippov.geekmarket.errorHandlers.UserNotFoundException;
import lev.philippov.geekmarket.service.ItemService;
import lev.philippov.geekmarket.service.UserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ItemCardController {
    ItemService itemService;
    UserService userService;

    public ItemCardController(ItemService itemService, UserService userService) {
        this.itemService = itemService;
        this.userService = userService;
    }

    @GetMapping(value = "/showItemCard/{id}")
    public String showItemCard(@PathVariable(name = "id") Long id, Model model) throws Throwable{
        Item item = itemService.findItemById(id);
        Float rating = null;

        if(item.getComments().size()!=0) {
            float score=0;
            for (UserComment comment: item.getComments()) {
                score += comment.getScore();
            }
            rating = score/item.getComments().size();
        }


        model.addAttribute(item);
        model.addAttribute("rating", rating);
        model.addAttribute("comments", item.getComments());
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
        if(item.getComments() == null) {
            List<UserComment> commentList = new ArrayList<>();
            item.setComments(commentList);
        }
            item.getComments().add(userComment);
        itemService.flush();
        return "redirect:/showItemCard/" + id;
    }

   // TODO: оформить карточку товара, сделать возможным оставлять комментарии только зарегистрированным пользователям,
    //  добавить к комментарию идентификатор остаивившего его.

}
