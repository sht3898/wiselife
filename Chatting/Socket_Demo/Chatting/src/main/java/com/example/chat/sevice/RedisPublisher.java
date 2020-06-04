package com.example.chat.sevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import com.example.chat.model.ChatMessage;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RedisPublisher {
	
	@Autowired
	private final RedisTemplate<String, Object> redisTemplate;

	public void publish(ChannelTopic topic, ChatMessage message) {
		redisTemplate.convertAndSend(topic.getTopic(), message);
	}
}
