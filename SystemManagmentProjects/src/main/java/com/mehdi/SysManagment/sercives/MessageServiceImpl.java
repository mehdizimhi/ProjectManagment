package com.mehdi.SysManagment.sercives;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mehdi.SysManagment.models.Chat;
import com.mehdi.SysManagment.models.Message;
import com.mehdi.SysManagment.models.User;
import com.mehdi.SysManagment.repositories.MessageRepository;
import com.mehdi.SysManagment.repositories.ProjectRepository;
import com.mehdi.SysManagment.repositories.UserRepository;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageRepository messageRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProjectService projectService;
	
	
	@Override
	public Message sendMessage(Long senderId, Long projectId, String content) throws Exception {
		User sender = userRepository.findById(senderId).orElseThrow(() -> new Exception("User not founs with this id" + senderId));
		
		Chat chat = projectService.getProjectById(projectId).getChat();
		
		Message msg = new Message();
		msg.setContent(content);
		msg.setSender(sender);
		msg.setCreatedAt(LocalDateTime.now());
		msg.setChat(chat);
		
		Message msgSaved = messageRepository.save(msg);
		
		chat.getMessages().add(msgSaved);
		return msgSaved;
	}

	@Override
	public List<Message> getMessagesByProjectId(Long projectId) throws Exception {
		Chat chat = projectService.getChatByProjectId(projectId);
		List<Message> findByChatIdOrberByCreatedAtAsc = messageRepository.findByChatIdOrberByCreatedAtAsc(chat.getId());
		
		return findByChatIdOrberByCreatedAtAsc;
	}

}
