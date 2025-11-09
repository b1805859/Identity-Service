package com.identityservice.dto.request.permission;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PermissionCreateRequest {

    @NotBlank(message = "{Permission.name.notBlank}")
    @Size(max = 25, message = "{Permission.name.size}")
    private String name;

    @Size(max = 50, message = "{Permission.description.size}")
    private String description;
}
