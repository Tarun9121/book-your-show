package com.bookings.service.implementation;

import com.bookings.constants.BookYourShow;
import com.bookings.convert.ActorConvert;
import com.bookings.dto.ActorDto;
import com.bookings.entity.Actor;
import com.bookings.exception.ApiException;
import com.bookings.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

@Service
public class ActorServiceImpl {
    private final ActorRepository actorRepository;
    private final ActorConvert actorConvert;

    @Autowired
    public ActorServiceImpl(ActorRepository actorRepository, ActorConvert actorConvert) {
        this.actorRepository = actorRepository;
        this.actorConvert = actorConvert;
    }

    public ResponseEntity<ActorDto> addActors(Actor actor) {
        ActorDto savedActorDto = new ActorDto();
        try {
            if(!ObjectUtils.isEmpty(actor)) {
                Actor existingActor = actorRepository.findByNameAndDOB(actor.getName(), actor.getDateOfBirth());
                if(ObjectUtils.isEmpty(existingActor)) {
                    Actor savedActor = actorRepository.save(actor);
                    savedActorDto = actorConvert.convert(savedActor);
                    return ResponseEntity.status(HttpStatus.OK).body(savedActorDto);
                } else {
                    throw new ApiException(BookYourShow.ACTOR_ALREADY_EXIST);
                }
            } else {
                throw new ApiException(BookYourShow.DATA_NULL);
            }
        } catch (Exception e) {
            savedActorDto.setStatus(HttpStatus.BAD_REQUEST);
            savedActorDto.setMessage(e.getMessage());
            savedActorDto.setLocalDateTime(LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(savedActorDto);
        }
    }

    public ResponseEntity<ActorDto> getActorById(UUID actorId) {
        ActorDto actorDto = new ActorDto();
        try {
            Actor actor = actorRepository.findById(actorId).orElseThrow(() -> new ApiException(BookYourShow.ACTOR_NOT_FOUND));
            actorDto = actorConvert.convert(actor);
            return ResponseEntity.status(HttpStatus.OK).body(actorDto);
        } catch(Exception e) {
            actorDto.setStatus(HttpStatus.BAD_REQUEST);
            actorDto.setMessage(e.getMessage());
            actorDto.setLocalDateTime(LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(actorDto);
        }
    }

    public ResponseEntity<List<ActorDto>> getAllActors() {
        try {
            List<Actor> allActors = actorRepository.findAll();
            if(CollectionUtils.isEmpty(allActors)) {
                return ResponseEntity.noContent().build();
            }
            List<ActorDto> actorDtoList = actorConvert.convert(allActors);
            return ResponseEntity.status(HttpStatus.OK).body(actorDtoList);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
