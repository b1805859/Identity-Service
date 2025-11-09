package com.identityservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Permission {

    @Id
    @NotBlank(message = "{Permission.name.notBlank}")
    @Size(max = 25, message = "{Permission.name.size}")
    private String name;

    @Size(max = 50, message = "{Permission.description.size}")
    private String description;
}
