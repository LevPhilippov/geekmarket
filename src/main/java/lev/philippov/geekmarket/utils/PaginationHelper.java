package lev.philippov.geekmarket.utils;

import lev.philippov.geekmarket.controller.ItemController;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Map;

public class PaginationHelper {
    ////Пагинация и навигация по страницам
    public static Pageable getPageable (Map<String, String[]> paramsMap) {
        int pageNumber= (paramsMap.containsKey("pageNumber") && !paramsMap.get("pageNumber")[0].isEmpty()) ? Integer.parseInt(paramsMap.get("pageNumber")[0]): ItemController.PAGE_NUMBER_DEF;
        int itemsNumber=(paramsMap.containsKey("itemsNumber") && !paramsMap.get("itemsNumber")[0].isEmpty())? Integer.parseInt(paramsMap.get("itemsNumber")[0]): ItemController.ITEMS_NUMBER_DEF;
        Pageable pageable = PageRequest.of(pageNumber, itemsNumber, Sort.Direction.ASC, "title");
        return pageable;
    }
}
