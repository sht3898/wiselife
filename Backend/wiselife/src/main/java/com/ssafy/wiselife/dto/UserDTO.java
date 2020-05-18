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
	private String profile_image;
	private int is_inst;
	private int gender;
	private int year;
	private int ages;
	private int area1;
	private int area2;
}
