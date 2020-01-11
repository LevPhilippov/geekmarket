package lev.philippov.geekmarket.repository;

import lev.philippov.geekmarket.Model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findUserByUsername(String name);
    Optional<User> findByPhone(String phone);
    Optional<User> findByEmail(String email);
}
