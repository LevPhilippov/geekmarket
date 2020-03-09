package lev.philippov.geekmarket.service;

import lev.philippov.geekmarket.Model.UserComment;
import lev.philippov.geekmarket.repository.UserCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCommentService {
    private UserCommentRepository userCommentRepository;

    @Autowired
    public void setUserCommentRepository(UserCommentRepository userCommentRepository) {
        this.userCommentRepository = userCommentRepository;
    }

    public List<UserComment> findUserCommentsByItem_Id(Long itemId) {
        return userCommentRepository.findAllByItem_Id(itemId);
    }

    public void saveComment(UserComment userComment) {
        userCommentRepository.save(userComment);
    }

}
