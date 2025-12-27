package se.jensen.yuki.springboot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.jensen.yuki.springboot.service.LikeService;

@RestController
@RequestMapping("/comments/likes")
@RequiredArgsConstructor
public class CommentLikeController {
    private final LikeService likeService;
}
