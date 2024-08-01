package com.shreyas.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shreyas.blog.entities.Category;
import com.shreyas.blog.entities.Post;
import com.shreyas.blog.entities.User;

public interface PostRepo extends JpaRepository<Post,Integer> {
	List<Post>findByUser(User user);
	List<Post>findByCategory(Category category);
	List<Post>findByTitleContains(String keyword);
}
