package com.mehdi.SysManagment.sercives;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mehdi.SysManagment.models.Chat;
import com.mehdi.SysManagment.models.Project;
import com.mehdi.SysManagment.models.User;
import com.mehdi.SysManagment.repositories.ProjectRepository;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ChatService chatService;
	
	@Override
	public Project createProject(Project project, User user) throws Exception {
		Project createdProject = new Project();
		
		createdProject.setOwner(user);
		createdProject.setTags(project.getTags());
		createdProject.setName(project.getName());
		createdProject.setCategory(project.getCategory());
		createdProject.setDescription(project.getDescription());
		createdProject.getTeam().add(user);
		
		Project savedProject = projectRepository.save(createdProject);
		
		Chat chat = new Chat();
		chat.setProject(savedProject);
		
		Chat projectChat = chatService.createdChat(chat);
		savedProject.setChat(projectChat);
		return savedProject;
	}

	@Override
	public List<Project> getProjectByTeam(User user, String category, String tag) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Project getProjectById(Long projectId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteProject(Long projectId, Long userId) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Project updateProject(Project updatedProject, Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addUserToProject(Long projectId, Long userId) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeUserFromProject(Long projectId, Long userId) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Chat getChatByProjectId(Long projectId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
