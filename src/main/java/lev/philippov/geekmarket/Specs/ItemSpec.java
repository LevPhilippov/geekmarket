package lev.philippov.geekmarket.Specs;

import lev.philippov.geekmarket.Model.Item;
import org.springframework.data.jpa.domain.Specification;

public class ItemSpec {

    public static Specification<Item> byPriceGreaterThan(Double minPrice) {
        return (Specification<Item>) (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("price"), minPrice);
    }

    public static Specification<Item> byPriceLesserThan(Double maxPrice) {
        return (Specification<Item>) (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get("price"), maxPrice);
    }

    public static Specification<Item> like(String keyword) {
        return (Specification<Item>) (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"),"%"+keyword+"%");
    }

}
