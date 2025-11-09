package com.identityservice.mapper;


import com.identityservice.dto.request.user.UserCreateRequest;
import com.identityservice.dto.request.user.UserUpdateRequest;
import com.identityservice.dto.response.user.UserCreateResponse;
import com.identityservice.dto.response.user.UserInfoResponse;
import com.identityservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserCreateRequest request);

    UserCreateResponse toUserCreateResponse(User user);

    User toUser(UserUpdateRequest request);

    UserInfoResponse toUserInfoResponse(User user);

    void toUser(@MappingTarget User user, User updateData);
}
