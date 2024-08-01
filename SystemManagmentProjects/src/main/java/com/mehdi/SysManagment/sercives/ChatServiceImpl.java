package com.mehdi.SysManagment.sercives;

import org.springframework.stereotype.Service;

import com.mehdi.SysManagment.models.Chat;
import com.mehdi.SysManagment.repositories.ChatRepository;

@Service
public class ChatServiceImpl implements ChatService {

	private ChatRepository chatRepository;
	@Override
	public Chat createdChat(Chat chat) {
		return chatRepository.save(chat);
	}

}
