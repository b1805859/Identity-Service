package com.identityservice.dto.request.user;

import com.identityservice.entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;


@Data
public class UserCreateRequest {

    @Past(message = "{User.dob.past}")
    private LocalDate dob;

    @NotBlank(message = "{User.firstname.notBlank}")
    @Size(max = 25, message = "{User.firstname.size}")
    private String firstname;

    @NotBlank(message = "{User.lastname.notBlank}")
    @Size(max = 25, message = "{User.lastname.size}")
    private String lastname;

    @NotBlank(message = "{User.password.notBlank}")
    @Size(max = 25, message = "{User.password.size}")
    private String password;

    @NotBlank(message = "{User.username.notBlank}")
    @Size(max = 25, message = "{User.username.size}")
    private String username;

    private Set<Role> roles;
}
