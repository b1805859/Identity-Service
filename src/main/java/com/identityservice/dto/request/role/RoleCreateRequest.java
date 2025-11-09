package com.identityservice.dto.request.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RoleCreateRequest {

    @NotBlank(message = "{Role.name.notBlank}")
    @Size(max = 25, message = "{Role.name.size}")
    private String name;

    @Size(max = 50, message = "{Role.description.size}")
    private String description;
}
