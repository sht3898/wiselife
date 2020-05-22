package com.ssafy.wiselife.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class MeetingDTO {
	
	@Getter @Setter @ToString
	public static class CreateMeeting {
		private String writer;
		private int isPeriod;
		private Date meetingDate;
		private String periodDate;
		private int isClass;
		private int maxPerson;
		private String content;
		private String refUrl;
		private String address;
		private int fee;
		private String unit;
		private int mainCategory;
		private String tags;
		private String area1;
		private String area2;
		private String phone;
	}
	
	@Getter @Setter
	public static class UpdateMeeting {
		private int isPeriod;
		private Date meetingDate;
		private String periodDate;
		private int isClass;
		private String content;
		private String refUrl;
		private String address;
		private int fee;
		private String unit;
		private int isActive;
		private int mainCategory;
		private String tags;
		private String phone;
	}
	
	@Getter @Setter
	public static class DetailMeeting {
		private String writer;
		private Date updatedAt;
		private int isPeriod;
		private Date meetingDate;
		private String periodDate;
		private int isClass;
		private int maxPerson;
		private int nowPerson;
		private String content;
		private String refUrl;
		private String address;
		private int fee;
		private String unit;
		private int isActive;
		private int likeCnt;
		private int viewCnt;
		private int mainCategory;
		private String tags;
		private int score;
		private String phone;
	}
	
	@Getter @Setter
	public static class CardMeeting {
		private String title;
		private String tags; //태그는 3개만
		private int score;
		private int isLike; //0 : 좋아요(X), 1 : 좋아요(O)
		private String area1;
		private String area2;
	}
	
	@Getter @Setter
	public static class InsertUserMeeting {
		private long uid;
		private int meeting_id;
	}
}
