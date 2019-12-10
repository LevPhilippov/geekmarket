package lev.philippov.geekmarket.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Map;

public class PaginationHelper {

    public static final Integer PAGE_NUMBER_DEF = 0;
    public static final Integer ITEMS_NUMBER_DEF = 10;

    ////Пагинация и навигация по страницам
    public static Pageable getPageable (Map<String, String[]> paramsMap) {
        int pageNumber= (paramsMap.containsKey("pageNumber") && !paramsMap.get("pageNumber")[0].isEmpty()) ? Integer.parseInt(paramsMap.get("pageNumber")[0]): PAGE_NUMBER_DEF;
        int itemsNumber=(paramsMap.containsKey("itemsNumber") && !paramsMap.get("itemsNumber")[0].isEmpty())? Integer.parseInt(paramsMap.get("itemsNumber")[0]): ITEMS_NUMBER_DEF;
        Pageable pageable = PageRequest.of(pageNumber, itemsNumber, Sort.Direction.ASC, "title");
        return pageable;
    }
}
