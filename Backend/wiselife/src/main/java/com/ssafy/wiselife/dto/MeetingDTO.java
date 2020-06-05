package com.ssafy.wiselife.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class MeetingDTO {
	
	@Getter @Setter @ToString
	public static class CreateMeeting {
		private String writer;
		private String title;
		private int isPeriod;
		@DateTimeFormat(pattern = "yyyy-MM-dd")
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
		private List<MultipartFile> files = new ArrayList<>();
	}
	
	@Getter @Setter
	public static class UpdateMeeting {
		private int isPeriod;
		private String title;
		@DateTimeFormat(pattern = "yyyy-MM-dd")
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
		private String area1;
		private String area2;
		private List<MultipartFile> files = new ArrayList<>();;
	}
	
	@Getter @Setter @ToString
	public static class DetailMeeting {
		private int meetingId;
		private String writer;
		private String title;
		private Date updatedAt;
		private Date createdAt;
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
		private int isActive; // 0-마감, 1-모집중, 2-진행중
		private int likeCnt;
		private int viewCnt;
		private int mainCategory;
		private String tags;
		private double score;
		private String phone;
		private int isLike;
		private int checkUser; // 0-작성자, 1-일반사용자, 2-참가자
		private String area1;
		private String area2;
		private List<String> meetingImages;
	}
	
	@Getter @Setter @ToString
	public static class CardMeeting {
		private int meetingId;
		private String title;
		private String tags; //태그는 3개만
		private double score;
		private int isLike; //0 : 좋아요(X), 1 : 좋아요(O)
		private String area1;
		private String area2;
		private int likeCnt;
		private int viewCnt;
		private List<String> meetingImages;
	}
	
	
	@Getter @Setter @ToString
	public static class ShortMeeting {
		private int meetingId;
		private String title;
	}
	
	@Getter @Setter @ToString
	public static class CheckMeetingOfReview{
		private int meetingId;
		private String title;
		private String writer;
		private int isClass;
		private int isPeriod;
		private String periodDate;
		private Date meetingDate;
		private String area1;
		private String area2;
		private int mainCategory;
		
	}

}
