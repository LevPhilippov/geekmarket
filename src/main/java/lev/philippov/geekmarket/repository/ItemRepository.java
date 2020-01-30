package lev.philippov.geekmarket.repository;

import lev.philippov.geekmarket.Model.Dto.ItemDto;
import lev.philippov.geekmarket.Model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>, JpaSpecificationExecutor {
    public List<ItemDto> findAllBy();
}
