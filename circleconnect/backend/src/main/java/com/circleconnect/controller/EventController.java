package com.circleconnect.controller;

import com.circleconnect.model.Event;
import com.circleconnect.repository.EventRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventRepository eventRepository;

    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @GetMapping
    public List<Event> list() { return eventRepository.findAll(); }

    @PostMapping
    public Event create(Authentication auth, @RequestBody Map<String, String> body) {
        String uid = auth.getName();
        Event e = new Event();
        e.setTitle(body.getOrDefault("title", "Untitled Event"));
        e.setDescription(body.getOrDefault("description", ""));
        e.setLocation(body.getOrDefault("location", ""));
        String startsAt = body.getOrDefault("startsAt", Instant.now().toString());
        e.setStartsAt(Instant.parse(startsAt));
        e.setOrganizerUid(uid);
        return eventRepository.save(e);
    }

    @PostMapping("/{id}/rsvp")
    public ResponseEntity<Void> rsvp(@PathVariable Long id, Authentication auth) {
        // Simplified MVP: would store attendance list; no-op for now
        return ResponseEntity.ok().build();
    }
}