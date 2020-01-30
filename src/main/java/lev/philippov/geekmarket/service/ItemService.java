package lev.philippov.geekmarket.service;

import lev.philippov.geekmarket.Model.Item;
import lev.philippov.geekmarket.Model.UserComment;
import lev.philippov.geekmarket.errorHandlers.ItemNotFoundException;
import lev.philippov.geekmarket.repository.ItemRepository;
import lev.philippov.geekmarket.repository.UserCommentRepository;
import lev.philippov.geekmarket.utils.HistoryHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class ItemService {

    private ItemRepository itemRepository;
    private UserCommentRepository userCommentRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository, UserCommentRepository userCommentRepository) {
        this.itemRepository = itemRepository;
        this.userCommentRepository = userCommentRepository;
    }

    public Page<Item> getPagableAndFilteredItems(Specification<Item> specification, Pageable pageable){
        return itemRepository.findAll(specification, pageable);
    }

    public Item findItemById(Long id) throws Throwable {
        return itemRepository.findById(id).orElseThrow(new Supplier<Throwable>() {
            @Override
            public Throwable get() {
                return new ItemNotFoundException("Item with id:" + id + " is abscent in database.");
            }
        });
    }

    public List<Item> findItemsByIds(List<Long> ids){
        return itemRepository.findAllById(ids);
    }

    public Float getItemRating(Item item) {
        Float rating = null;
        List<UserComment> comments = userCommentRepository.findAllByItem_Id(item.getId());
        if(comments != null) {
            float score=0;
            for (UserComment comment: comments) {
                score += comment.getScore();
            }
            rating = score/comments.size();
        }
        return rating;
        //TODO попробовать вынести на уровень БД
    }

    public void updateHistory(HttpServletRequest request, HttpServletResponse response, Long id, HttpSession session) {
        HistoryHelper.updateHistory(request,response, id, this,session);
    }

    public Item saveItem(Item item){
        return itemRepository.save(item);
    }

    public List<Item> findllItems() {
        return (List) itemRepository.findAll();
    }

    public Optional<Item> findById(Long id) {
        return itemRepository.findById(id);
    }

    public void deleteById(Long id) {
        itemRepository.deleteById(id);
    }

    public void flush(){
        itemRepository.flush();
    }
}
