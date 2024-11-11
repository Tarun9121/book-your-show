package com.bookings.convert;

import com.bookings.dto.AvailableShowDto;
import com.bookings.entity.Show;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class ShowConvert {
    public List<AvailableShowDto> convert(List<Show> showList) {
        List<AvailableShowDto> availableShowDtoList = new ArrayList<>();

        if(!CollectionUtils.isEmpty(showList)) {
            showList.forEach(show -> {
                AvailableShowDto showDto = new AvailableShowDto();
                BeanUtils.copyProperties(show, showDto);
                availableShowDtoList.add(showDto);
            });
        }
        return availableShowDtoList;
    }
}
