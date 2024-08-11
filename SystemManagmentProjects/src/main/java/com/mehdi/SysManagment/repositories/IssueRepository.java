package com.mehdi.SysManagment.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mehdi.SysManagment.models.Issue;

public interface IssueRepository extends JpaRepository<Issue, Long> {
	
	public List<Issue> findByProjectId(Long id);

}
