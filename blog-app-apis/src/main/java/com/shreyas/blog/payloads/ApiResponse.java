package com.shreyas.blog.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor //no argument constructor is created automatically at the runtime if we don't specify @NoArgsConstructor. If we are using any other constructor with args. then we need to write this annotation so that we can create an object of this class without providing any arguments.
public class ApiResponse {
	public String message;
	public boolean success;
}
