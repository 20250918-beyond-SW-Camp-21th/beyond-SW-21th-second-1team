package com.valetparker.reviewservice.command.client;

import com.valetparker.reviewservice.common.dto.ApiResponse;
import com.valetparker.reviewservice.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "valetparker-user-service", configuration = FeignClientConfig.class)
public interface UserClient {

//    @GetMapping("/users/{userId}/grade")
    @GetMapping("/users/r")
    ApiResponse<String> getUserId(@PathVariable("userId") Long userId);
}