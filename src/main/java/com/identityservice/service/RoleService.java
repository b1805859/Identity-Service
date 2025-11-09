package com.identityservice.service;


import com.identityservice.common.exception.AppException;
import com.identityservice.common.exception.ErrorCode;
import com.identityservice.entity.Role;
import com.identityservice.mapper.RoleMapper;
import com.identityservice.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    /**
     * Creates a new role if it does not already exist.
     *
     * @param request the role entity to create
     * @throws AppException if the role name already exists
     */
    @Transactional
    public Void create(Role request) {
        if (roleRepository.existsByName(request.getName()))
            throw new AppException(ErrorCode.ROLE_ALREADY_EXISTS);

        try {
            roleRepository.save(request);
        } catch (Exception e) {
            throw new AppException(ErrorCode.DATABASE_ERROR);
        }

        return null;
    }

    /**
     * Retrieves all roles from the database.
     *
     * @return list of all roles
     * @throws AppException if a database access error occurs
     */
    public List<Role> getAll() {
        try {
            return roleRepository.findAll();
        } catch (Exception e) {
            throw new AppException(ErrorCode.DATABASE_ERROR);
        }
    }

    /**
     * Deletes a role by its name.
     *
     * @param name the name of the role to delete
     * @throws AppException if the role does not exist or database operation fails
     */
    @Transactional
    public Void delete(String name) {
        if (!roleRepository.existsByName(name)) {
            throw new AppException(ErrorCode.ROLE_NOT_FOUND);
        }

        try {
            roleRepository.deleteByName(name);
        } catch (Exception e) {
            throw new AppException(ErrorCode.DATABASE_ERROR);
        }

        return null;
    }

    /**
     * Updates an existing role with new data.
     *
     * @param name       the name of the role to update
     * @param updateData the new data for the role
     * @throws AppException if the role does not exist or database operation fails
     */
    @Transactional
    public Void update(String name, Role updateData) {

        Role role = roleRepository.findByName(name).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND));

        try {
            roleMapper.toRole(role, updateData); // Map new data into the existing entity
            roleRepository.save(role);
        } catch (Exception e) {
            throw new AppException(ErrorCode.DATABASE_ERROR);
        }

        return null;
    }
}
