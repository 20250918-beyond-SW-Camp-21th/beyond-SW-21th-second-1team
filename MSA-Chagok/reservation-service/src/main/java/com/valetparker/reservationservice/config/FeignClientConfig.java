package com.valetparker.reservationservice.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignClientConfig {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {

            /* 현재 요청의 Http Servlet Request 를 가져옴 */
            ServletRequestAttributes requestAttributes =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

            if(requestAttributes != null) {
                String userId = requestAttributes.getRequest().getHeader("X-User-Id");
                String role = requestAttributes.getRequest().getHeader("X-User-Role");
                requestTemplate.header("X-User-Id", userId);
                requestTemplate.header("X-User-Role", role);
            }
        };
    }
}