package com.identityservice.service;

import com.identityservice.common.exception.AppException;
import com.identityservice.common.exception.ErrorCode;
import com.identityservice.constant.PredefinedRole;
import com.identityservice.dto.request.user.UserCreateRequest;
import com.identityservice.dto.request.user.UserUpdateRequest;
import com.identityservice.dto.response.user.UserInfoResponse;
import com.identityservice.entity.Role;
import com.identityservice.entity.User;
import com.identityservice.mapper.UserMapper;
import com.identityservice.repository.RoleRepository;
import com.identityservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;
  private final RoleRepository roleRepository;

  /**
   * Creates a new user if the username does not already exist. The user is automatically assigned
   * the default {@link PredefinedRole#USER_ROLE}.
   *
   * @param request User data to be created, including username and password.
   * @return The created user's information as a {@link UserInfoResponse} DTO.
   * @throws AppException if the username already exists (ErrorCode.USER_EXISTED).
   */
  @Transactional
  public UserInfoResponse create(UserCreateRequest request) {

    if (userRepository.existsByUsername(request.getUsername())) {
      throw new AppException(ErrorCode.USER_EXISTED);
    }

    User user = userMapper.toUser(request);

    user.setPassword(passwordEncoder.encode(request.getPassword()));

    HashSet<Role> roles = new HashSet<>();

    roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);

    user.setRoles(roles);
    user.setCreateAt(new Date());
    user.setUdpateAt(new Date());

    user = userRepository.save(user);

    return userMapper.toUserInfoResponse(user);
  }

  /**
   * Retrieves all users from the database.
   *
   * @return A list of {@link UserInfoResponse} DTOs. An empty list is returned if no users are
   * found.
   */
  public List<UserInfoResponse> getAll() {

    return userRepository.findAll().stream().map(userMapper
        ::toUserInfoResponse).toList();
  }

  /**
   * Retrieves user information by username.
   *
   * @param username The username to search for.
   * @return The found {@link User} entity.
   * @throws AppException if the user is not found (ErrorCode.USER_NOT_FOUND).
   */
  public User getUserInfo(String username) {
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
  }

  /**
   * Updates existing user information based on the username.
   *
   * @param username   The username of the user to update.
   * @param updateData New user information to apply.
   * @throws AppException if the user is not found (ErrorCode.USER_NOT_FOUND).
   */
  @Transactional
  public UserInfoResponse update(String username, UserUpdateRequest updateData) {

    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

    userMapper.toUser(user, updateData);
    user.setUdpateAt(new Date());

    userRepository.save(user);

    return userMapper.toUserInfoResponse(user);
  }

  /**
   * Deletes a user by username.
   *
   * @param username The username of the user to delete.
   * @throws AppException if the user does not exist (ErrorCode.USER_NOT_FOUND).
   */
  @Transactional
  public void delete(String username) {

    User user = userRepository.findByUsername(username).orElseThrow(
        () -> new AppException(ErrorCode.USER_NOT_FOUND));

    userRepository.delete(user);
  }
}