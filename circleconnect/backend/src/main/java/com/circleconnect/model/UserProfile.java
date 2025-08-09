package com.circleconnect.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_profiles")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String firebaseUid;

    private String displayName;
    private String photoUrl;

    private int points;

    // comma-separated badge names for simplicity
    private String badgesCsv;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirebaseUid() { return firebaseUid; }
    public void setFirebaseUid(String firebaseUid) { this.firebaseUid = firebaseUid; }

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }

    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }

    public String getBadgesCsv() { return badgesCsv; }
    public void setBadgesCsv(String badgesCsv) { this.badgesCsv = badgesCsv; }
}