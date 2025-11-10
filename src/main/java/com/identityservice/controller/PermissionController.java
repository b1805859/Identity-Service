package com.identityservice.controller;

import com.identityservice.dto.request.permission.PermissionCreateRequest;
import com.identityservice.dto.request.permission.PermissionUpdateRequest;
import com.identityservice.dto.response.ApiResponse;
import com.identityservice.dto.response.permission.PermissionResponse;
import com.identityservice.mapper.PermissionMapper;
import com.identityservice.service.PermissionService;
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
 * REST controller responsible for handling permission-related operations.
 * <p>
 * Provides endpoints for creating, retrieving, updating, and deleting permissions. Each endpoint
 * returns a standardized {@link ApiResponse} object.
 */
@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
public class PermissionController {

  private final PermissionService permissionService;
  private final PermissionMapper permissionMapper;

  /**
   * Creates a new permission.
   * <p>
   * - Validates and converts the incoming request DTO to an entity.<br> - Persists the permission
   * via the service layer.<br> - Returns a standardized API response indicating success.
   *
   * @param request permission creation request
   * @return standardized API response indicating success
   */
  @PostMapping
  public ApiResponse<PermissionResponse> create(
      @RequestBody @Valid PermissionCreateRequest request) {

    return ApiResponse.<PermissionResponse>builder()
        .result(permissionService.create(request))
        .build();
  }

  /**
   * Retrieves all permissions.
   * <p>
   * - Fetches all permission entities from the database.<br> - Maps them to
   * {@link PermissionResponse} DTOs.<br> - Returns the list wrapped in a standardized API
   * response.
   *
   * @return standardized API response containing a list of all permissions
   */
  @GetMapping
  public ApiResponse<List<PermissionResponse>> getAll() {

    return ApiResponse.<List<PermissionResponse>>builder()
        .result(permissionService.getAll())
        .build();
  }

  /**
   * Updates an existing permission by name.
   * <p>
   * - Extracts the {@code name} from the path.<br> - Accepts updated permission information in the
   * request body.<br> - Calls the service to perform the update.<br> - Returns a standardized API
   * response indicating success.
   *
   * @param name       the name of the permission to update
   * @param updateData updated permission data
   * @return standardized API response indicating success
   */
  @PutMapping("/{name}")
  public ApiResponse<Void> update(@PathVariable String name,
      @RequestBody @Valid PermissionUpdateRequest updateData) {

    permissionService.update(name, updateData);
    return ApiResponse.<Void>builder()
        .build();
  }

  /**
   * Deletes a permission by name.
   * <p>
   * - Extracts the {@code name} from the path.<br> - Delegates the deletion to the service
   * layer.<br> - Returns {@code true} if the deletion succeeded.<br> - Wraps the result in a
   * standardized API response.
   *
   * @param name the name of the permission to delete
   * @return standardized API response indicating success or failure
   */
  @DeleteMapping("/{name}")
  public ApiResponse<Void> delete(@PathVariable String name) {
    permissionService.delete(name);
    return ApiResponse.<Void>builder().build();
  }
}
