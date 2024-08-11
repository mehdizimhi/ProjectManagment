package com.mehdi.SysManagment.sercives;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mehdi.SysManagment.models.Issue;
import com.mehdi.SysManagment.models.Project;
import com.mehdi.SysManagment.models.User;
import com.mehdi.SysManagment.repositories.IssueRepository;
import com.mehdi.SysManagment.request.IssueRequest;

@Service
public class IssueServiceImpl implements IssueService {

	@Autowired
	private IssueRepository issueRepository;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private UserService userService;
	
	@Override
	public Issue getIssueById(Long issueId) throws Exception {
		Optional<Issue> issue = issueRepository.findById(issueId);
		if(issue.isPresent()) {
			return issue.get();
		}
		throw new Exception("No issue found with this issue Id" + issueId);
	}

	@Override
	public List<Issue> getIssueByProjectId(Long projectId) throws Exception {
		return issueRepository.findByProjectId(projectId);
	}

	@Override
	public Issue createIssue(IssueRequest issueReq, User user) throws Exception {
		Project p = projectService.getProjectById(issueReq.getProjectId());
		
		Issue issue = new Issue();
		issue.setTitle(issueReq.getTitle());
		issue.setDescription(issueReq.getDescription());
		issue.setStatus(issueReq.getStatus());
		issue.setProjectId(issueReq.getProjectId());
		issue.setPriority(issueReq.getPriority());
		issue.setDueDate(issueReq.getDueDate());
		
		issue.setProject(p);
		
		return issueRepository.save(issue);
	}

	@Override
	public void deleteIssue(Long issueId, Long userid) throws Exception {
		getIssueById(issueId);
		issueRepository.deleteById(issueId);
	}

	@Override
	public Issue addUserToIssue(Long issueId, Long userId) throws Exception {
		User user = userService.findUserById(userId);
		Issue issue=getIssueById(issueId);
		
		issue.setAssignee(user);
		
		return issueRepository.save(issue);
	}

	@Override
	public Issue updateStatus(Long issueId, String status) throws Exception {
		Issue issue = getIssueById(issueId);
		
		issue.setStatus(status);
		return issueRepository.save(issue);
	}

}
