package com.identityservice.mapper;


import com.identityservice.dto.request.user.UserCreateRequest;
import com.identityservice.dto.request.user.UserUpdateRequest;
import com.identityservice.dto.response.user.UserInfoResponse;
import com.identityservice.entity.User;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

  User toUser(UserCreateRequest request);

  void toUser(@MappingTarget User user, UserUpdateRequest updateData);

  UserInfoResponse toUserInfoResponse(User user);

  List<UserInfoResponse> toUsersInfoResponse(List<User> user);

}
