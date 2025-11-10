package com.identityservice.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Data;

@Data
public class UserUpdateRequest {

  @Past(message = "{User.dob.past}")
  private LocalDate dob;

  @NotBlank(message = "{User.firstname.notBlank}")
  @Size(max = 25, message = "{User.firstname.size}")
  private String firstname;

  @NotBlank(message = "{User.lastname.notBlank}")
  @Size(max = 25, message = "{User.lastname.size}")
  private String lastname;
}
