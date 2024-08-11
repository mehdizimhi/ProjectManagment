package com.mehdi.SysManagment.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mehdi.SysManagment.models.User;
import com.mehdi.SysManagment.models.Comments;
import com.mehdi.SysManagment.request.CreateCommentRequest;
import com.mehdi.SysManagment.response.MessageResponse;
import com.mehdi.SysManagment.sercives.CommentService;
import com.mehdi.SysManagment.sercives.UserService;

@RestController
@RequestMapping("/api/comments")
public class CommentsController {
	
	@Autowired 
	private CommentService commentService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping()
	public ResponseEntity<Comments> createComment(@RequestBody CreateCommentRequest req, @RequestHeader("Authorization") String jwt) throws Exception {
		User user = userService.findUserProfleByJwt(jwt);
		Comments createdComment = commentService.createComment(req.getIssueId(), user.getId(), req.getContent());
		
		return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
		
	}
	
	@DeleteMapping("{/commentId}")
	public ResponseEntity<MessageResponse> deleteComment(@PathVariable Long commentId, @RequestHeader("Authorization") String jwt) throws Exception{
		User user = userService.findUserProfleByJwt(jwt);
		commentService.deleteComment(commentId, user.getId());
		MessageResponse res = new MessageResponse();
		res.setMessage("Comment deleted successfully");
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("/{issueId}")
	public ResponseEntity<List<Comments>> getCommentByIssueId(@PathVariable Long issueId) {
		List<Comments> comments = commentService.findCommentByIssueId(issueId);
		return new ResponseEntity<>(comments, HttpStatus.OK);
	}
	
}
