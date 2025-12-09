package com.valetparker.gateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {
    @RequestMapping("/fallback/user")
    public Mono<String> userFallback() {
        return Mono.just("현재 사용자 서비스에 장애가 발생했습니다. 잠시 후 다시 시도해주세요.");
    }
}
