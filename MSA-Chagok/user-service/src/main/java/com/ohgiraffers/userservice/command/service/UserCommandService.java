package com.ohgiraffers.userservice.command.service;

import com.ohgiraffers.userservice.command.domain.User;
import com.ohgiraffers.userservice.command.dto.request.UserCreateRequest;
import com.ohgiraffers.userservice.command.dto.request.UserUpdateRequest;
import com.ohgiraffers.userservice.command.repository.UserRepository;
import com.ohgiraffers.userservice.exception.BusinessException;
import com.ohgiraffers.userservice.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserCommandService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void registerUser(UserCreateRequest request) {

        if (isNullOrEmpty(request.getEmail()) ||
            isNullOrEmpty(request.getPassword()) ||
            isNullOrEmpty(request.getUserName()) ||
            isNullOrEmpty(request.getNickname()) ||
            isNullOrEmpty(request.getCarNumber())
        ) {
            throw new BusinessException(ErrorCode.REQUIRED_FIELD_MISSING);
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new BusinessException(ErrorCode.EMAIL_DUPLICATED);
        }

        if (userRepository.findByNickname(request.getNickname()).isPresent()){
            throw new BusinessException(ErrorCode.NICKNAME_DUPLICATED);
        }

        if (userRepository.findByCarNumber(request.getCarNumber()).isPresent()){
            throw new BusinessException(ErrorCode.CAR_NUMBER_DUPLICATED);
        }

        User user = modelMapper.map(request, User.class);
        user.setEncodedPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }

    private boolean isNullOrEmpty(String value) {
        return value == null || value.isBlank();
    }


    @Transactional
    public void updateUser(String email, UserUpdateRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        boolean hasPassword = request.getPassword() != null && !request.getPassword().isEmpty();
        boolean hasCarNumber = request.getCarNumber() != null && !request.getCarNumber().isEmpty();

        if(!hasPassword && !hasCarNumber) {
            throw new BusinessException(ErrorCode.NO_UPDATE_VALUES);
        }

        if(hasPassword) {
            user.setEncodedPassword(passwordEncoder.encode(request.getPassword()));
        }
        if(hasCarNumber) {
            userRepository.findByCarNumber(request.getCarNumber())
                            .filter(u -> !u.getUserNo().equals(user.getUserNo()))
                            .ifPresent(u -> {
                                throw new BusinessException(ErrorCode.CAR_NUMBER_DUPLICATED);
                            });

            user.setCarNumber(request.getCarNumber());
        }
    }

}
