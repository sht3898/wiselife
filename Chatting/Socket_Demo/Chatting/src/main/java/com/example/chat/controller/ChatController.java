package com.example.chat.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.example.chat.model.ChatMessage;

@CrossOrigin(origins = ("*"), maxAge = 6000)
@RestController
public class ChatController {
	
	private final SimpMessageSendingOperations messagingTemplate;

	@MessageMapping("/chat/message")
	public ChatMessage message(ChatMessage message) {
		if (ChatMessage.MessageType.ENTER.equals(message.getType()))
			message.setMessage(message.getSender() + "님이 입장하셨습니다.");
		messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
		return message;
	}
}
