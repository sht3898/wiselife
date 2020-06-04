package com.example.chat.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.example.chat.model.ChatMessage;
import com.example.chat.repo.ChatRoomRepository;
import com.example.chat.sevice.RedisPublisher;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@CrossOrigin(origins = ("*"), maxAge = 6000)
@Controller
@RequiredArgsConstructor
public class ChatController {

	private final RedisPublisher redisPublisher;
	private final ChatRoomRepository chatRoomRepository;

	@MessageMapping("/chat/message")
	public void message(ChatMessage message) {
		System.out.println("message :"+message.getMessage());
		if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
			chatRoomRepository.enterChatRoom(message.getRoomId());
			message.setMessage(message.getSender() + "님이 입장하셨습니다.");
		}
		// Websocket에 발행된 메시지를 redis로 발행한다(publish)
		redisPublisher.publish(chatRoomRepository.getTopic(message.getRoomId()), message);
	}
}
