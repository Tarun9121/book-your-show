package com.bookings.service.implementation;

import com.bookings.constants.BookYourShow;
import com.bookings.entity.Casting;
import com.bookings.exception.ApiException;
import com.bookings.repository.CastingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * modify the return statements
 */
@Service
public class CastingService {
    private final CastingRepository castingRepository;

    @Autowired
    public CastingService(CastingRepository castingRepository) {
        this.castingRepository = castingRepository;
    }

    public void addActor(Casting casting) {
        try {
            if(ObjectUtils.isEmpty(casting)) {
                throw new ApiException(BookYourShow.DATA_NULL);
            }
            castingRepository.save(casting);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addAllActors(List<Casting> castingList) {
        try {
            if(ObjectUtils.isEmpty(castingList)) {
                throw new ApiException(BookYourShow.DATA_NULL);
            }
            castingRepository.saveAll(castingList);
        } catch(ApiException apiException) {
            throw new ApiException(apiException.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
