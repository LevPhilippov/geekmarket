package lev.philippov.geekmarket.utils;

import lev.philippov.geekmarket.Model.Item;
import lev.philippov.geekmarket.service.ItemService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CookieHelper {
    static final int LAST_SHOWED_ITEMS=5;


    public static List<Item>  updateLastViewedItemsList(HttpServletRequest request, HttpServletResponse response, Long id, ItemService itemService) {
        Map<String, Cookie> viewedHistory = gatherHistoryFromCookies(request);

        //если вновь открытый элемент не совпадает ни с одним, уже записанным в cookie или история отсутствует, идем внутрь блока (в куки value это id просмотренных продуктов)
        if(viewedHistory.values().stream().noneMatch(cookie -> cookie.getValue().equals(id.toString())) || viewedHistory.isEmpty()) {
            if (viewedHistory.size() < LAST_SHOWED_ITEMS) {
                Cookie newCookie = new Cookie("viewed_item_" + (viewedHistory.size() + 1), id.toString());
                newCookie.setPath("/market");
                response.addCookie(newCookie);
            } else {
                for (int i = LAST_SHOWED_ITEMS; i > 2; i--) {
                    Cookie cookie = viewedHistory.get("viewed_item_" + i);
                    if (cookie == null) continue;
                    viewedHistory.get("viewed_item_" + (i - 1)).setValue(cookie.getValue());
                }
                viewedHistory.get("viewed_item_" + LAST_SHOWED_ITEMS).setValue(id.toString());
            }
        }

        System.out.println("---------------------------");
        System.out.println(viewedHistory.values().toString());
        System.out.println("---------------------------");

        return getLastViewedItemsList(itemService, viewedHistory);
    }

    public static Map<String, Cookie> gatherHistoryFromCookies(HttpServletRequest request) {
        Map<String, Cookie> viewedHistory = new HashMap<>();
        if(request.getCookies()!=null) {
            for(Cookie c:request.getCookies()) {
                if(c.getName().startsWith("viewed_item_")) {
                    viewedHistory.put(c.getName(),c);
                }
            }
        }
        return viewedHistory;
    }

    private static List<Item> getLastViewedItemsList(ItemService itemService, Map<String, Cookie> viewedHistory) {
        List<Item> lastViewedItems = null;
        if(!viewedHistory.isEmpty()) {
            List<Long> ids = new ArrayList<>();
            for (Cookie value : viewedHistory.values()) {
                ids.add(Long.parseLong(value.getValue()));
            }
            lastViewedItems = itemService.findItemsByIds(ids);
        }
        return lastViewedItems;
    }

    public static List<Item> getLastViewedItemsList(HttpServletRequest request, ItemService itemService) {
        Map<String, Cookie> viewedHistory = gatherHistoryFromCookies(request);
        List<Item> lastViewedItems = null;
        if(!viewedHistory.isEmpty()) {
            List<Long> ids = new ArrayList<>();
            for (Cookie value : viewedHistory.values()) {
                ids.add(Long.parseLong(value.getValue()));
            }
            lastViewedItems = itemService.findItemsByIds(ids);
        }
        return lastViewedItems;
    }

    public static void removeCookies(HttpServletRequest request, HttpServletResponse response) {
        for(Cookie c: request.getCookies()){
            if(c.getName().startsWith("viewed_item_")) {
                Cookie cookie = new Cookie(c.getName(),c.getValue());
                cookie.setMaxAge(0);
                cookie.setPath(c.getPath());
                response.addCookie(cookie);
            }
        }
    }
}
