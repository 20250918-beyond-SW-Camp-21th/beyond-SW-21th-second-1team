package com.ohgiraffers.userservice.auth.service;

import com.ohgiraffers.userservice.auth.dto.request.LoginRequest;
import com.ohgiraffers.userservice.auth.dto.response.TokenResponse;
import com.ohgiraffers.userservice.auth.entity.RefreshToken;
import com.ohgiraffers.userservice.auth.repository.RefreshTokenRepository;
import com.ohgiraffers.userservice.command.domain.User;
import com.ohgiraffers.userservice.command.repository.UserRepository;
import com.ohgiraffers.userservice.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public TokenResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("이메일 또는 비밀번호가 잘못 되었습니다. 아이디와 비밀번호를 정확히 입력해 주세요."));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("이메일 또는 비밀번호가 잘못 되었습니다. 아이디와 비밀번호를 정확히 입력해 주세요.");
        }

        String accessToken = jwtTokenProvider.createAccessToken(user.getEmail(), user.getRole().name());
        System.out.println("accessToken = " + accessToken);
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getEmail());
        System.out.println("refreshToken = " + refreshToken);

        RefreshToken refreshTokenEntity = RefreshToken.builder()
                .email(user.getEmail())
                .token(refreshToken)
                .expiryDate(jwtTokenProvider.getRefreshTokenExpiryDate(refreshToken))
                .build();

        refreshTokenRepository.save(refreshTokenEntity);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public TokenResponse refreshToken(String providedRefreshToken) {

        jwtTokenProvider.validateToken(providedRefreshToken);
        String email = jwtTokenProvider.getEmailFromJWT(providedRefreshToken);

        RefreshToken storedToken = refreshTokenRepository.findById(email)
                .orElseThrow(() -> new BadCredentialsException("해당 유저로 조회되는 리프레시 토큰 없음"));

        if(!storedToken.getToken().equals(providedRefreshToken)) {
            throw new BadCredentialsException("리프레시 토큰 일치하지 않음");
        }

        if(storedToken.getExpiryDate().before(new Date())) {
            throw new BadCredentialsException("리프레시 토큰 유효시간 만료");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("해당 리프레시 토큰을 위한 유저 없음"));

        String accessToken = jwtTokenProvider.createAccessToken(user.getEmail(), user.getRole().name());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getEmail());

        RefreshToken tokenEntity = RefreshToken.builder()
                .email(user.getEmail())
                .token(refreshToken)
                .expiryDate(
                        new Date(System.currentTimeMillis()
                                + jwtTokenProvider.getRefreshTokenExpiration())
                )
                .build();

        refreshTokenRepository.save(tokenEntity);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    public void logout(String refreshToken) {
        jwtTokenProvider.validateToken(refreshToken);
        String email = jwtTokenProvider.getEmailFromJWT(refreshToken);
        refreshTokenRepository.deleteByEmail(email);
    }
}
