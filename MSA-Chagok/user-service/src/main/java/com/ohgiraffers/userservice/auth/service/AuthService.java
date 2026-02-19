package com.ohgiraffers.userservice.auth.service;

import com.ohgiraffers.userservice.auth.dto.request.LoginRequest;
import com.ohgiraffers.userservice.auth.dto.response.TokenResponse;
import com.ohgiraffers.userservice.auth.entity.RefreshToken;
import com.ohgiraffers.userservice.auth.repository.RefreshTokenRepository;
import com.ohgiraffers.userservice.command.domain.User;
import com.ohgiraffers.userservice.command.repository.UserRepository;
import com.ohgiraffers.userservice.exception.BusinessException;
import com.ohgiraffers.userservice.exception.ErrorCode;
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

        if (request.getEmail() == null || request.getEmail().isEmpty() ||
            request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new BusinessException(ErrorCode.REQUIRED_FIELD_MISSING);
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_CREDENTIALS));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException(ErrorCode.INVALID_CREDENTIALS);
        }

        String accessToken = jwtTokenProvider.createAccessToken(user.getEmail(), user.getRole().name(), user.getUserNo());
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
                .orElseThrow(() -> new BusinessException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));

        if(!storedToken.getToken().equals(providedRefreshToken)) {
            throw new BusinessException(ErrorCode.REFRESH_TOKEN_MISMATCH);
        }

        if(storedToken.getExpiryDate().before(new Date())) {
            throw new BusinessException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        String accessToken = jwtTokenProvider.createAccessToken(user.getEmail(), user.getRole().name(), user.getUserNo());
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

        RefreshToken storedToken = refreshTokenRepository.findById(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));
        if(!storedToken.getToken().equals(refreshToken)) {
            throw new BusinessException(ErrorCode.REFRESH_TOKEN_MISMATCH);
        }

        if (storedToken.getExpiryDate().before(new Date())) {
            throw new BusinessException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        }

        refreshTokenRepository.deleteByEmail(email);
    }
}
