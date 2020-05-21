package com.ssafy.wiselife.dto;

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
	private int area;
	
	@Getter @Setter
	public static class UserLogin{
		private String email;
		private String password;
	}

}
