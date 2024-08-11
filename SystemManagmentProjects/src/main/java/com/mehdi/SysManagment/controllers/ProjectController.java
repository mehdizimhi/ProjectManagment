package com.mehdi.SysManagment.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mehdi.SysManagment.models.Chat;
import com.mehdi.SysManagment.models.Invitation;
import com.mehdi.SysManagment.models.Project;
import com.mehdi.SysManagment.models.User;
import com.mehdi.SysManagment.request.InviteRequest;
import com.mehdi.SysManagment.response.MessageResponse;
import com.mehdi.SysManagment.sercives.InvitationService;
import com.mehdi.SysManagment.sercives.ProjectService;
import com.mehdi.SysManagment.sercives.UserService;

@RestController
@RequestMapping("api/projects")
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private InvitationService invitationService;
	
	@GetMapping
	public ResponseEntity<List<Project>> getProjects(
			@RequestParam(required = false) String caterogy,
			@RequestParam(required = false) String tag,
			@RequestHeader("Authorization") String jwt
			
			) throws Exception{
		
		User user = userService.findUserProfleByJwt(jwt);
		List<Project> projects = projectService.getProjectByTeam(user, jwt, tag);
		return new ResponseEntity<>(projects, HttpStatus.OK);
	}
	
	@GetMapping("/{projectId}")
	public ResponseEntity<Project> getProjectById(
			@PathVariable Long projectId,
			@RequestHeader("Authorization") String jwt
			
			) throws Exception {
		
		User user = userService.findUserProfleByJwt(jwt);
		Project project = projectService.getProjectById(projectId);
		return new ResponseEntity<>(project, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Project> createProject(
			@RequestHeader("Authorization") String jwt,
			@RequestBody Project project
			
			) throws Exception {
		
		User user = userService.findUserProfleByJwt(jwt);
		Project createdProject = projectService.createProject(project,user);
		return new ResponseEntity<>(createdProject, HttpStatus.OK);
	}
	
	@PatchMapping("/{projectId}")
	public ResponseEntity<Project> updateProject(
			@PathVariable Long projectId,
			@RequestHeader("Authorization") String jwt,
			@RequestBody Project project
			
			) throws Exception {
		
		User user = userService.findUserProfleByJwt(jwt);
		Project updatedProject = projectService.updateProject(project,projectId);
		return new ResponseEntity<>(updatedProject, HttpStatus.OK);
	}
	
	@DeleteMapping("/{projectId}")
	public ResponseEntity<MessageResponse> deleteProjectById(
			@PathVariable Long projectId,
			@RequestHeader("Authorization") String jwt
			
			) throws Exception {
		
		User user = userService.findUserProfleByJwt(jwt);
		projectService.deleteProject(projectId,user.getId());
		MessageResponse res = new MessageResponse("Project deleted successfully");
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<Project>> searchProject(
			@RequestParam(required = false) String keyword,
			@RequestHeader("Authorization") String jwt
			
			) throws Exception{
		
		User user = userService.findUserProfleByJwt(jwt);
		List<Project> projects = projectService.searchProjects(keyword,user);
		return new ResponseEntity<>(projects, HttpStatus.OK);
	}
	
	@GetMapping("/{projectId}/chat")
	public ResponseEntity<Chat> getChatByProjectId(
			@PathVariable Long projectId,
			@RequestHeader("Authorization") String jwt
			
			) throws Exception {
		
		User user = userService.findUserProfleByJwt(jwt);
		Chat chat = projectService.getChatByProjectId(projectId);
		return new ResponseEntity<>(chat, HttpStatus.OK);
	}
	
	@PostMapping("/invite")
	public ResponseEntity<MessageResponse> inviteProject(
			@RequestBody InviteRequest req,
			@RequestHeader("Authorization") String jwt,
			@RequestBody Project project
			
			) throws Exception {
		
		User user = userService.findUserProfleByJwt(jwt);
		invitationService.sendInvitation(req.getEmail(), req.getProjectId());
		MessageResponse res = new MessageResponse("User invitation sent");
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("/accept_invitation")
	public ResponseEntity<Invitation> acceptInvitationProject(
			@RequestParam String token,
			@RequestHeader("Authorization") String jwt,
			@RequestBody Project project
			
			) throws Exception {
		
		User user = userService.findUserProfleByJwt(jwt);
		Invitation inv = invitationService.acceptInvitation(token, user.getId());
		projectService.addUserToProject(inv.getProjetId(), user.getId());
		return new ResponseEntity<>(inv, HttpStatus.ACCEPTED);
	}
	
}
