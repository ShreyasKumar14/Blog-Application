package com.shreyas.blog.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
	private int id;
	
	@NotEmpty
	@Size(min=6,message="Minimum 6 characters should be there in the content!!")
	private String content;
	
//	private PostDto post;
}
