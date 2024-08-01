package com.shreyas.blog.services;

import java.util.List;

import com.shreyas.blog.payloads.CategoryDto;

public interface CategoryService {  //by default all the members will be public in an interface.
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(CategoryDto categoryDto,Integer category_id);
    void deleteCategory(Integer category_id);
    CategoryDto getCategory(Integer category_id);
    List<CategoryDto>getAllCategories();
}
