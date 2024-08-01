package com.shreyas.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shreyas.blog.entities.Comment;
import com.shreyas.blog.entities.Post;
import com.shreyas.blog.exceptions.ResourceNotFoundException;
import com.shreyas.blog.payloads.CommentDto;
import com.shreyas.blog.repositories.CommentRepo;
import com.shreyas.blog.repositories.PostRepo;
import com.shreyas.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepo cmtRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDto createComment(CommentDto cmtDto,Integer postID) {
		Comment comment = this.modelMapper.map(cmtDto, Comment.class);
		Post post = this.postRepo.findById(postID).orElseThrow(() -> new ResourceNotFoundException("Post","post_id",postID));
		comment.setPost(post);
		Comment savedComment = this.cmtRepo.save(comment);
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer cmtId) {
		Comment cmt = this.cmtRepo.findById(cmtId).orElseThrow(() -> new ResourceNotFoundException("Comment","comment_id",cmtId));
		this.cmtRepo.delete(cmt);
	}

	@Override
	public List<CommentDto> getAllComments() {
	    List<Comment>getAll = this.cmtRepo.findAll();
	    List<CommentDto>getAllcmt = getAll.stream().map(cmt -> this.modelMapper.map(cmt, CommentDto.class)).collect(Collectors.toList());
		return getAllcmt;
	}

}
