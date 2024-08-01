package com.shreyas.blog.payloads;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	private int id;
	
	@NotEmpty    //@NotEmty is a combination of @NotNull and @NotBlank
	@Size(min=4,message="Username must be of at least 4 letters!!")
	private String name;
	
	@Email(message="Email address is not valid!!")
	private String email;
	
	@NotEmpty
	@Size(min=3,max=12,message="Password must be withn 3-12 letters")
	// @Pattern()  - we can add this annotation to check the regex pattern.
	private String password;
	
	@NotEmpty
	private String about;
	
	private List<PostDto>posts = new ArrayList();
}
