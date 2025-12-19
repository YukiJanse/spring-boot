INSERT INTO app_user (username, email, password, role, display_name, bio, avatar_url)
VALUES ('alice', 'alice@example.com', '$2a$10$dummyhash1', 'USER', 'Alice', 'Hello, I am Alice', NULL),
       ('bob', 'bob@example.com', '$2a$10$dummyhash2', 'USER', 'Bob', 'Bob here!', NULL),
       ('carol', 'carol@example.com', '$2a$10$dummyhash3', 'USER', 'Carol', 'Coffee lover ☕', NULL),
       ('admin', 'admin@example.com', '$2a$10$dummyhash4', 'ADMIN', 'Admin', 'System admin', NULL);

INSERT INTO posts (content, user_id, original_post_id)
VALUES ('Hello world!', 1, NULL),            -- post id = 1
       ('Spring Boot is awesome!', 2, NULL), -- post id = 2
       ('Good morning everyone ☀️', 3, NULL),
       ('RT: Hello world!', 2, 1); -- repost

INSERT INTO comments (content, user_id, post_id, parent_comment_id)
VALUES ('Nice post!', 2, 1, NULL), -- comment id = 1
       ('Thank you!', 1, 1, 1),    -- reply to comment 1
       ('Agree!', 3, 2, NULL);


INSERT INTO post_likes (user_id, post_id)
VALUES (2, 1),
       (3, 1),
       (1, 2);

INSERT INTO comment_likes (user_id, comment_id)
VALUES (1, 1),
       (3, 1),
       (2, 3);

INSERT INTO follows (follower_id, followed_id)
VALUES (1, 2), -- Alice follows Bob
       (1, 3), -- Alice follows Carol
       (2, 1), -- Bob follows Alice
       (3, 2); -- Carol follows Bob

INSERT INTO blocks (blocking_id, blocked_id)
VALUES (3, 1); -- Carol blocks Alice

INSERT INTO refresh_tokens (user_id, token, expires_at, revoked)
VALUES (1, 'refresh-token-alice', DATE_ADD(NOW(), INTERVAL 7 DAY), FALSE),
       (2, 'refresh-token-bob', DATE_ADD(NOW(), INTERVAL 7 DAY), FALSE),
       (3, 'refresh-token-carol', DATE_ADD(NOW(), INTERVAL 7 DAY), TRUE);

