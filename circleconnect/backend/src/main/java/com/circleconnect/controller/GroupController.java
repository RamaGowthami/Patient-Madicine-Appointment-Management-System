package com.circleconnect.controller;

import com.circleconnect.model.Group;
import com.circleconnect.repository.GroupRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupRepository groupRepository;

    public GroupController(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @GetMapping
    public List<Group> list() {
        return groupRepository.findAll();
    }

    @PostMapping
    public Group create(@Valid @RequestBody Group group) {
        return groupRepository.save(group);
    }

    @PostMapping("/{id}/join")
    public ResponseEntity<Void> join(@PathVariable Long id, Authentication auth) {
        // Simplified MVP: no membership table yet
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/leave")
    public ResponseEntity<Void> leave(@PathVariable Long id, Authentication auth) {
        // Simplified MVP: no membership table yet
        return ResponseEntity.ok().build();
    }
}