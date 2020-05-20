package com.ssafy.wiselife.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.wiselife.dao.UserDao;
import com.ssafy.wiselife.model.User;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserDao userdao;
	
	//uid, username, profile_image, is_inst, gender, year, ages, area1, area2
	@Override
	public int signUp(User user) {		
		// TODO Auto-generated method stub
		return userdao.signUp(user);
	}
}
