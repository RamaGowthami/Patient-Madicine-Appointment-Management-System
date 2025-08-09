package com.circleconnect.service;

import com.circleconnect.model.UserProfile;
import com.circleconnect.repository.UserProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    private final UserProfileRepository userProfileRepository;
    private final BadgeService badgeService;

    public UserService(UserProfileRepository userProfileRepository, BadgeService badgeService) {
        this.userProfileRepository = userProfileRepository;
        this.badgeService = badgeService;
    }

    @Transactional
    public UserProfile ensureUser(String firebaseUid, String displayName, String photoUrl) {
        return userProfileRepository.findByFirebaseUid(firebaseUid)
                .orElseGet(() -> {
                    UserProfile profile = new UserProfile();
                    profile.setFirebaseUid(firebaseUid);
                    profile.setDisplayName(displayName);
                    profile.setPhotoUrl(photoUrl);
                    profile.setPoints(0);
                    profile.setBadgesCsv("");
                    return userProfileRepository.save(profile);
                });
    }

    @Transactional
    public UserProfile addPoints(String firebaseUid, int points) {
        UserProfile profile = userProfileRepository.findByFirebaseUid(firebaseUid)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        int newPoints = Math.max(0, profile.getPoints() + points);
        profile.setPoints(newPoints);
        List<String> badges = badgeService.computeBadges(newPoints);
        profile.setBadgesCsv(String.join(",", badges));
        return userProfileRepository.save(profile);
    }

    public UserProfile getByUid(String firebaseUid) {
        return userProfileRepository.findByFirebaseUid(firebaseUid)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}