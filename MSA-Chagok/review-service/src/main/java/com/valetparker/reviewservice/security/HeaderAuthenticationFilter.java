package com.valetparker.reviewservice.security;

import com.valetparker.reviewservice.common.model.CustomUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
        String userNoStr = request.getHeader("X-User-No");

        log.info("email : {}", email);
        log.info("role : {}", role);
        log.info("userNo : {}", userNoStr);

//        if (email != null) {
//
//            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//            if(role != null) {
//                authorities.add(new SimpleGrantedAuthority(role));
//            }
//
//            // Principal 을 UserDetails 로 구성해야 AuthenticationPrincipal에서 받을 수 있음
//            UserDetails principal = new org.springframework.security.core.userdetails.User(email, "", authorities);
//
//            PreAuthenticatedAuthenticationToken authentication =
//                    new PreAuthenticatedAuthenticationToken(principal, null, authorities);
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }


        if (email != null && userNoStr != null && role != null) {
            Long userNo = Long.valueOf(userNoStr);

            CustomUser customUser = CustomUser.builder()
                    .email(email)
                    .userNo(userNo)
                    .password("")  // 필요 없음
                    .authorities(List.of(new SimpleGrantedAuthority(role)))
                    .build();

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(customUser, null, customUser.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(auth);
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
