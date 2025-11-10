package com.identityservice.service;

import com.identityservice.common.exception.AppException;
import com.identityservice.common.exception.ErrorCode;
import com.identityservice.dto.request.permission.PermissionCreateRequest;
import com.identityservice.dto.request.permission.PermissionUpdateRequest;
import com.identityservice.dto.response.permission.PermissionResponse;
import com.identityservice.entity.Permission;
import com.identityservice.mapper.PermissionMapper;
import com.identityservice.repository.PermissionRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionService {

  private final PermissionRepository permissionRepository;
  private final PermissionMapper permissionMapper;

  /**
   * Retrieve all permissions from the system.
   *
   * @return list of Permission entities
   */
  public List<PermissionResponse> getAll() {
    return permissionRepository.findAll().stream().map(permissionMapper::toPermissionResponse)
        .toList();
  }

  /**
   * Create a new permission. If a permission with the same name already exists, throws
   * {@link AppException} with {@code PERMISSION_ALREADY_EXISTS}.
   *
   * @param request the {@link Permission} entity containing the data to be created
   * @throws AppException if the permission already exists or if a database error occurs
   */
  @Transactional
  public PermissionResponse create(PermissionCreateRequest request) {
    if (permissionRepository.existsByName(request.getName())) {
      throw new AppException(ErrorCode.PERMISSION_EXISTED);
    }

    Permission permission = new Permission();

    permission = permissionMapper.toPermission(request);

    return permissionMapper.toPermissionResponse(permissionRepository.save(permission));
  }

  /**
   * Update an existing permission by its name. If the permission does not exist, throws
   * {@link AppException} with {@code PERMISSION_NOT_FOUND}.
   *
   * @param name       the name of the permission to update
   * @param updateData the {@link Permission} entity containing updated data
   * @throws AppException if the permission does not exist or if a database error occurs
   */
  @Transactional
  public void update(String name, PermissionUpdateRequest updateData) {

    Permission permission = permissionRepository.findByName(name)
        .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));

    permissionMapper.toPermission(permission, updateData);

    permissionRepository.save(permission);

  }

  /**
   * Delete a permission by its name. If the permission does not exist, throws {@link AppException}
   * with {@code PERMISSION_NOT_FOUND}.
   *
   * @param name the name of the permission to delete
   * @throws AppException if the permission does not exist or if a database error occurs
   */
  @Transactional
  public void delete(String name) {
    if (!permissionRepository.existsByName(name)) {
      throw new AppException(ErrorCode.PERMISSION_NOT_FOUND);
    }

    permissionRepository.deleteByName(name);
  }
}
