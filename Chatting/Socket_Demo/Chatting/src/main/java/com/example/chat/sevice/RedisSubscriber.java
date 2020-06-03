package com.example.chat.sevice;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import com.example.chat.model.ChatMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
public class RedisSubscriber implements MessageListener {

	private final ObjectMapper objectMapper = new ObjectMapper();
	private final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
	private final SimpMessageSendingOperations messagingTemplate = null;

	@Override
	public void onMessage(Message message, byte[] pattern) {
		// TODO Auto-generated method stub
		try {
			// redis에서 발행된 데이터를 받아 deserialize
			String publishMessage = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());
			// ChatMessage 객채로 맵핑
			ChatMessage roomMessage = objectMapper.readValue(publishMessage, ChatMessage.class);
			// Websocket 구독자에게 채팅 메시지 Send
			messagingTemplate.convertAndSend("/sub/chat/room/" + roomMessage.getRoomId(), roomMessage);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
