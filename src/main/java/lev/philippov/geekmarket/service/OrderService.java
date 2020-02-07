package lev.philippov.geekmarket.service;

import lev.philippov.geekmarket.Model.Order;
import lev.philippov.geekmarket.Model.OrderStatus;
import lev.philippov.geekmarket.Model.User;
import lev.philippov.geekmarket.config.RabbitMQConfig;
import lev.philippov.geekmarket.repository.CartItemRepository;
import lev.philippov.geekmarket.repository.OrderRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private RabbitTemplate rabbitTemplate;

    public OrderService(OrderRepository orderRepository, RabbitTemplate rabbitTemplate) {
        this.orderRepository = orderRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Transactional
    public Order saveOrder(Order order){
        order.setStatus(OrderStatus.RECEIVED);
        Order saved = orderRepository.save(order);
        rabbitTemplate.convertAndSend(RabbitMQConfig.TRANSMIT_EXCHANGER,RabbitMQConfig.routingKey, saved.getId().toString());
        return saved;
    }

    @Transactional
    public boolean changeOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId).get();
        if(order != null) {
            order.setStatus(status);
            flush();
        } else {
            System.out.println("Заказ с id " + orderId + "не найден в базе!");
            return false;
        }
        return order.getStatus().equals(status);
    }

    @Transactional
    public List<Order> findAllByUser(User user) {
       return orderRepository.findAllByUser(user.getId());
    }

    @Transactional
    public List<Order> findAllByPhone(String phone) {
        return orderRepository.findAllByPhone(phone);
    }

    public void flush() {
        orderRepository.flush();
    }

}
