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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_info_id")
    private OrderInfo orderInfo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.ALL)
    private List<CartItem> cartItems;


    public Order (User user, Cart cart, OrderInfo orderInfo) {
        this.user = user;
        this.cartItems = new ArrayList<>();
        this.orderInfo=orderInfo;
//        this.orderInfo.setOrder(this);
        for(CartItem i : cart.getCartItemsMap().values()) {
            i.setOrder(this);
            cartItems.add(i);
        }
        this.totalPrice = cart.getCartPrice();
    }
}
