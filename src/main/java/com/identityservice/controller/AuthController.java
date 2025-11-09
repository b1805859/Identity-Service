package com.identityservice.controller;

import com.identityservice.common.exception.AppException;
import com.identityservice.common.exception.ErrorCode;
import com.identityservice.dto.request.auth.AuthRequest;
import com.identityservice.dto.response.ApiResponse;
import com.identityservice.dto.response.auth.AuthResponse;
import com.identityservice.entity.User;
import com.identityservice.repository.UserRepository;
import com.identityservice.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtTokenProvider jwtTokenProvider;
    //  private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ApiResponse<AuthResponse> authenticate(@RequestBody AuthRequest request) {

        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(()
                -> new AppException(ErrorCode.USER_NOT_FOUND));

        String token = jwtTokenProvider.generateToken(user);
        return ApiResponse.<AuthResponse>builder()
                .result(new AuthResponse(token))
                .build();
    }
}
