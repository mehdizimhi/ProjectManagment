package com.mehdi.SysManagment.sercives;

import java.util.List;

import com.mehdi.SysManagment.models.Message;

public interface MessageService {

	Message sendMessage(Long senderId, Long projectId, String content) throws Exception;
	List<Message> getMessagesByProjectId(Long projectId) throws Exception;
}
