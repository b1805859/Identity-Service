package com.identityservice.mapper;


import com.identityservice.dto.request.role.RoleCreateRequest;
import com.identityservice.dto.request.role.RoleUpdateRequest;
import com.identityservice.dto.response.role.RoleResponse;
import com.identityservice.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    void toRole(@MappingTarget Role role, Role updateData);

    Role toRole(RoleCreateRequest request);

    RoleResponse toRoleResponse(Role role);

    Role toRole(RoleUpdateRequest request);
}
