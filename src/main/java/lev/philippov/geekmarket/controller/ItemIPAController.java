package lev.philippov.geekmarket.controller;

import lev.philippov.geekmarket.Model.Item;
import lev.philippov.geekmarket.errorHandlers.ItemErrorResponse;
import lev.philippov.geekmarket.errorHandlers.ItemNotFoundException;
import lev.philippov.geekmarket.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/items")
public class ItemIPAController {

    private ItemService itemService;

    @Autowired
    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/")
    public List<Item> getAllItems(){
        return itemService.findllItems();
    }

    @GetMapping("/{id}")
    public Item findById(@PathVariable(required = false) Long id){
        try {
            return itemService.findItemById(id);
        } catch (Throwable throwable) {
            throw new ItemNotFoundException(throwable.getMessage());
        }
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Item addItem(@RequestBody(required = false) Item item) {
        return itemService.saveItem(item);
    }


    @PutMapping("/{id}")
    public Item saveOrUpdate(@RequestBody (required = false) Item item, @PathVariable("id") Long id) {
        return itemService.findById(id).<Item>map(i -> {
            i.setTitle(item.getTitle());
            i.setPrice(item.getPrice());
            return itemService.saveItem(i);
        }).orElseGet(() -> {
            item.setId(id);
            return itemService.saveItem(item);
        });
    }


    @DeleteMapping("/{id}")
    public int deleteItem(@PathVariable("id") Long id) {
        itemService.deleteById(id);
        return HttpStatus.OK.value();
    }

    @ExceptionHandler
    public ResponseEntity<ItemErrorResponse> handleException (ItemNotFoundException e) {
        ItemErrorResponse itemErrorResponse = new ItemErrorResponse();
        itemErrorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        itemErrorResponse.setMessage(e.getMessage());
        itemErrorResponse.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<ItemErrorResponse>(itemErrorResponse, HttpStatus.NOT_FOUND);
    }
}
