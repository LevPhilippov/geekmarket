package lev.philippov.geekmarket.config;

import lev.philippov.geekmarket.service.RabbitMQService;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String TRANSMIT_QUEUE ="transmitQueue";
    public static final String ANS_QUEUE = "ansQueue";
//    public static final String EXCHANGE_NAME="directExchanger";
    public static final String TRANSMIT_EXCHANGER ="transmitExchanger";
    public static final String routingKey = "rat";
    public static final String ansRoutingKey = "duck";


    @Bean
    public Queue getQueue() {
        return new Queue(TRANSMIT_QUEUE, false, false, true);
    }

    @Bean(name = "ansQueue")
    public Queue getAnsQueue(){
        return new Queue(ANS_QUEUE, false, false, true);
    }

//    @Bean(name = "directExchanger1")
//    public DirectExchange DirectExchange(){
//        return new DirectExchange(EXCHANGE_NAME,false,true);
//    }

//    @Bean(name = "directExchanger2")
//    public DirectExchange TransmitExchange(){
//        return new DirectExchange(TRANSMIT_EXCHANGER,false,true);
//    }

    @Bean(name="topicExchanger")
    public TopicExchange topicExchange(){
        return new TopicExchange(TRANSMIT_EXCHANGER, false, true);
    }

    @Bean
    public Binding bindingDirectExchanger(@Qualifier(value = "ansQueue") Queue queue, TopicExchange topicExchange){
        return BindingBuilder.bind(queue).to(topicExchange).with(ansRoutingKey);
    }

    @Bean
    SimpleMessageListenerContainer containerForTransmitter(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(ANS_QUEUE);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(RabbitMQService service) {
        return new MessageListenerAdapter(service, "receiveMessage");
    }

//    @Bean
//    RabbitMQService rabbitMQService(){
//        return new RabbitMQService();
//    }

}
