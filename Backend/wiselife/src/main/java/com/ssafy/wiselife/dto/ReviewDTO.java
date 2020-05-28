package com.ssafy.wiselife.dto;


import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class ReviewDTO {
	
	@Getter @Setter
	public static class WriteReview {
		private int meetingId;
		private String content;
		private double score;
		private MultipartFile imageFile;
	}
	
	@Getter @Setter @ToString
	public static class DetailReview {
		private int reviewId;
		private String profileImage;
		private String username;
		private double score;
		private String imageUrl;
		private String content;
		private int checkUser; //0-작성자, 1-참여자
	}
}
