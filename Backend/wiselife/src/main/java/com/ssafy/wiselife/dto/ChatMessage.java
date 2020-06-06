package com.ssafy.wiselife.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {

	private String roomId; // 방번호
	private MessageType type; // 메시지 타입
	private String sender; // 메시지 보낸사람
	private String message; // 메시지
//	private long userCount; // 채팅방 인원수, 채팅방 내에서 메시지가 전달될때 인원수 갱신시 사용

	public ChatMessage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ChatMessage(MessageType type, String roomId, String sender, String message) {
		this.type = type;
		this.roomId = roomId;
		this.sender = sender;
		this.message = message;
//		this.userCount = userCount;
	}

	public enum MessageType {
		ENTER, TALK, QUIT
	}

}
