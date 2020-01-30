package lev.philippov.geekmarket.soap;

import lev.philippov.geekmarket.Model.Dto.ItemDto;
import lev.philippov.geekmarket.repository.ItemRepository;
import lev.philippov.geekmarket.soap.catalog.SoapItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class SoapCatalogService {

    private ItemRepository itemRepository;

    @Autowired
    public void setItemRepository(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<SoapItemDto> findAllItemDto(){
        List<SoapItemDto> soapItemDtos = new ArrayList<>();
        List<ItemDto> itemDtos = itemRepository.findAllBy();
        for (ItemDto itemDto : itemDtos) {
            SoapItemDto soapItemDto = new SoapItemDto();
            soapItemDto.setTitle(itemDto.getTitle());
            soapItemDto.setPrice(itemDto.getPrice());
            soapItemDtos.add(soapItemDto);
        }
        return soapItemDtos;
    }
}
