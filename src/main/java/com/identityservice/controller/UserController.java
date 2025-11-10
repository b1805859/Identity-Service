package com.identityservice.controller;

import com.identityservice.dto.request.user.UserCreateRequest;
import com.identityservice.dto.request.user.UserUpdateRequest;
import com.identityservice.dto.response.ApiResponse;
import com.identityservice.dto.response.user.UserInfoResponse;
import com.identityservice.mapper.UserMapper;
import com.identityservice.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller responsible for handling user-related operations.
 * <p>
 * Provides endpoints for creating, retrieving, updating, and deleting users. Each endpoint returns
 * a standardized {@link ApiResponse} object.
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final UserMapper userMapper;

  /**
   * Creates a new user.
   * <p>
   * - Validates and converts the incoming request DTO to an entity.<br> - Persists the user via the
   * service layer.<br> - Maps the saved entity to a response DTO.<br> - Returns a standardized API
   * response.
   *
   * @param request user creation request
   * @return standardized API response containing created user info
   */
  @PostMapping
  public ApiResponse<UserInfoResponse> create(@RequestBody @Valid UserCreateRequest request) {

    return ApiResponse.<UserInfoResponse>builder()
        .result(userService.create(request))
        .build();
  }

  /**
   * Retrieves all users.
   * <p>
   * - Fetches all user entities from the database.<br> - Maps them to {@link UserInfoResponse}
   * DTOs.<br> - Returns the list wrapped in a standardized API response.
   *
   * @return standardized API response containing a list of all users
   */
  @GetMapping
  public ApiResponse<List<UserInfoResponse>> getAll() {

    return ApiResponse.<List<UserInfoResponse>>builder()
        .result(userService.getAll())
        .build();
  }

  /**
   * Retrieves user information by username.
   * <p>
   * - Extracts the {@code username} from the path variable.<br> - Fetches the corresponding user
   * entity from the service.<br> - Maps it to a {@link UserInfoResponse}.<br> - Returns the result
   * in a standardized API response.
   *
   * @param username the username to look up
   * @return standardized API response containing user info
   */
  @GetMapping("/{username}")
  public ApiResponse<UserInfoResponse> getUser(@PathVariable String username) {

    return ApiResponse.<UserInfoResponse>builder()
        .result(userMapper.toUserInfoResponse(userService.getUserInfo(username)))
        .build();
  }

  /**
   * Updates an existing user by username.
   * <p>
   * - Extracts the {@code username} from the path.<br> - Accepts updated user information in the
   * request body.<br> - Calls the service to perform the update.<br> - Maps the updated entity to a
   * response DTO.<br> - Returns the updated user info wrapped in a standardized API response.
   *
   * @param username   the username of the user to update
   * @param updateData updated user data
   * @return standardized API response containing updated user info
   */
  @PutMapping("/{username}")
  public ApiResponse<UserInfoResponse> update(@PathVariable String username,
      @RequestBody @Valid UserUpdateRequest updateData) {

    return ApiResponse.<UserInfoResponse>builder()
        .result(userService.update(username, updateData))
        .build();
  }

  /**
   * Deletes a user by username.
   * <p>
   * - Extracts the {@code username} from the path.<br> - Delegates the deletion to the service
   * layer.<br> - Returns {@code true} if the deletion succeeded.<br> - Wraps the result in a
   * standardized API response.
   *
   * @param username the username of the user to delete
   * @return standardized API response indicating success or failure
   */
  @DeleteMapping("/{username}")
  public ApiResponse<Void> deleteUser(@PathVariable String username) {

    userService.delete(username);

    return ApiResponse.<Void>builder()
        .build();
  }
}
