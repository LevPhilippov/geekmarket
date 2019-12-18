package lev.philippov.geekmarket.repository;

import lev.philippov.geekmarket.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
