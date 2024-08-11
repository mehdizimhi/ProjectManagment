package com.mehdi.SysManagment.sercives;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mehdi.SysManagment.models.Comments;
import com.mehdi.SysManagment.models.Issue;
import com.mehdi.SysManagment.models.User;
import com.mehdi.SysManagment.repositories.CommentsRepository;
import com.mehdi.SysManagment.repositories.IssueRepository;
import com.mehdi.SysManagment.repositories.UserRepository;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentsRepository commentsRepository;
	
	@Autowired
	private IssueRepository issueRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public Comments createComment(Long issueId, Long userId, String content) throws Exception {
		Optional<Issue> issueOpt = issueRepository.findById(issueId);
		Optional<User> userOpt = userRepository.findById(userId);
		
		if(issueOpt.isEmpty()) {
			throw new Exception("issue not found with this id"+ issueId);
		}
		if(userOpt.isEmpty()) {
			throw new Exception("user not found with this id"+ userId);
		}
		
		Issue issue = issueOpt.get();
		User user = userOpt.get();
		
		Comments comment = new Comments();
		comment.setIssue(issue);
		comment.setUser(user);
		comment.setCreatedDateTime(LocalDateTime.now());
		comment.setContent(content);
		
		Comments savedComment = commentsRepository.save(comment);
		
		issue.getComments().add(savedComment);
		
		return savedComment;
	}

	@Override
	public void deleteComment(Long commentId, Long userId) throws Exception {
		Optional<Comments> commentsOpt = commentsRepository.findById(commentId);
		Optional<User> userOpt = userRepository.findById(userId);
		
		if(commentsOpt.isEmpty()) {
			throw new Exception("comment not found with this id"+ commentId);
		}
		if(userOpt.isEmpty()) {
			throw new Exception("user not found with this id"+ userId);
		}
		
		Comments cmt = commentsOpt.get();
		User usr = userOpt.get();
		
		if (cmt.getUser().equals(usr)) {
			commentsRepository.delete(cmt);
		}
		else {
			throw new Exception("User does not have permission to delete this comment");
		}
		
		
	}

	@Override
	public List<Comments> findCommentByIssueId(Long issueId) {
		return commentsRepository.findByIssueId(issueId);
	}

}
