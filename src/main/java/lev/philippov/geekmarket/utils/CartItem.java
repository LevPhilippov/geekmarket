package lev.philippov.geekmarket.utils;

import lev.philippov.geekmarket.Model.Item;

public class CartItem {
    private Item item;
    private int quantity = 0;

    public CartItem(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
