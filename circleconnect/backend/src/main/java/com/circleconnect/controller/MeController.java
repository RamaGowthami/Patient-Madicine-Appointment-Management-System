package com.circleconnect.controller;

import com.circleconnect.model.UserProfile;
import com.circleconnect.service.UserService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/me")
public class MeController {

    private final UserService userService;

    public MeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserProfile> getMe(Authentication authentication) {
        String uid = authentication.getName();
        UserProfile profile = userService.getByUid(uid);
        return ResponseEntity.ok(profile);
    }

    @PostMapping("/ensure")
    public ResponseEntity<UserProfile> ensureMe(Authentication authentication, @RequestBody Map<String, String> body) {
        String uid = authentication.getName();
        String displayName = body.getOrDefault("displayName", "");
        String photoUrl = body.getOrDefault("photoUrl", "");
        UserProfile profile = userService.ensureUser(uid, displayName, photoUrl);
        return ResponseEntity.ok(profile);
    }
}