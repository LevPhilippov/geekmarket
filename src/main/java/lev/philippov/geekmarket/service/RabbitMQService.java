package lev.philippov.geekmarket.service;

import lev.philippov.geekmarket.Model.OrderStatus;
import lev.philippov.geekmarket.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class RabbitMQService {

    private OrderService orderService;

    @Autowired
    public RabbitMQService(OrderService orderService) {
        this.orderService = orderService;
    }

    public void receiveMessage(Object object) {
        if(object instanceof byte[]) {
            String message = new String((byte[])object, StandardCharsets.UTF_8);
            System.out.println(this.getClass() + ": Received from topic <" + message + ">");
            System.out.println("Changing order status to " + OrderStatus.CONFIRMED);
            boolean result = orderService.changeOrderStatus(Long.parseLong(message), OrderStatus.CONFIRMED);
            if(result) System.out.println("Operation completed! Status of order " + message + " was successfully changed!");

        } else if (object instanceof String) {
            System.out.println(this.getClass() + ": Received from topic <" + (String) object + ">");
        }
    }

//    public void sendMessageToExternalApp(Long orderId){
//        rabbitTemplate.convertAndSend(RabbitMQConfig.TRANSMIT_EXCHANGER,RabbitMQConfig.routingKey, orderId.toString());
//    }


}
