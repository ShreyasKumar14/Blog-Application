package com.shreyas.blog.services;

import java.util.List;

import com.shreyas.blog.payloads.CommentDto;

public interface CommentService {
	CommentDto createComment(CommentDto cmtDto,Integer postId);
	void deleteComment(Integer cmtId);
	List<CommentDto>getAllComments();
}
