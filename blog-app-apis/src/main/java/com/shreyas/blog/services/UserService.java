package com.shreyas.blog.services;

import java.util.List;

import com.shreyas.blog.payloads.UserDto;

public interface UserService {     // We don't need to specify public access modifier in front of the members, since all the members of an interface are by default public unlike classes, where all the members have "default"(package specific) access modifier by default 
        UserDto createUser(UserDto user);
        UserDto updateUser(UserDto user,Integer id);
        UserDto getUserById(Integer userId);
        List<UserDto>getAllUsers();
        void deleteUser(Integer userId);
}
