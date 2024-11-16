package com.bookings.controller;

import com.bookings.dto.ActorDto;
import com.bookings.entity.Actor;
import com.bookings.service.implementation.ActorServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/actor")
public class ActorController {
    private final ActorServiceImpl actorService;

    public ActorController(ActorServiceImpl actorService) {
        this.actorService = actorService;
    }

    @PostMapping
    public ResponseEntity<ActorDto> addActor(@RequestBody Actor actor) {
        return actorService.addActors(actor);
    }

    @GetMapping("/get-actor/{actorId}")
    public ResponseEntity<ActorDto> getActorById(@PathVariable("actorId") UUID actorId) {
        return actorService.getActorById(actorId);
    }

    @GetMapping("/get-actor")
    public ResponseEntity<List<ActorDto>> getAllActors() {
        return actorService.getAllActors();
    }

}
