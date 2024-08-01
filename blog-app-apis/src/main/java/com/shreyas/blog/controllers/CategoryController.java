package com.shreyas.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shreyas.blog.payloads.ApiResponse;
import com.shreyas.blog.payloads.CategoryDto;
import com.shreyas.blog.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/")
	public ResponseEntity<CategoryDto>createCategory(@Valid @RequestBody CategoryDto category){
		CategoryDto catDto = categoryService.createCategory(category);
		return new ResponseEntity<>(catDto,HttpStatus.CREATED);
	}
	
	@GetMapping("/{cat_id}")
	public ResponseEntity<CategoryDto>getCategoryById(@PathVariable("cat_id") int category_id){
		CategoryDto catDto = categoryService.getCategory(category_id);
		return new ResponseEntity<>(catDto,HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>>getAllCategories(){
		List<CategoryDto>catDtoList = categoryService.getAllCategories();
		return new ResponseEntity<>(catDtoList,HttpStatus.OK);
	}
	
	@PutMapping("/{category_id}")
	public ResponseEntity<CategoryDto>updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable int category_id){
		CategoryDto catDto=categoryService.updateCategory(categoryDto, category_id);	
		return new ResponseEntity<>(catDto,HttpStatus.OK);
	}
	
	@DeleteMapping("{category_id}")
	public ResponseEntity<ApiResponse>deleteCategory(@PathVariable int category_id){
		categoryService.deleteCategory(category_id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted successfully!!",true),HttpStatus.OK);
	}
	
}
