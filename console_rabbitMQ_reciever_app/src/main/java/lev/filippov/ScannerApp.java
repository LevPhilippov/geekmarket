package lev.filippov;

import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ScannerApp {
    private BlockingQueue<String> queue = new ArrayBlockingQueue<>(5);
    private Thread scannerThread;

    public void startScanner() {
        this.scannerThread = new Thread(() -> {
            Scanner scanner = new Scanner(System.in, "UTF-8");
            String message = null;
                while (true) {
                    try {
                        System.out.println("Размер очереди до изъятия " + queue.size() + ". Ожидаем сообщений.");
                        message = queue.take();
                        System.out.println("Размер очереди после изъятия " + queue.size());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Do you confirm an order #" + message + " ?");
                    scanner.hasNext();
                    String answer = scanner.nextLine();
                    if(answer.toLowerCase().trim().equals("yes")){
                        System.out.println("Вы подвердили заказ номер " + message);
                        RabbitMQReceiver.sendAnswer(message);
                    }
                }
        });

        scannerThread.start();
    }

    public void addOrderId(String orderId){
        try {
            queue.put(orderId);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
