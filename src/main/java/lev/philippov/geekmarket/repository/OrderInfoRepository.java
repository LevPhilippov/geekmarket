package lev.philippov.geekmarket.repository;

import lev.philippov.geekmarket.Model.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderInfoRepository extends JpaRepository<OrderInfo, Long> {
}
