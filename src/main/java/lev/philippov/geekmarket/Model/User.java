package lev.philippov.geekmarket.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    @Size(min=3,max=255)
    private String username;

    @Column(name = "password")
    @Size(min=3,max=255)
    private String password;

    @Column(name = "first_name")
    @Size(min=3,max=30)
    private String firstName;

    @Column(name = "last_name")
    @Size(min=3,max=30)
    private String lastName;

    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "phone")
    @Size(min=12,max=12)
    private String phone;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;
}
