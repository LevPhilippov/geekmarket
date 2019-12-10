package lev.philippov.geekmarket.utils;

import lev.philippov.geekmarket.Model.Item;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Cart {
    private Map<Long, CartItem> cartItemsMap;

    private List<Item> items;

    @PostConstruct
    private void init() {
        cartItemsMap = new HashMap<>();
        items = new ArrayList<>();
    }

    public void add(Item item) {
        if(cartItemsMap.containsKey(item.getId())){
            cartItemsMap.get(item.getId()).setQuantity(cartItemsMap.get(item.getId()).getQuantity()+1);
        }
        else
            cartItemsMap.put(item.getId(),new CartItem(item,1));

        items.add(item);
        Long key = item.getId();
    }


    public List<Item> getItems(){
        return items;
    }

    public Map<Long, CartItem> getCartItemsMap() {
        return cartItemsMap;
    }

    public void delete(Long id) {
        if(cartItemsMap.containsKey(id)){
            int quantity = cartItemsMap.get(id).getQuantity();
            if(quantity == 1) {
                cartItemsMap.remove(id);
            } else {
                cartItemsMap.get(id).setQuantity(--quantity);
            }
        }
    }
}
