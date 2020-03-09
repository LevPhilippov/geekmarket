package lev.philippov.geekmarket;

import lev.philippov.geekmarket.Model.CartItem;
import lev.philippov.geekmarket.Model.Order;
import lev.philippov.geekmarket.Model.User;
import lev.philippov.geekmarket.controller.CartController;
import lev.philippov.geekmarket.service.OrderService;
import lev.philippov.geekmarket.service.UserService;
import lev.philippov.geekmarket.utils.Cart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
//@SpringBootTest
//@AutoConfigureMockMvc
@WebMvcTest(CartController.class)
public class CartControllerMvcTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;
//
    @MockBean
    Cart cart;
//
    @MockBean
    OrderService orderService;

    @MockBean
    Principal principal;

//    @MockBean
//    Model model;

    @BeforeEach
    public void init() {
        given(this.cart.getCartItemsMap()).willReturn(new HashMap<Long, CartItem>());
        given(this.orderService.saveOrder(any())).willReturn(null);
        given(this.userService.findByUsername(any())).willReturn(java.util.Optional.of(new User()));
        given(this.principal.getName()).willReturn("Bill");
//        given(this.model.addAttribute(any())).willReturn(null);
    }

    @Test
    public void showMvcTest() throws Exception {
        mockMvc.perform(get("/cart"))
                .andExpect(status().isOk()).andExpect(content().string(containsString("Geekmarket Cart")));
    }

    @Test
    public void deleteFromCartTest() throws Exception {
        mockMvc.perform(get("/cart/delete").param("id", "1"))
                .andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/cart"));
    }

    @Test
    public void saveOrderTest() throws Exception {
        mockMvc.perform(post("/cart/save").requestAttr("order", new Order()))
                .andExpect(status().isOk());
    }

    @Test
    public void oneClickPurchaseTest() throws Exception {
        mockMvc.perform(post("/oneclick").param("phone", "1234567890"))
                .andExpect(status().isOk());
    }
}
