package lev.philippov.geekmarket.controller;

import lev.philippov.geekmarket.Model.Item;
import lev.philippov.geekmarket.service.ItemService;
import lev.philippov.geekmarket.utils.ItemSpecUtil;
import lev.philippov.geekmarket.utils.PaginationHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class ItemController {

    private ItemService itemService;
    public static final Integer PAGE_NUMBER_DEF = 0;
    public static final Integer ITEMS_NUMBER_DEF = 10;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/items")
    public String getAllItems(@RequestParam(name = "editableItemId", required = false) Long editableItemId,
                              HttpServletRequest httpServletRequest,
                              Model model) {
//        httpServletRequest.getParameterMap().keySet().forEach(s -> System.out.println(paramsMap2.get(s)[0]));

        Map<String, Object> specAndFiltersMap = ItemSpecUtil.getSpecification(httpServletRequest.getParameterMap());
        Pageable pageable = PaginationHelper.getPageable(httpServletRequest.getParameterMap());
        String filters = (String) specAndFiltersMap.get("filters");
        Page<Item> pageItems = itemService.getPagableAndFilteredItems((Specification<Item>) specAndFiltersMap.get("specs"), pageable);

        System.out.println(filters);

        if (editableItemId != null) {
            Item editableItem = null;
            if (editableItemId > 0) {
                try {
                    editableItem = itemService.findItemById(editableItemId);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            } else {
                editableItem = new Item();
            }
            model.addAttribute("editableItem", editableItem);
        }
        model.addAttribute("pageItems", pageItems);
        model.addAttribute("filters", filters);
        return "items";
    }

    @PostMapping("/items")
    public String saveItem(@ModelAttribute(name = "editableItem") Item item) {
        itemService.saveItem(item);
        return "redirect:/items" ;
    }
}
