package com.shreyas.blog.services;

import java.util.List;

import com.shreyas.blog.entities.Category;
import com.shreyas.blog.entities.User;
import com.shreyas.blog.payloads.PostDto;
import com.shreyas.blog.payloads.PostResponse;

public interface PostService {
	PostDto createPost(PostDto postDto,Integer user_id,Integer category_id);
	PostDto getPostById(int post_id);
	PostResponse getAllPosts(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	PostDto updatePost(PostDto postDto,int post_id);
	void deletePost(int post_id);
	List<PostDto>getPostByUser(Integer user_id);
	List<PostDto>getPostByCategory(Integer category_id);
	List<PostDto>searchPost(String keyword);
}
