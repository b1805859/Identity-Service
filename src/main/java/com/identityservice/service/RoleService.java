package com.identityservice.service;


import com.identityservice.common.exception.AppException;
import com.identityservice.common.exception.ErrorCode;
import com.identityservice.dto.request.role.RoleCreateRequest;
import com.identityservice.dto.request.role.RoleUpdateRequest;
import com.identityservice.dto.response.role.RoleResponse;
import com.identityservice.entity.Role;
import com.identityservice.mapper.RoleMapper;
import com.identityservice.repository.RoleRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

  /**
   * Repository for database interactions with the Role entity.
   */
  private final RoleRepository roleRepository;

  /**
   * Mapper for converting between Role entities, DTOs (Data Transfer Objects), and requests.
   */
  private final RoleMapper roleMapper;


  /**
   * Creates a new role if a role with the same name does not already exist.
   *
   * @param request The RoleCreateRequest containing the data for the new role.
   * @return A RoleResponse object representing the newly created role.
   * @throws AppException if a role with the provided name already exists (ErrorCode.ROLE_EXISTED).
   */
  @Transactional
  public RoleResponse create(RoleCreateRequest request) {
    if (roleRepository.existsByName(request.getName())) {
      throw new AppException(ErrorCode.ROLE_EXISTED);
    }

    Role role = roleMapper.toRole(request);

    role = roleRepository.save(role);

    return roleMapper.toRoleResponse(role);
  }

  /**
   * Retrieves all roles from the database.
   *
   * @return A List of RoleResponse objects representing all roles found in the database.
   */
  public List<RoleResponse> getAll() {

    return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
  }

  /**
   * Deletes a role identified by its unique name.
   *
   * @param name The name of the role to delete.
   * @throws AppException if the role with the given name does not exist
   *                      (ErrorCode.ROLE_NOT_FOUND).
   */
  @Transactional
  public void delete(String name) {
    if (!roleRepository.existsByName(name)) {
      throw new AppException(ErrorCode.ROLE_NOT_FOUND);
    }

    // Assuming 'name' is the ID used for deletion, or RoleRepository handles deletion by name implicitly.
    // A direct 'deleteByName' method on the repository would be cleaner if available.
    roleRepository.deleteById(name);

  }

  /**
   * Updates an existing role identified by its name with new data.
   *
   * @param name       The name of the role to update.
   * @param updateData The RoleUpdateRequest containing the new data for the role.
   * @throws AppException if the role with the given name does not exist
   *                      (ErrorCode.ROLE_NOT_FOUND).
   */
  @Transactional
  public void update(String name, RoleUpdateRequest updateData) {

    Role role = roleRepository.findByName(name).orElseThrow(
        () -> new AppException(ErrorCode.ROLE_NOT_FOUND));

    roleMapper.toRole(role, updateData);

    roleRepository.save(role);

  }
}
