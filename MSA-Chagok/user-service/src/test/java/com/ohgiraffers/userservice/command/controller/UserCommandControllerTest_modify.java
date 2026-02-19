package com.ohgiraffers.userservice.command.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohgiraffers.userservice.command.dto.request.UserUpdateRequest;
import com.ohgiraffers.userservice.command.service.UserCommandService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserCommandController.class)
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser(username = "hi@email.com", roles = "USER")
class UserCommandControllerTest_modify {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserCommandService userCommandService;

    @Test
    @DisplayName("회원 수정 Controller 테스트 - 성공")
    void modifyUser_success() throws Exception {

        String email = "hi@email.com";

        String json = """
                {
                  "password": "hio",
                  "carNumber": "11가1111"
                }
                """;

        doNothing().when(userCommandService).updateUser(any(String.class), any());

        mockMvc.perform(
                        post("/user/modify")
                                .with(csrf())
                                .header("X-USER-EMAIL", email)   // ⭐ 필수 추가
                                .header("X-USER-ROLE", "USER")   // ⭐ 필수 추가
                                .header("X-USER-NO", "1")        // 필요하면
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isCreated());

        verify(userCommandService).updateUser(any(), any());
    }
}