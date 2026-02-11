package se.jensen.yuki.springboot.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import se.jensen.yuki.springboot.user.infrastructure.jpa.UserJpaEntity;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "posts")
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserJpaEntity user;

    @ManyToOne
    @JoinColumn(name = "original_post_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Post originalPost;

}
