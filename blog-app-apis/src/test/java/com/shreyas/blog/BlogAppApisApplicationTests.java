package com.shreyas.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shreyas.blog.repositories.UserRepo;
import com.shreyas.blog.services.UserService;

@SpringBootTest
class BlogAppApisApplicationTests {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired 
	private UserService usr;
	
	@Test
	void contextLoads() {
	}
	
//	@Test
//	public void repoTest() {
//		System.out.println(userRepo.getClass().getName());
//		System.out.println(userRepo.getClass().getPackageName());
//	}

	@Test
	public void serviceTest() {
		System.out.println(usr.getClass().getName());
	}

}
