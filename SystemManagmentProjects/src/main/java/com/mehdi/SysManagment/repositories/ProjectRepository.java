package com.mehdi.SysManagment.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mehdi.SysManagment.models.Project;
import com.mehdi.SysManagment.models.User;

public interface ProjectRepository extends JpaRepository<Project, Long>{
	
	List<Project> findByOwner(User user);
	
	List<Project> findByNameContainingAndTeamContaining(String partialName, User user);
	
	@Query("Select p from Project p join p.team t where t=:user")
	List<Project> findByTeam(@Param("user") User user);
	
	List<Project> findByTeamContainingOrOwner(User user, User owner);
	
	

}
