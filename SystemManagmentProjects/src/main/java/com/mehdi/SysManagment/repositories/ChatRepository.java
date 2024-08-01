package com.mehdi.SysManagment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mehdi.SysManagment.models.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {
	
	
}
