package lev.philippov.geekmarket.service;

import lev.philippov.geekmarket.Model.Item;
import lev.philippov.geekmarket.Specs.ItemSpec;
import lev.philippov.geekmarket.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.SQLDataException;
import java.util.Map;
import java.util.function.Supplier;

@Service
public class ItemService {

    private ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Page<Item> getPagableAndFilteredItems(Map paramsMap){
        Specification<Item> specs = Specification.where(null);
        StringBuilder sb = new StringBuilder();
        System.out.println(sb.toString());

        if(paramsMap.get("minPrice") != null && (Integer)paramsMap.get("minPrice") > 0) {
            specs = specs.and(ItemSpec.byPriceGreaterThan((Integer) paramsMap.get("minPrice")));
            sb.append("&minPrice=" + (Integer) paramsMap.get("minPrice"));
        }
        if(paramsMap.get("maxPrice") != null) {
            specs = specs.and(ItemSpec.byPriceLesserThan((Integer) paramsMap.get("maxPrice")));
            sb.append("&maxPrice=" + (Integer) paramsMap.get("maxPrice"));
        }
        Pageable pageable = PageRequest.of((Integer) paramsMap.get("pageNumber"), (Integer) paramsMap.get("itemsNumber"), Sort.Direction.ASC, "title");
        System.out.println(sb.toString());
        return itemRepository.findAll(specs, pageable);
    }

    public Item findItemById(Long id) throws Throwable {
        return itemRepository.findById(id).orElseThrow((Supplier<Throwable>) () -> new SQLDataException());
    }

    public void saveItem(Item item){
        itemRepository.save(item);
    }
}
