package lev.philippov.geekmarket.service;

import lev.philippov.geekmarket.Model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {
        Optional<User> findByUsername(String username);
        Optional<User> findByPhone(String phone);
        User saveUser(User user);
        boolean checkUniqueness(User user);
}
