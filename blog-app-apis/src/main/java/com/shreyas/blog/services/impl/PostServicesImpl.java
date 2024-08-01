package com.shreyas.blog.services.impl;

import java.util.Date;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.shreyas.blog.entities.Category;
import com.shreyas.blog.entities.Post;
import com.shreyas.blog.entities.User;
import com.shreyas.blog.exceptions.ResourceNotFoundException;
import com.shreyas.blog.payloads.PostDto;
import com.shreyas.blog.payloads.PostResponse;
import com.shreyas.blog.repositories.CategoryRepo;
import com.shreyas.blog.repositories.PostRepo;
import com.shreyas.blog.repositories.UserRepo;
import com.shreyas.blog.services.PostService;

@Service
public class PostServicesImpl implements PostService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public PostDto createPost(PostDto postDto,Integer user_id,Integer category_id) {
//		System.out.printf("posting "+category_id+" ");
		User user=this.userRepo.findById(user_id).orElseThrow(() -> new ResourceNotFoundException("user","user_id",user_id));
		Category cat=this.categoryRepo.findById(category_id).orElseThrow(() -> new ResourceNotFoundException("category","cat_id",category_id));
		Post post=this.modelMapper.map(postDto,Post.class);
		post.setImageName("Default.img");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(cat);
		Post savedPost = this.postRepo.save(post);
		return this.modelMapper.map(savedPost, PostDto.class);
		
	}

	@Override
	public PostDto getPostById(int post_id) {
		Post post=this.postRepo.findById(post_id).orElseThrow(() -> new ResourceNotFoundException("Post","id",post_id));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostResponse getAllPosts(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		Sort sort= (sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		Pageable p = PageRequest.of(pageNumber, pageSize,sort);
		
		Page<Post>pagePosts = this.postRepo.findAll(p);
		List<Post>posts = pagePosts.getContent();
		List<PostDto>postsDto = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		PostResponse postRes = new PostResponse();
		postRes.setPostDtos(postsDto);
		postRes.setPageNumber(pagePosts.getNumber());
		postRes.setPageSize(pagePosts.getSize());
		postRes.setTotatElements(pagePosts.getNumberOfElements());
		postRes.setTotalPages(pagePosts.getTotalPages());
		postRes.setLast(pagePosts.isLast());
		return postRes;
	}

	@Override
	public PostDto updatePost(PostDto postDto, int post_id) {
	    Post post = this.postRepo.findById(post_id).orElseThrow(()->new ResourceNotFoundException("Post","ID",post_id));
	    post.setTitle(postDto.getTitle());
	    post.setContent(postDto.getContent());
	    post.setImageName(postDto.getImageName());
	    Post updatedPost = this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(int post_id) {
		Post post = this.postRepo.findById(post_id).orElseThrow(() -> new ResourceNotFoundException("Post","ID",post_id));
		this.postRepo.delete(post);
	}

	@Override
	public List<PostDto> getPostByUser(Integer user_id) {
		 User user = this.userRepo.findById(user_id).orElseThrow(()->new ResourceNotFoundException("User","user id",user_id));
		 List<Post>posts = this.postRepo.findByUser(user);
		 List<PostDto>postsDto = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		 return postsDto;
	}

	@Override
	public List<PostDto> getPostByCategory(Integer category_id) {
		Category cat = this.categoryRepo.findById(category_id).orElseThrow(()->new ResourceNotFoundException("Category","category_id",category_id));
		List<Post>posts = this.postRepo.findByCategory(cat);
		List<PostDto>postsDto = posts.stream().map(post -> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		return postsDto;
	}

	@Override
	public List<PostDto> searchPost(String keyword) {
		List<Post>posts = this.postRepo.findByTitleContains(keyword);
		List<PostDto>postDtos = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}
	
}
