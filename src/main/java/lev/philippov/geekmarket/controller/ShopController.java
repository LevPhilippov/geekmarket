package lev.philippov.geekmarket.controller;

import lev.philippov.geekmarket.Model.Item;
import lev.philippov.geekmarket.errorHandlers.ItemNotFoundException;
import lev.philippov.geekmarket.service.ItemService;
import lev.philippov.geekmarket.utils.Cart;
import lev.philippov.geekmarket.utils.ItemSpecUtil;
import lev.philippov.geekmarket.utils.PaginationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Controller
public class ShopController {

    private ItemService itemService;
    private Cart cart;

    @Autowired
    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Autowired
    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/shop")
    public String getAllItems(@RequestParam(name = "addId", required = false) Long addId,
                              HttpServletRequest httpServletRequest,
                              Model model) {

        Map<String, Object> specAndFiltersMap = ItemSpecUtil.getSpecification(httpServletRequest.getParameterMap());
        Pageable pageable = PaginationHelper.getPageable(httpServletRequest.getParameterMap());
        String filters = (String) specAndFiltersMap.get("filters");
        Page<Item> pageItems = itemService.getPagableAndFilteredItems((Specification<Item>) specAndFiltersMap.get("specs"), pageable);
        model.addAttribute("pageItems", pageItems);
        model.addAttribute("filters", filters);
        return "shop";
    }

    @GetMapping("/shop/add")
    public void addToCart(@RequestParam(name = "id", required = true) Long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Item item = itemService.findItemById(id);
            if(item == null) throw new ItemNotFoundException("Item not found!");
            cart.add(item);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        response.sendRedirect(request.getHeader("referer"));
    }

}
