package se.jensen.yuki.springboot.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import se.jensen.yuki.springboot.user.infrastructure.persistence.UserJpaEntity;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "mutes",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_mutes_muting_muted",
                columnNames = {"muting_id", "muted_id"}
        )
)
public class Mute extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "muting_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserJpaEntity muting;

    @ManyToOne(optional = false)
    @JoinColumn(name = "muted_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserJpaEntity muted;
}
