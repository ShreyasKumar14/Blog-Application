package com.shreyas.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shreyas.blog.entities.User;
import com.shreyas.blog.exceptions.UserNotFoundException;
import com.shreyas.blog.repositories.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService{
	
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("load");
		User user = this.userRepo.findByEmail(username).orElseThrow(()->new UserNotFoundException("User","name",username));
//		System.out.println("test");
		return user;
	}

}
