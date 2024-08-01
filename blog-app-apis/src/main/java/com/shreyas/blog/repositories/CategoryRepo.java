package com.shreyas.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shreyas.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category,Integer> {

}
