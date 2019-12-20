package lev.philippov.geekmarket.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Data
@NoArgsConstructor
@Table(name = "order_info")
public class OrderInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

//    @OneToOne(mappedBy = "orderInfo")
//    @JoinColumn(name = "order_id")
//    private Order order;

    @Column(name = "first_name")
    private String firstName;

    @Column(name="lastName")
    private String lastName;

    @Column(name="phone")
    private String phone;

    @Email
    @Column(name = "email")
    private String email;

    @Lob
    @Column(name="address")
    private String address;

    @Lob
    @Column(name = "comment")
    private String comment;

    public OrderInfo(User user){
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.phone = user.getPhone();
        this.email=user.getEmail();
    }
}
