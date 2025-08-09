package com.circleconnect.config;

import com.circleconnect.service.ChallengeService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    private final ChallengeService challengeService;

    public DataInitializer(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    @PostConstruct
    public void seed() {
        if (challengeService.getCurrentChallenge() == null) {
            challengeService.upsertCurrentWeek(
                    "Share your favorite local spot",
                    "Post a photo or story about a hidden gem in your neighborhood.",
                    50
            );
        }
    }
}