package com.mehdi.SysManagment.sercives;

import java.util.List;

import com.mehdi.SysManagment.models.Project;
import com.mehdi.SysManagment.models.User;

public interface ProjectService {

	Project createProject(Project project, User user) throws Exception;
	
	List<Project> getProjectByTeam(User user, String category, String tag) throws Exception;
	
	Project getProjectById(Long projectId) throws Exception;
	
	void deleteProject(Long projectId, Long userId) throws Exception;
	
	Project updateProject(Project updatedProject , Long id) throws Exception;
	
	void addUserToProject(Long projectId, Long userId) throws Exception;
	
	
	
	
	 
}
