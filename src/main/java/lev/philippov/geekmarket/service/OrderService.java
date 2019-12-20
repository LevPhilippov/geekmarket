package lev.philippov.geekmarket.service;

import lev.philippov.geekmarket.Model.Order;
import lev.philippov.geekmarket.Model.OrderInfo;
import lev.philippov.geekmarket.Model.User;
import lev.philippov.geekmarket.repository.CartItemRepository;
import lev.philippov.geekmarket.repository.OrderRepository;
import lev.philippov.geekmarket.utils.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private CartItemRepository cartItemRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, CartItemRepository cartItemRepository) {
        this.orderRepository = orderRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public void saveOrder(User user, Cart cart, OrderInfo orderInfo){
        Order order = new Order(user, cart, orderInfo);
        orderRepository.save(order);
        cart.clear();
    }
}
