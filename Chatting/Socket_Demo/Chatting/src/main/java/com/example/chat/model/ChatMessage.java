package com.example.chat.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
	
	private MessageType type; // 메시지 타입
	private String roomId; // 방번호
	private String sender; // 메시지 보낸사람
	private String message; // 메시지

	public ChatMessage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ChatMessage(MessageType type, String roomId, String sender, String message) {
		super();
		this.type = type;
		this.roomId = roomId;
		this.sender = sender;
		this.message = message;
	}
	

	
	public enum MessageType {
		ENTER, TALK, JOIN
	}
	
}
