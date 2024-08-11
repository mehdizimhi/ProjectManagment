package com.mehdi.SysManagment.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mehdi.SysManagment.models.Chat;
import com.mehdi.SysManagment.models.Message;
import com.mehdi.SysManagment.models.User;
import com.mehdi.SysManagment.request.CreateMessageRequest;
import com.mehdi.SysManagment.sercives.MessageService;
import com.mehdi.SysManagment.sercives.ProjectService;
import com.mehdi.SysManagment.sercives.UserService;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

	@Autowired
	private MessageService messageService;
	@Autowired
	private UserService userService;
	@Autowired
	private ProjectService projectService;
	
	@PostMapping("/send")
	public ResponseEntity<Message> sendMessage(@RequestBody CreateMessageRequest req) throws Exception{
		
		User user = userService.findUserById(req.getSenderId());
		
		Chat chats = projectService.getProjectById(req.getProjectId()).getChat();
		
		if(chats == null) throw new Exception("Chats not found");
		
		Message sendMessage = messageService.sendMessage(req.getSenderId(), req.getProjectId(), req.getContent());
		return ResponseEntity.ok(sendMessage);
	}
	
	@GetMapping("/chat/{projectId)")
	public ResponseEntity<List<Message>> getMessagesByChatId(@PathVariable Long projectId) throws Exception{
		List<Message> messages = messageService.getMessagesByProjectId(projectId);
		return ResponseEntity.ok(messages);
	}
	
}
