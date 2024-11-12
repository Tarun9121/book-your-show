package com.bookings.service.implementation;

import com.bookings.constants.BookYourShow;
import com.bookings.entity.Actor;
import com.bookings.exception.ApiException;
import com.bookings.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class ActorServiceImpl {
    private final ActorRepository actorRepository;
    @Autowired
    public ActorServiceImpl(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    public void addActors(Actor actor) {
        try {
            if(!ObjectUtils.isEmpty(actor)) {
                Actor existingActor = actorRepository.findByNameAndDOB(actor.getName(), actor.getDateOfBirth());
                if(ObjectUtils.isEmpty(existingActor)) {
                    actorRepository.save(actor);
                } else {
                    throw new ApiException(BookYourShow.ACTOR_ALREADY_EXIST);
                }
            } else {
                throw new ApiException(BookYourShow.DATA_NULL);
            }
        } catch (Exception e) {
//            have to update the catch block
            throw new RuntimeException(e);
        }
    }
}
