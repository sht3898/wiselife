package com.ssafy.wiselife.service;

import com.ssafy.wiselife.dto.UserDTO;

public interface IUserService {
	
	UserDTO signUp(UserDTO user);

	boolean uidDuplicateCheck(long uid);
	
}
