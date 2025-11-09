package com.identityservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    @Column(unique = true)
    private String username;

    @Version
    private Integer version;

    private Date createAt;

    private Date udpateAt;

    @ManyToMany
    private Set<Role> roles;

}
