package se.jensen.yuki.springboot.model;

import jakarta.persistence.*;
import lombok.*;
import se.jensen.yuki.springboot.user.infrastructure.persistence.User;

import java.time.Instant;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "refresh_tokens")
public class RefreshToken extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    private String token;

    private Instant expiresAt;

    private boolean revoked;
}
