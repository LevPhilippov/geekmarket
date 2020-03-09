package lev.filippov;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQReceiver {

    public static final String TRANSMIT_QUEUE ="transmitQueue";
    public static final String TRANSMIT_EXCHANGER ="transmitExchanger";
    public static final String ANS_QUEUE = "ansQueue";
    public static final String routingKey = "rat";
    public static final String ansRoutingKey = "duck";
    private static Channel channel;

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();
        channel = connection.createChannel();
        channel.exchangeDeclare(TRANSMIT_EXCHANGER, BuiltinExchangeType.TOPIC, false, true,false,null);
        channel.queueDeclare(TRANSMIT_QUEUE, false, false, true, null);
        channel.queueBind(TRANSMIT_QUEUE,TRANSMIT_EXCHANGER,routingKey);

        ScannerApp scannerApp = new ScannerApp();
        scannerApp.startScanner();
        System.out.println(" [*] Waiting for messages");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            scannerApp.addOrderId(message);
        };
        channel.basicConsume(TRANSMIT_QUEUE, true, deliverCallback, consumerTag -> { });
    }


    public static void sendAnswer(String orderId){
        System.out.println("Тест отправки подтверждения заказа № " + orderId);
        try {
            channel.basicPublish(TRANSMIT_EXCHANGER, ansRoutingKey, null, orderId.getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
