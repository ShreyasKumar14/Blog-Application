package com.shreyas.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shreyas.blog.entities.Category;
import com.shreyas.blog.exceptions.ResourceNotFoundException;
import com.shreyas.blog.payloads.CategoryDto;
import com.shreyas.blog.payloads.UserDto;
import com.shreyas.blog.repositories.CategoryRepo;
import com.shreyas.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	ModelMapper modelMapper;
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		
	    Category category = modelMapper.map(categoryDto, Category.class);
	    Category savedCategory = this.categoryRepo.save(category);
	    return modelMapper.map(savedCategory, CategoryDto.class);
//		return null;
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer category_id) {
		Category cat = categoryRepo.findById(category_id).orElseThrow(()->new ResourceNotFoundException("Category","Category id",category_id));
		
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDescription(categoryDto.getCategoryDescription());
		
		Category updatedCat = categoryRepo.save(cat);
		
		return this.modelMapper.map(updatedCat,CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer category_id) {
		Category cat=categoryRepo.findById(category_id).orElseThrow(()->new ResourceNotFoundException("Category","Category id",category_id));
		categoryRepo.delete(cat);
		
	}

	@Override
	public CategoryDto getCategory(Integer category_id) {
		Category cat=categoryRepo.findById(category_id).orElseThrow(()->new ResourceNotFoundException("Category","Category id",category_id));
		return this.modelMapper.map(cat, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		List<Category>categoryList = this.categoryRepo.findAll();
		List<CategoryDto>categoryDtoList = categoryList.stream().map((cat) -> this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		return categoryDtoList;
	}

}
