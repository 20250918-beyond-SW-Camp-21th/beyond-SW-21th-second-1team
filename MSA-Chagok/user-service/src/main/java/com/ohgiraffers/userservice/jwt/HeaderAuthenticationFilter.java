package com.ohgiraffers.userservice.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class HeaderAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // API Gateway가 전달한 헤더 읽기
        String email = request.getHeader("X-User-Email");
        String role = request.getHeader("X-User-Role");

        log.info("email : {}", email);
        log.info("role : {}", role);

//        if (email != null && role != null) {
//            // 이미 Gateway에서 검증된 정보로 인증 객체 구성
//            PreAuthenticatedAuthenticationToken authentication =
//                    new PreAuthenticatedAuthenticationToken(email, null,
//                            List.of(new SimpleGrantedAuthority(role)));
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }

        if (email != null) {

            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            if(role != null) {
                authorities.add(new SimpleGrantedAuthority(role));
            }

            // Principal 을 UserDetails 로 구성해야 AuthenticationPrincipal에서 받을 수 있음
            UserDetails principal = new org.springframework.security.core.userdetails.User(email, "", authorities);

            PreAuthenticatedAuthenticationToken authentication =
                    new PreAuthenticatedAuthenticationToken(principal, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);

    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
