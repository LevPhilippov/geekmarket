package lev.philippov.geekmarket;

import lev.philippov.geekmarket.Model.Order;
import lev.philippov.geekmarket.Model.User;
import lev.philippov.geekmarket.controller.CartController;
import lev.philippov.geekmarket.service.OrderService;
import lev.philippov.geekmarket.service.UserService;
import lev.philippov.geekmarket.utils.Cart;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartControllerTest {

    @Mock
    UserService userService;

    @Mock
    OrderService orderService;

    @Mock
    Principal principal;

    @Mock
    Cart cart;

    @Mock
    Model model;

    @InjectMocks
    CartController cartController;

    @Test
    public void showTest(){
        assertEquals("cart", cartController.show(model));
    }

    @Test
    public void deleteTest(){
        assertEquals("redirect:/cart",cartController.deleteFromCart(1L));
        Mockito.verify(cart,times(1)).delete(eq(1L));
    }

    @Test
    public void saveOrderTest(){
        Order order = new Order();
        User user = new User();

        when(principal.getName()).thenReturn("bob");
        when(userService.findByUsername("bob")).thenReturn(Optional.of(user));
        when(orderService.saveOrder(order)).thenReturn(order);
        assertEquals("show_order_details", cartController.saveOrder(order, principal, model));
        Mockito.verify(principal,times(1)).getName();

        Mockito.verify(userService,times(1)).findByUsername(eq("bob"));
        Mockito.verify(cart,times(1)).clear();
        Mockito.verify(orderService,times(1)).saveOrder(eq(order));
        Mockito.verify(model,times(1)).addAttribute(eq(order));
    }


}
