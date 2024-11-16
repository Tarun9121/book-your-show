package com.bookings.convert;

import com.bookings.dto.ActorDto;
import com.bookings.entity.Actor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class ActorConvert {
    public ActorDto convert(Actor actor) {
        ActorDto actorDto = new ActorDto();

        if(!ObjectUtils.isEmpty(actor)) {
            BeanUtils.copyProperties(actor, actorDto);
        }
        return actorDto;
    }

    public List<ActorDto> convert(List<Actor> actorList) {
        List<ActorDto> actorDtoList = new ArrayList();

        if(!CollectionUtils.isEmpty(actorList)) {
            BeanUtils.copyProperties(actorList, actorDtoList);
        }

        return actorDtoList;
    }
}
