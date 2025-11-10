package com.identityservice.dto.response.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDate;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfoResponse {

  private LocalDate dob;

  private String firstname;

  private String lastname;

  private String password;

  private String username;

  private Integer version;

  private String firstName;

  private String lastName;
}
