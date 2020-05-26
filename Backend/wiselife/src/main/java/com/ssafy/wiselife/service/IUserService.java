package com.ssafy.wiselife.service;

import java.util.List;
import java.util.Map;

import com.ssafy.wiselife.dto.SurveyDTO;
import com.ssafy.wiselife.dto.UserDTO;

public interface IUserService {
	
	void signUp(List<Integer> interest_category, UserDTO user);
	
//	UserDTO signUp(UserDTO user);

	boolean uidDuplicateCheck(long uid);
	
	SurveyDTO survey(SurveyDTO survey, long uid);
	
	List<String> area(String area);

	Map<String, Object> getUserInfo(long uid);


}
