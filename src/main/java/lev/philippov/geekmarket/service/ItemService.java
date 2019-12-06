package lev.philippov.geekmarket.service;

import lev.philippov.geekmarket.Model.Item;
import lev.philippov.geekmarket.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.SQLDataException;
import java.util.function.Supplier;

@Service
public class ItemService {

    private ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Page<Item> getPagableAndFilteredItems(Specification<Item> specification, Pageable pageable){
        return itemRepository.findAll(specification, pageable);
    }

    public Item findItemById(Long id) throws Throwable {
        return itemRepository.findById(id).orElseThrow((Supplier<Throwable>) () -> new SQLDataException());
    }

    public void saveItem(Item item){
        itemRepository.save(item);
    }
}
