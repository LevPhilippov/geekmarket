package lev.philippov.geekmarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
// 1. История заказов
// 2. Добавить регистрацию с валидацией
// 3. Покупка в 1 клик без регистрации по номеру телефона
// 4. Автоподвязывание по номеру телефона истории заказов (если человек на номер 123
// в виде гостя оформил 20 заказов, и потом зарегался под этим номером, то под его
// новую учетную запись должны подвязаться все эти заказы)


// Домашнее задание:
// 5. Если пользователь купил товар, то он должен иметь возможность оставить отзыв
// о товаре, и выставить оценку

@SpringBootApplication
public class GeekmarketApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(GeekmarketApplication.class, args);
    }
}
