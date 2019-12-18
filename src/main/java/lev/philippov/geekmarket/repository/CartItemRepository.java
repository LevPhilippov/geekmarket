package lev.philippov.geekmarket.repository;

import lev.philippov.geekmarket.Model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
