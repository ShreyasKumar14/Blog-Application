package com.shreyas.blog.payloads;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostResponse {
	private List<PostDto>postDtos;
	private int pageNumber;
	private int pageSize;
	private int totatElements;
	private int totalPages;
	private boolean isLast;
}
