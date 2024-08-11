package com.mehdi.SysManagment.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mehdi.SysManagment.models.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

	List<Message> findByChatIdOrberByCreatedAtAsc(Long chatId);
}
