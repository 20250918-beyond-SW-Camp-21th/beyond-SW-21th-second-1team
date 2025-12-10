package paymentservice.user.auth.controller;

import com.valetparker.chagok.common.dto.ApiResponse;
import com.valetparker.chagok.user.auth.dto.request.LoginRequest;
import com.valetparker.chagok.user.auth.dto.request.RefreshTokenRequest;
import com.valetparker.chagok.user.auth.dto.response.TokenResponse;
import com.valetparker.chagok.user.auth.service.AuthService;
import com.valetparker.chagok.user.command.domain.User;
import com.valetparker.chagok.user.command.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponse>> login(@RequestBody LoginRequest request) {
        TokenResponse token = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success(token));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<TokenResponse>> refreshToken(
            @RequestBody RefreshTokenRequest request
            ){
        TokenResponse response = authService.refreshToken(request.getRefreshToken());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@PathVariable("email") String email,
                                                    @RequestBody RefreshTokenRequest request){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        authService.logout(request.getRefreshToken());
        return ResponseEntity.ok(ApiResponse.success(null));
    }

//    @PostMapping("/logout")
//    public ResponseEntity<ApiResponse<Vo>> logout()
}
