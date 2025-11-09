package com.identityservice.service;

import com.identityservice.common.exception.AppException;
import com.identityservice.common.exception.ErrorCode;
import com.identityservice.entity.Permission;
import com.identityservice.mapper.PermissionMapper;
import com.identityservice.repository.PermissionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<Permission> getAll() {
        return permissionRepository.findAll();
    }

    /**
     * Create a new permission.
     * If a permission with the same name already exists, throws {@link AppException} with {@code PERMISSION_ALREADY_EXISTS}.
     *
     * @param request the {@link Permission} entity containing the data to be created
     * @throws AppException if the permission already exists or if a database error occurs
     */
    @Transactional
    public Void create(Permission request) {
        if (permissionRepository.existsByName(request.getName()))
            throw new AppException(ErrorCode.PERMISSION_ALREADY_EXISTS);

        try {
            permissionRepository.save(request);
        } catch (Exception ex) {
            // Catch any database-level exception and wrap it in a standardized AppException
            throw new AppException(ErrorCode.DATABASE_ERROR);
        }

        return null;
    }

    /**
     * Update an existing permission by its name.
     * If the permission does not exist, throws {@link AppException} with {@code PERMISSION_NOT_FOUND}.
     *
     * @param name       the name of the permission to update
     * @param updateData the {@link Permission} entity containing updated data
     * @throws AppException if the permission does not exist or if a database error occurs
     */
    @Transactional
    public Void update(String name, Permission updateData) {

        Permission permission = permissionRepository.findByName(name)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));

        try {
            // Map the updated fields to the existing entity
            permissionMapper.toPermission(permission, updateData);

            // Save the updated entity to the database
            permissionRepository.save(permission);
        } catch (Exception ex) {
            throw new AppException(ErrorCode.DATABASE_ERROR);
        }

        return null;
    }

    /**
     * Delete a permission by its name.
     * If the permission does not exist, throws {@link AppException} with {@code PERMISSION_NOT_FOUND}.
     *
     * @param name the name of the permission to delete
     * @throws AppException if the permission does not exist or if a database error occurs
     */
    @Transactional
    public Void delete(String name) {
        if (!permissionRepository.existsByName(name)) {
            throw new AppException(ErrorCode.PERMISSION_NOT_FOUND);
        }

        try {
            permissionRepository.deleteByName(name);
        } catch (Exception ex) {
            throw new AppException(ErrorCode.DATABASE_ERROR);
        }

        return null;
    }
}
