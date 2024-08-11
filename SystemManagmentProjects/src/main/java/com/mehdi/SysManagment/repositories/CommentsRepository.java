package com.mehdi.SysManagment.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mehdi.SysManagment.models.Comments;

public interface CommentsRepository extends JpaRepository<Comments, Long> {

	List<Comments> findByIssueId(Long issueId);
}
