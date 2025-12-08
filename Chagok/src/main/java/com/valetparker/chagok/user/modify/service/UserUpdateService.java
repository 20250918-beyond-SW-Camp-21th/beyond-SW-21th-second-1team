package com.valetparker.chagok.user.modify.service;

import com.valetparker.chagok.user.command.domain.User;
import com.valetparker.chagok.user.command.repository.UserRepository;
import com.valetparker.chagok.user.modify.dto.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserUpdateService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void updateUser(Long userNo, UserUpdateRequest request) {

        User user = userRepository.findByUserNo(userNo)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없음"));

        if(request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setEncodedPassword(passwordEncoder.encode(request.getPassword()));
        }

        if(request.getCarNumber() != null && !request.getCarNumber().isEmpty()) {
            user.setCarNumber(request.getCarNumber());
        }
        userRepository.save(user);
    }

}
