package com.bookings.service.implementation;

import com.bookings.constants.BookYourShow;
import com.bookings.convert.Convert;
import com.bookings.dto.UserDto;
import com.bookings.entity.User;
import com.bookings.exception.ApiException;
import com.bookings.repository.UserRepository;
import com.bookings.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.UUID;


@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final Convert convert;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, Convert convert) {
        this.userRepository = userRepository;
        this.convert = convert;
    }

    @Override
    public ResponseEntity<String> registerUser(UserDto userDto) {
        try {
            if (ObjectUtils.isEmpty(userDto)) {
                log.info(BookYourShow.DATA_NULL);
                throw new ApiException(BookYourShow.DATA_NULL);
            }
            if (userRepository.existsByEmailAndMobileNo(userDto.getEmail(),userDto.getMobileNo())) {
                log.info(BookYourShow.ACCOUNT_ALREADY_EXIST);
                throw new ApiException(BookYourShow.ACCOUNT_ALREADY_EXIST);
            }
            User user = Convert.convert(userDto);
            userRepository.save(user);
            log.info("Successfully registered user");
            return ResponseEntity.status(HttpStatus.CREATED).body(BookYourShow.ACCOUNT_CREATED);
        } catch (ApiException e) {
            log.error("Validation error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("An unexpected error occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> softDeleteUser(UUID id) {
        try {
            User user = userRepository.findActiveById(id)
                    .orElseThrow(() -> new ApiException(BookYourShow.DATA_NOT_FOUND + ": User ID " + id));

            user.setDeleted(true);
            userRepository.save(user);

            log.info(BookYourShow.DATA_DELETED);
            return ResponseEntity.status(HttpStatus.OK).body(BookYourShow.DATA_DELETED);

        } catch (ApiException e) {
            log.error(BookYourShow.DATA_NOT_FOUND + ": {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        } catch (Exception e) {
            log.error(BookYourShow.SOMETHING_WENT_WRONG + ": {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BookYourShow.SOMETHING_WENT_WRONG);
        }
    }

    public User getUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(BookYourShow.DATA_NOT_FOUND + ": User ID " + userId));
    }
}