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

public class HistoryHelper {
    static final int LAST_SHOWED_ITEMS=5;


    public static void updateHistory(HttpServletRequest request, HttpServletResponse response, Long id, ItemService itemService, HttpSession session) {

        Map<String, Cookie> viewedHistory = new HashMap<>();
        if(request.getCookies() != null) {
            for(Cookie c:request.getCookies()) {
                if(c.getName().startsWith("viewed_item_")) {
                    viewedHistory.put(c.getName(),c);
                }
            }
        }
        //если вновь открытый элемент не совпадает ни с одним, уже записанным в cookie или история отсутствует, идем внутрь блока
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

        addCookiesInfoIntoSession(itemService, session, viewedHistory);
    }

    public static void gatherHistoryFromCookies(HttpServletRequest request, ItemService itemService, HttpSession session) {
        Map<String, Cookie> viewedHistory = new HashMap<>();
        if(request.getCookies()!=null) {
            for(Cookie c:request.getCookies()) {
                if(c.getName().startsWith("viewed_item_")) {
                    viewedHistory.put(c.getName(),c);
                }
            }
        }
        addCookiesInfoIntoSession(itemService, session, viewedHistory);
    }

    private static void addCookiesInfoIntoSession(ItemService itemService, HttpSession session, Map<String, Cookie> viewedHistory) {
        if(!viewedHistory.isEmpty()) {
            List<Long> ids = new ArrayList<>();
            for (Cookie value : viewedHistory.values()) {
                ids.add(Long.parseLong(value.getValue()));
            }
            List<Item> items = itemService.findItemsByIds(ids);
            session.setAttribute("items", items);
        }
    }
}
