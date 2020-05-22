package com.ssafy.wiselife.service;

import com.ssafy.wiselife.dto.SurveyDTO;
import com.ssafy.wiselife.dto.UserDTO;

public interface IUserService {
	
	UserDTO signUp(UserDTO user);

	boolean uidDuplicateCheck(long uid);
	
	void survey(SurveyDTO survey);
	
}
