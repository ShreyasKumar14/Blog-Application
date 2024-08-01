package com.shreyas.blog.payloads;

import java.util.Date;
import java.util.List;

import com.shreyas.blog.entities.Category;
import com.shreyas.blog.entities.Comment;
import com.shreyas.blog.entities.User;

import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {
	private int postID;
	@NotEmpty
	@Size(min=4,message="Min. characters should at least 4!!")
    private String title;
	
	@NotEmpty
	@Size(min=8)
    private String content;
	
    private String imageName;
    private Date addedDate;
    private CategoryDto category;
//	private UserDto user;
	private List<CommentDto>comments;
}
