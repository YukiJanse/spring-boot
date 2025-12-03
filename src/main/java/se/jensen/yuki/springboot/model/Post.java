package se.jensen.yuki.springboot.model;

import java.time.LocalDateTime;

public class Post {
    private Long id;
    private String text;
    private LocalDateTime createdAt;

    public Post() {
    }

    public Post(Long id, String text, LocalDateTime createdAt) {
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
