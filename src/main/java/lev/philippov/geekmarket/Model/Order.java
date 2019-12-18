package lev.philippov.geekmarket.Model;

import lev.philippov.geekmarket.utils.Cart;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = CascadeType.ALL)
    private List<CartItem> cartItems;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    public Order (User user, Cart cart) {
        this.user = user;
        this.cartItems = new ArrayList<>();
        for(CartItem i : cart.getCartItemsMap().values()) {
            i.setOrder(this);
            cartItems.add(i);
        }
        this.totalPrice = cart.getCartPrice();
    }
}
