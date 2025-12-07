package com.valetparker.chagok.common.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class HeaderAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // API Gateway가 전달한 헤더 읽기
        String userNo = request.getHeader("X-User-Id");
        String nickname = request.getHeader("X-User-Role");

        log.info("userId : {}", userNo);
        log.info("role : {}", nickname);

        if (userNo != null && nickname != null) {
            // 이미 Gateway에서 검증된 정보로 인증 객체 구성
            PreAuthenticatedAuthenticationToken authentication =
                    new PreAuthenticatedAuthenticationToken(userNo, null,
                            List.of(new SimpleGrantedAuthority(nickname)));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();

        return path.equals("/auth/login") ||
                path.equals("/auth/refresh") ||
                path.matches("^/auth/\\d+/logout$");  // 숫자 포함 logout 허용
    }

}
