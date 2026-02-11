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
        name = "blocks",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_blocks_blocking_blocked",
                columnNames = {"blocking_id", "blocked_id"}
        )
)
public class Block extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "blocking_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserJpaEntity blocking;

    @ManyToOne(optional = false)
    @JoinColumn(name = "blocked_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserJpaEntity blocked;
}