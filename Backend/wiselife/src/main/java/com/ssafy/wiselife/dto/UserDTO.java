package com.ssafy.wiselife.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
//uid, username, profile_image, is_inst, gender, year, ages, area1, area2
public class UserDTO {
	private long uid;
	private String username;
	private String profileImage;
	private int isInst;
	private int gender;
	private int year;
	private int ages;
	private String area1;
	private String area2;
	
	private List<Integer> interestCategory;
	
	@Getter @Setter
	public static class UserLogin{
		private String email;
		private String password;
	}
		
}
