package lev.philippov.geekmarket.service;

import lev.philippov.geekmarket.Model.Order;
import lev.philippov.geekmarket.Model.Role;
import lev.philippov.geekmarket.Model.User;
import lev.philippov.geekmarket.errorHandlers.UserAlreadyExistException;
import lev.philippov.geekmarket.repository.RoleRepository;
import lev.philippov.geekmarket.repository.UserRepository;
import org.hibernate.NonUniqueResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private OrderService orderService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder, OrderService orderService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.orderService = orderService;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public Optional<User> findByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    @Override
    @Transactional
    public boolean checkUniqueness(User user) {
        if (findByUsername(user.getUsername()).isPresent())
            throw new UserAlreadyExistException("User " + user.getUsername() + " already exists!");
        if (userRepository.findByEmail(user.getEmail()).isPresent())
            throw new UserAlreadyExistException("User with email " + user.getEmail() + " already exists!");
        return true;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username)
                .orElseThrow((Supplier<UsernameNotFoundException>) () -> new UsernameNotFoundException("Wrong username or password!"));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities(user));
    }

    private Collection<? extends GrantedAuthority> authorities (User user) {
        return user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public User saveUser(User user) {
//        Подвязываем пароль, и роли
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Collection<Role> roles = new ArrayList<>();
        Role simpleUser = roleRepository.findByName("ROLE_USER");
        roles.add(simpleUser);
        user.setRoles(roles);
        user = userRepository.save(user);
//        Подвязываем заказы (при наличии)
        List<Order> orderList = orderService.findAllByPhone(user.getPhone());
        for (Order order : orderList) {
            order.setUser(user);
        }
        orderService.flush();
        return user;
    }




}
