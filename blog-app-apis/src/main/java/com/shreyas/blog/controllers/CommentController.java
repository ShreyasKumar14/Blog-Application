package com.shreyas.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shreyas.blog.payloads.ApiResponse;
import com.shreyas.blog.payloads.CommentDto;
import com.shreyas.blog.services.CommentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/")
public class CommentController {
	
	@Autowired
	private CommentService cmtService;
	
	@PostMapping("saveComment/{postID}")
	public ResponseEntity<CommentDto>saveComment(@Valid CommentDto cmtDto,@PathVariable Integer postID){
		CommentDto savedCmtDto = this.cmtService.createComment(cmtDto,postID);
		return ResponseEntity.ok(savedCmtDto);
	}
	
	@GetMapping("getAllComments")
	public ResponseEntity<List<CommentDto>>getAll(){
		return ResponseEntity.ok(this.cmtService.getAllComments());
	}
	
	@DeleteMapping("deletecomment/{cmtId}")
	public ResponseEntity<ApiResponse>deleteComment(@PathVariable Integer cmtId){
		this.cmtService.deleteComment(cmtId);
		return ResponseEntity.ok(new ApiResponse("Comment deleted successfully",true));
	}
}
