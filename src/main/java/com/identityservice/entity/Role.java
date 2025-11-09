package com.identityservice.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Role {

    @Id
    @NotBlank(message = "{Role.name.notBlank}")
    @Size(max = 25, message = "{Role.name.size}")
    private String name;

    @Size(max = 50, message = "{Role.description.size}")
    private String description;

    @ManyToMany
    private Set<Permission> permissions;
}
