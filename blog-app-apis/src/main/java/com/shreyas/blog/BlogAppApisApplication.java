package com.shreyas.blog;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BlogAppApisApplication{

//	@Autowired 
//	private PasswordEncoder passwordEncoder;
	
	
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}
	
	
	@Bean
	public ModelMapper modelMapper() {  	   //ModelMapper is a class that is used to map one object to another and visa versa. (convert one object to another)
		return new ModelMapper();
	}


//	@Override
//	public void run(String... args) throws Exception {
//		System.out.println(this.passwordEncoder.encode("1234"));
//	}
	

}
