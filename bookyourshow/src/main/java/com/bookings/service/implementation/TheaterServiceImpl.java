package com.bookings.service.implementation;

import com.bookings.constants.BookYourShow;
import com.bookings.convert.Convert;
import com.bookings.dto.BaseResponse;
import com.bookings.dto.TheaterDto;
import com.bookings.entity.Theater;
import com.bookings.exception.ApiException;
import com.bookings.repository.TheaterRepository;
import com.bookings.service.TheaterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@Slf4j
public class TheaterServiceImpl implements TheaterService {
    @Autowired
    private TheaterRepository theaterRepository;
    @Autowired
    private Convert transform;

    public BaseResponse<Object> registerTheater(TheaterDto theaterDto) {
        try {
            if(ObjectUtils.isEmpty(theaterDto)) {
               throw new ApiException(BookYourShow.DATA_NULL);
            }
            Theater theater = transform.convert(theaterDto);
            theaterRepository.save(theater);
            log.info("Theater Details saved");
            return BaseResponse.success(HttpStatus.CREATED, BookYourShow.DATA_SAVED);
        } catch (Exception e) {
            log.error("{}", e.getMessage());
            return BaseResponse.error(HttpStatus.BAD_REQUEST, BookYourShow.DATA_SAVING_FAILURE);
        }
    }
}
