package com.jwt.demo.service;

import com.jwt.demo.dto.UserEntityDto;

import java.util.List;

public interface UserEntityService {

    UserEntityDto createUser(UserEntityDto userEntityDto, int roleId);

    UserEntityDto getUserById(int userId);

    List<UserEntityDto> getAllUsers();

}
