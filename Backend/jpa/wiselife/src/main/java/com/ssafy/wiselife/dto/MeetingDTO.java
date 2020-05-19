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
		private long uid;
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
	}
	
	@Getter @Setter
	public static class DetailMeeting {
		private int meetingId;
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
	}
	
	@Getter @Setter
	public static class InsertUserMeeting {
		private long uid;
		private int meeting_id;
	}
}
