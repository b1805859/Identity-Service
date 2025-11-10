package com.identityservice.controller;


import com.identityservice.dto.request.role.RoleCreateRequest;
import com.identityservice.dto.request.role.RoleUpdateRequest;
import com.identityservice.dto.response.ApiResponse;
import com.identityservice.dto.response.role.RoleResponse;
import com.identityservice.mapper.RoleMapper;
import com.identityservice.service.RoleService;
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
 * REST controller responsible for handling role-related operations.
 * <p>
 * Provides endpoints for creating, retrieving, updating, and deleting roles. Each endpoint returns
 * a standardized {@link ApiResponse} object.
 */
@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

  private final RoleService roleService;
  private final RoleMapper roleMapper;

  /**
   * Creates a new role.
   * <p>
   * - Validates and converts the incoming request DTO to an entity.<br> - Persists the role via the
   * service layer.<br> - Returns a standardized API response.
   *
   * @param request role creation request
   * @return standardized API response indicating success
   */
  @PostMapping
  public ApiResponse<RoleResponse> create(@RequestBody @Valid RoleCreateRequest request) {

    return ApiResponse.<RoleResponse>builder()
        .result(roleService.create(request))
        .build();
  }

  /**
   * Retrieves all roles.
   * <p>
   * - Fetches all role entities from the database.<br> - Maps them to {@link RoleResponse}
   * DTOs.<br> - Returns the list wrapped in a standardized API response.
   *
   * @return standardized API response containing a list of all roles
   */
  @GetMapping
  public ApiResponse<List<RoleResponse>> getAll() {

    return ApiResponse.<List<RoleResponse>>builder()
        .result(roleService.getAll())
        .build();
  }

  /**
   * Updates an existing role by name.
   * <p>
   * - Extracts the {@code name} from the path.<br> - Accepts updated role information in the
   * request body.<br> - Calls the service to perform the update.<br> - Returns a standardized API
   * response indicating success.
   *
   * @param name       the name of the role to update
   * @param updateData updated role data
   * @return standardized API response indicating success
   */
  @PutMapping("/{name}")
  public ApiResponse<Void> update(@PathVariable String name,
      @RequestBody @Valid RoleUpdateRequest updateData) {
    roleService.update(name, updateData);
    return ApiResponse.<Void>builder()
        .build();
  }

  /**
   * Deletes a role by name.
   * <p>
   * - Extracts the {@code name} from the path.<br> - Delegates the deletion to the service
   * layer.<br> - Returns {@code true} if the deletion succeeded.<br> - Wraps the result in a
   * standardized API response.
   *
   * @param name the name of the role to delete
   * @return standardized API response indicating success or failure
   */
  @DeleteMapping("/{name}")
  public ApiResponse<Void> delete(@PathVariable String name) {
    roleService.delete(name);
    return ApiResponse.<Void>builder().build();
  }
}
