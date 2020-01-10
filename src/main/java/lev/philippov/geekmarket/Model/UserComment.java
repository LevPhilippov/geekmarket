package lev.philippov.geekmarket.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user_comments")
public class UserComment {

    public UserComment(Integer score, String comment, Item item) {
        this.score=score;
        this.comment=comment;
        this.item=item;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "score")
    private Integer score;

    @Column(name = "comment")
    @Lob
    private String comment;

    @ManyToOne
    @JoinColumn(name = "item_id")
    Item item;
}
