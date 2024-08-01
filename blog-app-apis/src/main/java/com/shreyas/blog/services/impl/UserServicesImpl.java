package com.shreyas.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shreyas.blog.entities.User;
import com.shreyas.blog.payloads.UserDto;
import com.shreyas.blog.repositories.UserRepo;
import com.shreyas.blog.services.UserService;
import com.shreyas.blog.exceptions.ResourceNotFoundException;

@Service
public class UserServicesImpl implements UserService {
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;   //"userRepo" object(bean) is injected into this service class - dependency injection.
	@Override
	public UserDto createUser(UserDto userDto) {
		
		User user = dtoToUser(userDto);
		User savedUser = this.userRepo.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "id",userId));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());
		User updatedUser = this.userRepo.save(user);
		UserDto userDto1 = userToDto(updatedUser);
		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "id",userId));
		
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User>users = this.userRepo.findAll();
		List<UserDto>usersDto = users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
		return usersDto;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user","id",userId));
		this.userRepo.delete(user);

	}
	
	private User dtoToUser(UserDto userDto) {
//		User user=new User();
//		user.setId(userDto.getId());
//		user.setEmail(userDto.getEmail());
//		user.setPassword(userDto.getPassword());
//		user.setName(userDto.getName());
//		user.setAbout(userDto.getAbout());
		
		User user=modelMapper.map(userDto, User.class);
		return user;
	}
	
	private UserDto userToDto(User user) {
		UserDto userDto=modelMapper.map(user, UserDto.class);
		return userDto;
	}

}
