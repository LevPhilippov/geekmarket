package lev.philippov.geekmarket.utils;

import lev.philippov.geekmarket.Model.CartItem;
import lev.philippov.geekmarket.Model.Item;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Cart {
    private Map<Long, CartItem> cartItemsMap;
    private BigDecimal cartPrice;


    @PostConstruct
    private void init() {
        cartItemsMap = new HashMap<>();
        cartPrice = new BigDecimal(0);
    }

    public void add(Item item) {
        if(cartItemsMap.containsKey(item.getId())){
            CartItem updatableCartItem = cartItemsMap.get(item.getId());
            updatableCartItem.setQuantity(updatableCartItem.getQuantity()+1);
        }
        else
            cartItemsMap.put(item.getId(),new CartItem(item,1));
        recalculate();
    }

    public Map<Long, CartItem> getCartItemsMap() {
        return cartItemsMap;
    }

    public void delete(Long id) {
        if(cartItemsMap.containsKey(id)){
            CartItem updatableCartItem = cartItemsMap.get(id);
            if(updatableCartItem.getQuantity() == 1) {
                cartItemsMap.remove(id);
            } else {
                cartItemsMap.get(id).setQuantity(updatableCartItem.getQuantity()-1);
            }
        }
        recalculate();
    }

    public void clear() {
        cartItemsMap.clear();
        cartPrice = new BigDecimal(0);
    }

    public BigDecimal getCartPrice() {
        return cartPrice;
    }

    private void recalculate() {
        cartItemsMap.values().forEach(cartItem -> cartPrice = cartPrice.add(cartItem.getPosPrice()));
    }
}
