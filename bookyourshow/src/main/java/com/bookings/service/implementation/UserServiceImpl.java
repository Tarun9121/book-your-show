package com.bookings.service.implementation;

import com.bookings.constants.BookYourShow;
import com.bookings.convert.Convert;
import com.bookings.dto.UserDto;
import com.bookings.dto.UserRequestDto;
import com.bookings.entity.User;
import com.bookings.exception.ApiException;
import com.bookings.repository.UserRepository;
import com.bookings.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
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

    public ResponseEntity<UserDto> registerUserDetails(UserRequestDto userReqDto) {
        UserDto userDto = new UserDto();
        try {
            if(ObjectUtils.isEmpty(userReqDto)) {
                throw new ApiException(BookYourShow.DATA_NULL);
            }

            User user = convert.convert(userReqDto);
            User savedUser = userRepository.save(user);
            userDto = convert.convert(savedUser);

            return ResponseEntity.status(HttpStatus.OK).body(userDto);
        } catch(Exception e) {
            userDto.setStatus(HttpStatus.BAD_REQUEST);
            userDto.setMessage(e.getMessage());
            userDto.setLocalDateTime(LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userDto);
        }
    }

    public ResponseEntity<UserRequestDto> getUserByLoginDetails(UserRequestDto userRequestDto) {
        UserRequestDto existingUser = new UserRequestDto();
        try {
            User user = userRepository.getByCrediantals(userRequestDto.getEmail(), userRequestDto.getPassword());
            if(ObjectUtils.isEmpty(user)) {
                throw new ApiException(BookYourShow.NO_USER_FOUND);
            }
            existingUser = convert.convertToRequesDto(user);
            return ResponseEntity.status(HttpStatus.OK).body(existingUser);
        } catch (Exception e) {
            existingUser.setStatus(HttpStatus.BAD_REQUEST);
            existingUser.setMessage(e.getMessage());
            existingUser.setLocalDateTime(LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(existingUser);
        }
    }

    public ResponseEntity<UserDto> getUserByEmail(String email) {
        try {
            if(StringUtils.isEmpty(email)) {
               throw new ApiException(BookYourShow.DATA_NULL);
            }

            User existingUser = userRepository.findUserByEmailId(email)
                    .orElseThrow(() -> new ApiException(BookYourShow.ACCOUNT_NOT_FOUND));

            UserDto userDto = convert.convert(existingUser);

            return ResponseEntity.status(HttpStatus.OK).body(userDto);
        } catch (Exception e) {
            UserDto userDto = new UserDto();
            userDto.setStatus(HttpStatus.BAD_REQUEST);
            userDto.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userDto);
        }
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

    public ResponseEntity<UserDto> getUserDetailsById(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(BookYourShow.USER_NOT_FOUND));
        UserDto userDto = convert.convert(user);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    public User getUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(BookYourShow.DATA_NOT_FOUND + ": User ID " + userId));
    }
}