package com.circleconnect.repository;

import com.circleconnect.model.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    Optional<Challenge> findByWeekStartLessThanEqualAndWeekEndGreaterThanEqual(LocalDate start, LocalDate end);
}