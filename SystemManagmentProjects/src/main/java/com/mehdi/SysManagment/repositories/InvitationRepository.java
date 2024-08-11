package com.mehdi.SysManagment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mehdi.SysManagment.models.Invitation;

public interface InvitationRepository extends JpaRepository<Invitation, Long>{

	Invitation findByToken(String token);
	
	Invitation findByEmail(String email);
	
	//test
}
