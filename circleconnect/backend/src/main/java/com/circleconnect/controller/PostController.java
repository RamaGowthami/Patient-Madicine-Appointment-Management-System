package com.circleconnect.controller;

import com.circleconnect.model.Post;
import com.circleconnect.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping
    public Page<Post> list(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "20") int size) {
        return postRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size));
    }

    @PostMapping
    public ResponseEntity<Post> create(Authentication auth, @RequestBody Map<String, String> body) {
        String uid = auth.getName();
        String type = body.getOrDefault("type", "STORY");
        String content = body.getOrDefault("content", "");
        String imageUrl = body.getOrDefault("imageUrl", null);
        Post post = new Post();
        post.setAuthorUid(uid);
        post.setType(Post.PostType.valueOf(type));
        post.setContent(content);
        post.setImageUrl(imageUrl);
        return ResponseEntity.ok(postRepository.save(post));
    }
}