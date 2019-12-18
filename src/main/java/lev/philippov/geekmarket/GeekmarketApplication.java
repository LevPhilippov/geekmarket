package lev.philippov.geekmarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class GeekmarketApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(GeekmarketApplication.class, args);
    }

}
