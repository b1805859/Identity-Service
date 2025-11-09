package com.identityservice.mapper;

import com.identityservice.dto.request.permission.PermissionCreateRequest;
import com.identityservice.dto.request.permission.PermissionUpdateRequest;
import com.identityservice.dto.response.permission.PermissionResponse;
import com.identityservice.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    void toPermission(@MappingTarget Permission permission, Permission updateData);

    Permission toPermission(PermissionCreateRequest request);

    PermissionResponse toPermissionResponse(Permission role);

    Permission toPermission(PermissionUpdateRequest request);
}
