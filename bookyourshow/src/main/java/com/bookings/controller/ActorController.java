package com.bookings.controller;

import com.bookings.entity.Actor;
import com.bookings.service.implementation.ActorServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/actor")
public class ActorController {
    private final ActorServiceImpl actorService;

    public ActorController(ActorServiceImpl actorService) {
        this.actorService = actorService;
    }

    @PostMapping
    public void addActor(@RequestBody Actor actor) {
        actorService.addActors(actor);
    }
}
