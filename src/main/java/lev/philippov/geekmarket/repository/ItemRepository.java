package lev.philippov.geekmarket.repository;

import lev.philippov.geekmarket.Model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ItemRepository extends JpaRepository<Item, Long>, JpaSpecificationExecutor {

}
