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
        name = "follows",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_follows_follower_followed",
                columnNames = {"follower_id", "followed_id"}
        )
)
public class Follow extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "follower_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserJpaEntity follower;

    @ManyToOne(optional = false)
    @JoinColumn(name = "followed_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserJpaEntity followed;
}
