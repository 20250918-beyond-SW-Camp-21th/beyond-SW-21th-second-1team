package com.ohgiraffers.userservice.command.service;

import com.ohgiraffers.userservice.command.domain.User;
import com.ohgiraffers.userservice.command.dto.request.UserCreateRequest;
import com.ohgiraffers.userservice.command.dto.request.UserUpdateRequest;
import com.ohgiraffers.userservice.command.repository.UserRepository;
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
        User user = modelMapper.map(request, User.class);
        user.setEncodedPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }


    @Transactional
    public void updateUser(String email, UserUpdateRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없음"));

        if(request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setEncodedPassword(passwordEncoder.encode(request.getPassword()));
        }

        if(request.getCarNumber() != null && !request.getCarNumber().isEmpty()) {
            user.setCarNumber(request.getCarNumber());
        }
    }

}
