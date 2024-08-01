package com.shreyas.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shreyas.blog.config.AppConstants;
import com.shreyas.blog.payloads.ApiResponse;
import com.shreyas.blog.payloads.ImageResponse;
import com.shreyas.blog.payloads.PostDto;
import com.shreyas.blog.payloads.PostResponse;
import com.shreyas.blog.services.PostService;
import com.shreyas.blog.services.FileServices;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileServices fileservice;
	
	@Value("${project.image}")
	private String path;
	
	@PostMapping("user/{user_id}/category/{category_id}/posts")
	public ResponseEntity<PostDto>createPost(@Valid @RequestBody PostDto postDto,@PathVariable Integer user_id,@PathVariable Integer category_id){
		PostDto savedPostDto = this.postService.createPost(postDto, user_id, category_id);
		return new ResponseEntity<>(savedPostDto,HttpStatus.CREATED);
	}
	
	@GetMapping("user/{user_id}/posts")
	public ResponseEntity<List<PostDto>>getPostsByUser(@PathVariable Integer user_id){
		List<PostDto>postsByUser = this.postService.getPostByUser(user_id);
		return ResponseEntity.ok(postsByUser);
	}
	
	@GetMapping("category/{category_id}/posts")
	public ResponseEntity<List<PostDto>>getPostsByCategory(@PathVariable Integer category_id){
		List<PostDto>postsByCategory = this.postService.getPostByCategory(category_id);
		return ResponseEntity.ok(postsByCategory);
	}
	
	@GetMapping("posts")
	public ResponseEntity<PostResponse>getAllPosts(@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required=false) Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue=AppConstants.PAGE_SIZE, required=false) Integer pageSize,
			@RequestParam(value = "sortBy",defaultValue="postID",required=false) String sortBy,
			@RequestParam(value = "sortDir",defaultValue="asc",required=false) String sortDir){
		return ResponseEntity.ok(this.postService.getAllPosts(pageNumber, pageSize,sortBy,sortDir));
	}
	
	@GetMapping("{post_id}/posts")
	public ResponseEntity<PostDto>getPostById(@PathVariable Integer post_id){
		PostDto postDto = this.postService.getPostById(post_id);
		return ResponseEntity.ok(postDto);
	}
	
	@PutMapping("{post_id}/posts")
	public ResponseEntity<PostDto>updatePost(@Valid @RequestBody PostDto post,@PathVariable Integer post_id){
		PostDto postDto = this.postService.updatePost(post, post_id);
		return ResponseEntity.ok(postDto);
	}
	
	@DeleteMapping("{post_id}/posts")
	public ResponseEntity<ApiResponse>deletePost(@PathVariable Integer post_id){
		this.postService.deletePost(post_id);
		return new ResponseEntity<>(new ApiResponse("Post deleted successfully!!",true),HttpStatus.OK);
	}
	
	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<List<PostDto>>searchPost(@PathVariable String keyword){
		List<PostDto>posts = this.postService.searchPost(keyword);
		return ResponseEntity.ok(posts);
	}
	
	@PostMapping("/posts/file/upload/{postId}")
	public ResponseEntity<ImageResponse>uploadImage(@RequestParam("image") MultipartFile image,@PathVariable Integer postId) throws IOException{
		PostDto postDto = this.postService.getPostById(postId);
		String fileName = this.fileservice.uploadImage(path, image);
		postDto.setImageName(fileName);
		PostDto updatedPostDto = this.postService.updatePost(postDto, postId);
		
		return new ResponseEntity<>(new ImageResponse(fileName,true),HttpStatus.OK);
	}
	
	@GetMapping("/posts/file/getImage/{imageName}")
    public void getImage(@PathVariable String imageName,HttpServletResponse response) throws IOException{
		InputStream resource = this.fileservice.getImage(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
}
