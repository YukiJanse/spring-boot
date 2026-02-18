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
@Table(
        name = "comment_likes",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_comment_likes_user_comment",
                columnNames = {"user_id", "comment_id"}
        )
)
public class CommentLike extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserJpaEntity user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "comment_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Comment comment;
}
