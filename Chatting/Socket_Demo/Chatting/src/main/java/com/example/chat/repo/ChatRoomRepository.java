package com.example.chat.repo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Repository;

import com.example.chat.model.ChatRoom;
import com.example.chat.sevice.RedisSubscriber;

//실습에서는 간단하게 만들 것이므로 채팅방 정보를 Map으로 관리하지만, 서비스에서는 DB나 다른 저장 매체에 채팅방 정보를 저장하도록 구현해야 합니다.
//그리고 ChatService는 ChatRoomRepository가 대체하므로 삭제합니다. 

@Repository
public class ChatRoomRepository {

	private final RedisMessageListenerContainer redisMessageListener = new RedisMessageListenerContainer();
	private final RedisSubscriber redisSubscriber = new RedisSubscriber();
	// Redis
	private static final String CHAT_ROOMS = "CHAT_ROOM";
	private RedisTemplate<String, Object> redisTemplate;
	private HashOperations<String, String, ChatRoom> opsHashChatRoom;
	private Map<String, ChannelTopic> topics;

	@PostConstruct
	private void init() {
		opsHashChatRoom = redisTemplate.opsForHash();
		topics = new HashMap<>();
	}

	public List<ChatRoom> findAllRoom() {
		// 채팅방 생성순서 최근 순으로 반환
		return opsHashChatRoom.values(CHAT_ROOMS);
	}

	public ChatRoom findRoomById(String id) {
		return opsHashChatRoom.get(CHAT_ROOMS, id);
	}

	public ChatRoom createChatRoom(String name) {
		ChatRoom chatRoom = ChatRoom.create(name);
		opsHashChatRoom.put(CHAT_ROOMS, chatRoom.getRoomId(), chatRoom);
		return chatRoom;
	}

	public void enterChatRoom(String roomId) {
		// TODO Auto-generated method stub
		ChannelTopic topic = topics.get(roomId);
		if (topic == null) {
			topic = new ChannelTopic(roomId);
			redisMessageListener.addMessageListener(redisSubscriber, topic);
			topics.put(roomId, topic);
		}
	}

	public ChannelTopic getTopic(String roomId) {
		// TODO Auto-generated method stub
		 return topics.get(roomId);
	}
}
