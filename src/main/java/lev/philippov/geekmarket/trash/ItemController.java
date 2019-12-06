//package lev.philippov.geekmarket.controller;
//
//import lev.philippov.geekmarket.Model.Item;
//import lev.philippov.geekmarket.service.ItemService;
//import org.springframework.data.domain.Page;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Controller
//public class ItemController {
//
//    private ItemService itemService;
//    private final Integer PAGE_NUMBER_DEF = 0;
//    private final Integer ITEMS_NUMBER_DEF = 10;
//
//
//    public ItemController(ItemService itemService) {
//        this.itemService = itemService;
//    }
//
//    @GetMapping("/items")
//    public String getAllItems(@RequestParam(name = "minPrice", required = false) Integer minPrice,
//                              @RequestParam(name = "maxPrice", required = false) Integer maxPrice,
//                              @RequestParam(name = "itemsNumber", required = false) Integer itemsNumber,
//                              @RequestParam(name = "pageNumber", required = false) Integer pageNumber,
//                              @RequestParam(name = "editableItemId", required = false) Long editableItemId,
//                              @RequestParam(name = "addNewItem", required = false) boolean addNewItem,
//                              Model model) {
//
//        pageNumber = (pageNumber == null)? PAGE_NUMBER_DEF : pageNumber;
//        itemsNumber = (itemsNumber == null)? ITEMS_NUMBER_DEF : itemsNumber;
//
//        Map<String, Object> paramsMap = new HashMap();
//        paramsMap.put("minPrice", minPrice);
//        paramsMap.put("maxPrice", maxPrice);
//        paramsMap.put("pageNumber", pageNumber);
//        paramsMap.put("itemsNumber", itemsNumber);
//
//
//        Page<Item> pageItems = itemService.getPagableAndFilteredItems(paramsMap);
//
//        if (editableItemId != null) {
//            Item editableItem = null;
//            try {
//                editableItem = itemService.findItemById(editableItemId);
//                model.addAttribute("editableItem", editableItem);
//            } catch (Throwable throwable) {
//                throwable.printStackTrace();
//            }
//        }
//
//        if(addNewItem) {
//            Item item = new Item();
//            model.addAttribute("editableItem", item);
//        }
//
//        model.addAttribute("pageItems", pageItems);
//        model.addAttribute("minPrice", minPrice);
//        model.addAttribute("maxPrice", maxPrice);
//        model.addAttribute("itemsNumber", itemsNumber);
//
////        model.addAttribute("filters", sb.toString());
//        return "items";
//    }
//
//    @PostMapping("/saveItem")
//    public String saveItem(@ModelAttribute(name = "editableItem") Item item,
//                           @ModelAttribute(name = "filters") String filters) {
//        itemService.saveItem(item);
//        return "redirect:/items?" + filters ;
//    }
//}
