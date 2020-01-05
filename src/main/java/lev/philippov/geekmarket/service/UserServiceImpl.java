package lev.philippov.geekmarket.service;

import lev.philippov.geekmarket.Model.Role;
import lev.philippov.geekmarket.Model.User;
import lev.philippov.geekmarket.repository.RoleRepository;
import lev.philippov.geekmarket.repository.UserRepository;
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
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public Optional<User> findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username)
                .orElseThrow((Supplier<UsernameNotFoundException>) () -> new UsernameNotFoundException("Wrong username or password!"));
//        System.out.println(user);
//        System.out.println(user.getRoles() != null);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities(user));
    }

    private Collection<? extends GrantedAuthority> authorities (User user) {
        return user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Collection<Role> roles = new ArrayList<>();
        Role simpleUser = roleRepository.findByName("ROLE_USER");
        roles.add(simpleUser);
        user.setRoles(roles);
        return userRepository.save(user);
    }
}
