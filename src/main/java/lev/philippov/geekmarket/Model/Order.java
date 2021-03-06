package lev.philippov.geekmarket.Model;

import lev.philippov.geekmarket.utils.Cart;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

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


    @Column(name = "first_name")
    private String firstName;

    @Column(name="lastName")
    private String lastName;

    @Column(name="phone")
    private String phone;

    @Email
    @Column(name = "email")
    private String email;

    @Lob
    @Column(name="address")
    private String address;

    @Lob
    @Column(name = "comment")
    private String comment;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = CascadeType.ALL)
    private Collection<CartItem> cartItems;

    public Order (User user, Cart cart) {
        this.user = user;
        this.cartItems = new ArrayList<>();
        for(CartItem i : cart.getCartItemsMap().values()) {
            i.setOrder(this);
            cartItems.add(i);
        }
        //TODO: можно добавить popup "Использовать данные профиля?"
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.totalPrice = cart.getCartPrice();
    }

    public Order(Cart cart) {
        this.cartItems = new ArrayList<>();
        this.totalPrice = cart.getCartPrice();
        for(CartItem i : cart.getCartItemsMap().values()) {
            i.setOrder(this);
            cartItems.add(i);
        }
    }

    public void prepareForSaving(User user, Cart cart){
        this.user = user;
        this.cartItems = new ArrayList<>();
        cartItems.addAll(cart.getCartItemsMap().values());
        for (CartItem cartItem : cartItems) {
            cartItem.setOrder(this);
        }
        this.totalPrice = cart.getCartPrice();
    }

    public void prepareForSaving(Cart cart) {
        this.cartItems = new ArrayList<>();
        cartItems.addAll(cart.getCartItemsMap().values());
        for (CartItem cartItem : cartItems) {
            cartItem.setOrder(this);
        }
        this.totalPrice = cart.getCartPrice();
    }
}
