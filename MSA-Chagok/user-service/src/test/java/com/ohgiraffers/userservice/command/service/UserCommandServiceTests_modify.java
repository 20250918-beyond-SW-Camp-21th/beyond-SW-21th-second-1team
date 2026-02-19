package com.ohgiraffers.userservice.command.service;


import com.ohgiraffers.userservice.command.domain.User;
import com.ohgiraffers.userservice.command.dto.request.UserUpdateRequest;
import com.ohgiraffers.userservice.command.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserCommandServiceTests_modify {

    UserRepository userRepository = mock(UserRepository.class);
    ModelMapper modelMapper = mock(ModelMapper.class);
    PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

    UserCommandService userCommandService =
            new UserCommandService(userRepository, modelMapper, passwordEncoder);

    @Test
    @DisplayName("회원 정보 수정 성공 - Service")
    void updateUser_success(){

        String email = "hi@email.com";

        // --- User 객체 강제 생성 ---
        User user = new User();
        ReflectionTestUtils.setField(user, "userNo", 1L);
        ReflectionTestUtils.setField(user, "email", email);
        ReflectionTestUtils.setField(user, "password", "oldPass");
        ReflectionTestUtils.setField(user, "userName", "나갈치");
        ReflectionTestUtils.setField(user, "nickname", "나는 갈치");
        ReflectionTestUtils.setField(user, "carNumber", "43부8493");
        ReflectionTestUtils.setField(user, "role", com.ohgiraffers.userservice.command.domain.UserRole.USER);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("newPass1234")).thenReturn("ENCODED");

        System.out.println("before user " + user);

        UserUpdateRequest request = new UserUpdateRequest();
        ReflectionTestUtils.setField(request, "password", "newPass1234");
        ReflectionTestUtils.setField(request, "carNumber", "11가1111");

        // when
        userCommandService.updateUser(email, request);
        System.out.println("after user : " + user);

        // then
        assertEquals("ENCODED", user.getPassword());
        assertEquals("11가1111", user.getCarNumber());

        verify(userRepository, never()).save(any());
    }
}