package com.identityservice.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthRequest {

    @NotBlank(message = "{User.username.notBlank}")
    @Size(max = 25, message = "{User.username.size}")
    String username;

    @NotBlank(message = "{User.password.notBlank}")
    @Size(max = 25, message = "{User.password.size}")
    String password;
}
