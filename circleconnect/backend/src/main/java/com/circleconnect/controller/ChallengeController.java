package com.circleconnect.controller;

import com.circleconnect.model.Challenge;
import com.circleconnect.model.Post;
import com.circleconnect.repository.PostRepository;
import com.circleconnect.service.ChallengeService;
import com.circleconnect.service.UserService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/challenges")
public class ChallengeController {

    private final ChallengeService challengeService;
    private final UserService userService;
    private final PostRepository postRepository;

    public ChallengeController(ChallengeService challengeService, UserService userService, PostRepository postRepository) {
        this.challengeService = challengeService;
        this.userService = userService;
        this.postRepository = postRepository;
    }

    @GetMapping("/current")
    public ResponseEntity<Challenge> current() {
        Challenge c = challengeService.getCurrentChallenge();
        if (c == null) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(c);
    }

    @PostMapping("/{id}/submit")
    public ResponseEntity<Post> submit(@PathVariable Long id,
                                       Authentication auth,
                                       @RequestBody Map<String, String> body) {
        Challenge c = challengeService.getCurrentChallenge();
        if (c == null || !c.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        String uid = auth.getName();
        String imageUrl = body.getOrDefault("imageUrl", null);
        String story = body.getOrDefault("story", "");
        // award points
        userService.addPoints(uid, c.getPointsReward());
        // create a post
        Post post = new Post();
        post.setAuthorUid(uid);
        post.setType(imageUrl != null && !imageUrl.isBlank() ? Post.PostType.PHOTO : Post.PostType.STORY);
        post.setImageUrl(imageUrl);
        post.setContent(story);
        Post saved = postRepository.save(post);
        return ResponseEntity.ok(saved);
    }
}