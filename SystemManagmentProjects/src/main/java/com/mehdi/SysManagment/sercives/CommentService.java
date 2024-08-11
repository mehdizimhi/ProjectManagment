package com.mehdi.SysManagment.sercives;

import java.util.List;

import com.mehdi.SysManagment.models.Comments;

public interface CommentService {

	Comments createComment (Long issueId, Long userId, String comment) throws Exception;
	
	void deleteComment(Long commentId, Long userId) throws Exception;
	
	List<Comments> findCommentByIssueId(Long issueId);
}
