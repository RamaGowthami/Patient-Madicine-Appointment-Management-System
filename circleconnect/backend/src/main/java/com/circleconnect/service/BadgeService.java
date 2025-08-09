package com.circleconnect.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BadgeService {
    public List<String> computeBadges(int totalPoints) {
        List<String> badges = new ArrayList<>();
        if (totalPoints >= 100) badges.add("Bronze");
        if (totalPoints >= 500) badges.add("Silver");
        if (totalPoints >= 1000) badges.add("Gold");
        return badges;
    }
}