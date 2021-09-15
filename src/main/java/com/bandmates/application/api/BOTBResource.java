package com.bandmates.application.api;

import com.bandmates.application.domain.BOTB;
import com.bandmates.application.domain.Profile;
import com.bandmates.application.service.BOTBService;
import com.bandmates.application.service.ProfileService;
import com.bandmates.application.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BOTBResource {
    private final BOTBService botbService;

    private final UserService userService;

    @GetMapping("/botb")
    public ResponseEntity<List<BOTB>> getAllBOTBs() {
        return ResponseEntity.ok(botbService.getAllBOTBs());
    }

    @GetMapping("/botb/{botbId}")
    public ResponseEntity<BOTB> getBOTB(@PathVariable Long botbId) {
        return ResponseEntity.ok(botbService.getBOTB(botbId));
    }

    @PostMapping("/botb/create")
    public ResponseEntity<BOTB> createBOTB(@RequestBody BOTB botb) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/botb/create").toUriString());
        return ResponseEntity.created(uri).body(botbService.saveBOTB(botb));
    }

    @PutMapping("/botb/update/{id}")
    public ResponseEntity<BOTB> updateBOTB(@RequestBody BOTB botb, @PathVariable Long id) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/botb/update").toUriString());
        return ResponseEntity.created(uri).body(botbService.updateBOTB(botb, id));
    }

    @PostMapping("/botb/users/create/{username}")
    public ResponseEntity<BOTB> createBOTBForUser(@RequestBody BOTB botb, @PathVariable String username) {
        BOTB createdBOTB = botbService.saveBOTB(botb);
        botbService.addBOTBToUser(createdBOTB.getId(), username);
        botbService.addUserToBOTB(createdBOTB.getId(), username);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/botb/users/add/{username}/{botbId}")
    public ResponseEntity<BOTB> addUserToBOTB(@PathVariable Long botbId, @PathVariable String username) {
        BOTB fetchedBOTB = botbService.getBOTB(botbId);
        fetchedBOTB.getUsers().add(userService.getUser(username));
        botbService.saveBOTB(fetchedBOTB);
        return ResponseEntity.ok().build();
    }
}