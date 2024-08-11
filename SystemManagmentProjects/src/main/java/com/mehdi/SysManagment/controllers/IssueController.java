package com.mehdi.SysManagment.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mehdi.SysManagment.models.Issue;
import com.mehdi.SysManagment.models.IssueDTO;
import com.mehdi.SysManagment.models.User;
import com.mehdi.SysManagment.request.IssueRequest;
import com.mehdi.SysManagment.response.AuthResponse;
import com.mehdi.SysManagment.response.MessageResponse;
import com.mehdi.SysManagment.sercives.IssueService;
import com.mehdi.SysManagment.sercives.UserService;

@RestController
@RequestMapping("/api/issues")
public class IssueController {
	
	@Autowired
	private IssueService issueService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/{issueId}")
	public ResponseEntity<Issue> getIssueById(@PathVariable Long issueId) throws Exception {
		return ResponseEntity.ok(issueService.getIssueById(issueId));
	}
	
	@GetMapping("/project/{projectId}")
	public ResponseEntity<List<Issue>> getIssueByProjectId(@PathVariable Long projectId) throws Exception {
		return ResponseEntity.ok(issueService.getIssueByProjectId(projectId));
	}
	
	@PostMapping
	public ResponseEntity<IssueDTO> createIssue(@RequestBody IssueRequest issue, @RequestHeader ("Authorization") String token) throws Exception {
		User tokenUser = userService.findUserProfleByJwt(token);
		User user = userService.findUserById(tokenUser.getId());
		
			Issue createdIssue = issueService.createIssue(issue, tokenUser);
			IssueDTO dto = new IssueDTO();
			dto.setDescription(createdIssue.getDescription());
			dto.setDueDate(createdIssue.getDueDate());
			dto.setId(createdIssue.getId());
			dto.setPriority(createdIssue.getPriority());
			dto.setProject(createdIssue.getProject());
			dto.setProjectId(createdIssue.getProjectId());
			dto.setStatus(createdIssue.getStatus());
			dto.setTitle(createdIssue.getTitle());
			dto.setTags(createdIssue.getTags());
			dto.setAssignee(createdIssue.getAssignee());
			
			return ResponseEntity.ok(dto);
		
	}
	
	@DeleteMapping("/{issueId}")
		public ResponseEntity<MessageResponse> deleteIssue(@PathVariable Long issueId, @RequestHeader("Authorization") String token) throws Exception {
			User user = userService.findUserProfleByJwt(token);
			issueService.deleteIssue(issueId, user.getId());
			
			MessageResponse res = new MessageResponse();
			res.setMessage("Issue deleted");
			
			return ResponseEntity.ok(res);
		}
	
	@PutMapping("/{issueId}/assignee/{userId}")
	public ResponseEntity<Issue> addUserToIssue(@PathVariable Long issueId, @PathVariable Long userId) throws Exception{
		Issue issue = issueService.addUserToIssue(issueId, userId);
		
		return ResponseEntity.ok(issue);
	}
	
	@PutMapping("/{issueId}/assignee/{status}")
	public ResponseEntity<Issue> updateIssueStatus(@PathVariable String status, @PathVariable Long issueId) throws Exception{
		Issue issue = issueService.updateStatus(issueId, status);
		
		return ResponseEntity.ok(issue);
	}
	
	
	
	
}
