package com.ssafy.wiselife.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class ReviewDTO {
	
	@Getter @Setter @ToString
	public static class WriteReview {
		private String content;
		private String imageUrl;
		private double score;
	}
	
	@Getter @Setter @ToString
	public static class DetailReview {
		private String profileImage;
		private String username;
		private double score;
		private String imageUrl;
		private String content;
		private int checkUser; //0-작성자, 1-참여자
	}
}
