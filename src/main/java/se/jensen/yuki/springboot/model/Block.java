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
        name = "blocks",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_block_blocking_blocked",
                columnNames = {"blocking_id", "blocked_id"}
        )
)
public class Block {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "blocking_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User blocking;

    @ManyToOne(optional = false)
    @JoinColumn(name = "blocked_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User blocked;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;
}