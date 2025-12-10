package com.valetparker.reservationservice.command.client;

import com.valetparker.reservationservice.common.dto.ApiResponse;
import com.valetparker.reservationservice.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "valetparker-user-service", configuration = FeignClientConfig.class)
public interface UserClient {

    @GetMapping("/users/{userId}/")
    Long getUserNo(@PathVariable("userId") String userId);

}