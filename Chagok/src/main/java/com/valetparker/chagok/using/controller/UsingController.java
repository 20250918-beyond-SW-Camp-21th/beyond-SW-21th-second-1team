package com.valetparker.chagok.using.controller;

import com.valetparker.chagok.common.dto.ApiResponse;
import com.valetparker.chagok.using.dto.request.UsingInfoRequest;
import com.valetparker.chagok.using.dto.response.UsingInfoResponse;
import com.valetparker.chagok.using.service.UsingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/using")
@RequiredArgsConstructor
public class UsingController {

    private final UsingService usingService;

    // 이용등록
    @GetMapping("/regist")
    public ResponseEntity<ApiResponse<Long>> createUsing(
            @RequestParam Long reservationId
    ) {
        Long response =  usingService.createUsing(reservationId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // 이용정보 조회
    @PostMapping("/info")
    public ResponseEntity<ApiResponse<UsingInfoResponse>> getUsingInfo(
            @RequestBody UsingInfoRequest request
    ) {
        UsingInfoResponse response = usingService.getUsingInfo(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
