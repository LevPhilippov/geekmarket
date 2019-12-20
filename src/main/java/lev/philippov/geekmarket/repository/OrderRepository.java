package lev.philippov.geekmarket.repository;

import lev.philippov.geekmarket.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT o from Order o WHERE o.user.id=?1")
    List<Order> findAllByUser(Long user_id);

}
