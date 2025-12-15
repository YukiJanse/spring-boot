package se.jensen.yuki.springboot.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        uniqueConstraints = @UniqueConstraint(
                name = "uk_mute_muting_muted",
                columnNames = {"muting_id", "muted_id"}
        )
)
public class Mute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "muting_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User muting;

    @ManyToOne(optional = false)
    @JoinColumn(name = "muted_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User muted;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;
}
