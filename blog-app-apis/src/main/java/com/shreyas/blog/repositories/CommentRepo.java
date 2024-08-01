package com.shreyas.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shreyas.blog.entities.Comment;
import com.shreyas.blog.entities.Post;

public interface CommentRepo extends JpaRepository<Comment,Integer> {
	List<Comment>findByPost(Post post);
}
