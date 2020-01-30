package lev.philippov.geekmarket.repository;

import lev.philippov.geekmarket.Model.Item;
import lev.philippov.geekmarket.Model.UserComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCommentRepository extends JpaRepository<UserComment, Long> {
    List<UserComment> findAllByItem_Id(Long id);
}
