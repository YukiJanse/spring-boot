package se.jensen.yuki.springboot.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String contents;


    @Column(name = "create_at", nullable = false)
    private LocalDateTime createdAt;

    private int likes;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    public Post() {
    }

    public Post(Long id, String contents, LocalDateTime createdAt) {
        this.id = id;
        this.contents = contents;
        this.createdAt = createdAt;
    }

    public Post(Long id, String contents, LocalDateTime createdAt, int likes, User user) {
        this.id = id;
        this.contents = contents;
        this.createdAt = createdAt;
        this.likes = likes;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String text) {
        this.contents = text;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
