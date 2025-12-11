package com.valetparker.reviewservice.config;

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
                String userNo = requestAttributes.getRequest().getHeader("X-User-No");
                String role = requestAttributes.getRequest().getHeader("X-User-Role");
                String email = requestAttributes.getRequest().getHeader("X-User-Email");
                requestTemplate.header("X-User-No", userNo);
                requestTemplate.header("X-User-Role", role);
                requestTemplate.header("X-User-Email", email);
            }
        };
    }
}