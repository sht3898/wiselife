package com.ssafy.wiselife.service;

import java.util.List;

import com.ssafy.wiselife.dto.SurveyDTO;
import com.ssafy.wiselife.dto.UserDTO;

public interface IUserService {
	
	UserDTO signUp(UserDTO user);

	boolean uidDuplicateCheck(long uid);
	
	SurveyDTO survey(SurveyDTO survey);
	
	List<String> area(String area);

	void signUpInterestCategory(List<Integer> interest_category, UserDTO user);
}
