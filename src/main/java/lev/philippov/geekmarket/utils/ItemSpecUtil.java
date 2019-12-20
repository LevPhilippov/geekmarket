package lev.philippov.geekmarket.utils;

import lev.philippov.geekmarket.Model.Item;
import lev.philippov.geekmarket.Specs.ItemSpec;
import org.springframework.data.jpa.domain.Specification;

import java.util.HashMap;
import java.util.Map;

public class ItemSpecUtil {

    // Параметры фильтра и спецификации
    public static Map<String, Object> getSpecification (Map<String, String[]> paramsMap) {
            Specification<Item> specs = Specification.where(null);
            StringBuilder filterBuilder = new StringBuilder();

            if(paramsMap.containsKey("keyword") && !paramsMap.get("keyword")[0].isEmpty()) {
                String keyword = paramsMap.get("keyword")[0];
                specs=specs.and(ItemSpec.like(keyword));
            }
            if(paramsMap.containsKey("minPrice") && !paramsMap.get("minPrice")[0].isEmpty()) {
                Double minPrice = Double.parseDouble(paramsMap.get("minPrice")[0]);
                specs = specs.and(ItemSpec.byPriceGreaterThan(minPrice));
                filterBuilder.append("&minPrice=" + minPrice);
            }
            if(paramsMap.containsKey("maxPrice") && !paramsMap.get("maxPrice")[0].isEmpty()) {
                Double maxPrice = Double.parseDouble(paramsMap.get("maxPrice")[0]);
                specs = specs.and(ItemSpec.byPriceLesserThan(maxPrice));
                filterBuilder.append("&maxPrice=" + maxPrice);

            }
            if(paramsMap.containsKey("startsWith") && !paramsMap.get("minPrice")[0].isEmpty()) {

            }
            String filters = filterBuilder.toString();
            Map<String, Object> map = new HashMap<>();
            map.put("specs", specs);
            map.put("filters", filters);
            return map;
        }

}
