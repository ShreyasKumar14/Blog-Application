package com.shreyas.blog.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
	private int category_id;
	
	@NotBlank
	@Size(min=4,message="Minimum title length should be of 4 letters")
	private String categoryTitle;
	
	@NotBlank
	@Size(min=20)
	private String categoryDescription;
}
