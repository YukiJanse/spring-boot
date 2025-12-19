-- ========================================
-- Users
-- ========================================
CREATE TABLE users
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    username     VARCHAR(255) NOT NULL UNIQUE,
    email        VARCHAR(255) NOT NULL UNIQUE,
    password     VARCHAR(255) NOT NULL,
    role         VARCHAR(50)  NOT NULL,
    display_name VARCHAR(255) NOT NULL,
    bio          VARCHAR(500) NOT NULL,
    avatar_url   VARCHAR(255),
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ========================================
-- Posts
-- ========================================
CREATE TABLE posts
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    content          TEXT   NOT NULL,
    user_id          BIGINT NOT NULL,
    original_post_id BIGINT,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_posts_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_posts_original
        FOREIGN KEY (original_post_id)
            REFERENCES posts (id)
            ON DELETE CASCADE
);

-- ========================================
-- Comments
-- ========================================
CREATE TABLE comments
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    content           TEXT,
    user_id           BIGINT  NOT NULL,
    post_id           BIGINT  NOT NULL,
    parent_comment_id BIGINT,
    deleted           BOOLEAN NOT NULL DEFAULT FALSE,
    edited            BOOLEAN NOT NULL DEFAULT FALSE,
    created_at        TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    updated_at        TIMESTAMP        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_comments_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_comments_post
        FOREIGN KEY (post_id)
            REFERENCES posts (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_comments_parent
        FOREIGN KEY (parent_comment_id)
            REFERENCES comments (id)
            ON DELETE CASCADE
);

-- ========================================
-- PostLikes
-- ========================================
CREATE TABLE post_likes
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id    BIGINT NOT NULL,
    post_id    BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_post_likes_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_post_likes_post
        FOREIGN KEY (post_id)
            REFERENCES posts (id)
            ON DELETE CASCADE,

    CONSTRAINT uk_post_likes_user_post UNIQUE (user_id, post_id)
);

-- ========================================
-- CommentLikes
-- ========================================
CREATE TABLE comment_likes
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id    BIGINT NOT NULL,
    comment_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_comment_likes_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_comment_likes_comment
        FOREIGN KEY (comment_id)
            REFERENCES comments (id)
            ON DELETE CASCADE,

    CONSTRAINT uk_comment_likes_user_comment UNIQUE (user_id, comment_id)
);

-- ========================================
-- Follows
-- ========================================
CREATE TABLE follows
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    follower_id BIGINT NOT NULL,
    followed_id BIGINT NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_follows_follower
        FOREIGN KEY (follower_id)
            REFERENCES users (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_follows_followed
        FOREIGN KEY (followed_id)
            REFERENCES users (id)
            ON DELETE CASCADE,

    CONSTRAINT uk_follows_follower_followed UNIQUE (follower_id, followed_id)
);
-- ========================================
-- Mutes
-- ========================================
CREATE TABLE mutes
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    muting_id  BIGINT NOT NULL,
    muted_id   BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_mutes_muting
        FOREIGN KEY (muting_id)
            REFERENCES users (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_mutes_muted
        FOREIGN KEY (muted_id)
            REFERENCES users (id)
            ON DELETE CASCADE,

    CONSTRAINT uk_mutes_muting_muted UNIQUE (muting_id, muted_id)
);
-- ========================================
-- Blocks
-- ========================================
CREATE TABLE blocks
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    blocking_id BIGINT NOT NULL,
    blocked_id  BIGINT NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_blocks_blocking
        FOREIGN KEY (blocking_id)
            REFERENCES users (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_blocks_blocked
        FOREIGN KEY (blocked_id)
            REFERENCES users (id)
            ON DELETE CASCADE,

    CONSTRAINT uk_blocks_blocking_blocked UNIQUE (blocking_id, blocked_id)
);

-- ========================================
-- RefreshTokens
-- ========================================
CREATE TABLE refresh_tokens
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id    BIGINT       NOT NULL,
    token      VARCHAR(255) NOT NULL,
    expires_at TIMESTAMP,
    revoked    BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP             DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP             DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_refresh_tokens_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE CASCADE
);
