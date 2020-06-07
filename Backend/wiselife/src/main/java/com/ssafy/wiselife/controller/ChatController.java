package com.ssafy.wiselife.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.ssafy.wiselife.dto.ChatMessage;
import com.ssafy.wiselife.repository.ChatRoomRepository;
import com.ssafy.wiselife.service.RedisPublisher;

import lombok.RequiredArgsConstructor;
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
