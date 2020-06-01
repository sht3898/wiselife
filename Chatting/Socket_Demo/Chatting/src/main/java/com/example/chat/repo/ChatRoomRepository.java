package com.example.chat.repo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.example.chat.model.ChatRoom;

//실습에서는 간단하게 만들 것이므로 채팅방 정보를 Map으로 관리하지만, 서비스에서는 DB나 다른 저장 매체에 채팅방 정보를 저장하도록 구현해야 합니다.
//그리고 ChatService는 ChatRoomRepository가 대체하므로 삭제합니다. 

@Repository
public class ChatRoomRepository {

	 private Map<String, ChatRoom> chatRoomMap;
	    @PostConstruct
	    private void init() {
	        chatRoomMap = new LinkedHashMap<>();
	    }
	    public List<ChatRoom> findAllRoom() {
	        // 채팅방 생성순서 최근 순으로 반환
	        List<ChatRoom> chatRooms = new ArrayList<>(chatRoomMap.values());
	        Collections.reverse(chatRooms);
	        return chatRooms;
	    }
	    public ChatRoom findRoomById(String id) {
	        return chatRoomMap.get(id);
	    }
	    public ChatRoom createChatRoom(String name) {
	    	ChatRoom chatRoom = ChatRoom.create(name);
	    	try {
		        chatRoomMap.put(chatRoom.getRoomId(), chatRoom);
		        return chatRoom;
				
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("message:"+e.getMessage());
				return chatRoom;
			}
	        
	    }
}
