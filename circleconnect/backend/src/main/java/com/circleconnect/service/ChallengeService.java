package com.circleconnect.service;

import com.circleconnect.model.Challenge;
import com.circleconnect.repository.ChallengeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class ChallengeService {
    private final ChallengeRepository challengeRepository;

    public ChallengeService(ChallengeRepository challengeRepository) {
        this.challengeRepository = challengeRepository;
    }

    public Challenge getCurrentChallenge() {
        LocalDate today = LocalDate.now();
        return challengeRepository
                .findByWeekStartLessThanEqualAndWeekEndGreaterThanEqual(today, today)
                .orElse(null);
    }

    @Transactional
    public Challenge upsertCurrentWeek(String title, String description, int pointsReward) {
        LocalDate today = LocalDate.now();
        LocalDate weekStart = today.minusDays(today.getDayOfWeek().getValue() - 1);
        LocalDate weekEnd = weekStart.plusDays(6);
        Challenge existing = getCurrentChallenge();
        if (existing != null) {
            existing.setTitle(title);
            existing.setDescription(description);
            existing.setPointsReward(pointsReward);
            return challengeRepository.save(existing);
        }
        Challenge challenge = new Challenge();
        challenge.setTitle(title);
        challenge.setDescription(description);
        challenge.setWeekStart(weekStart);
        challenge.setWeekEnd(weekEnd);
        challenge.setPointsReward(pointsReward);
        return challengeRepository.save(challenge);
    }
}