package com.ssafy.wiselife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.wiselife.model.User;
import com.ssafy.wiselife.service.IUserService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = ("*"), maxAge = 6000)
@RequestMapping("/api")
@RestController
public class UserController {

	@Autowired
	private IUserService userservice;

	@PostMapping("/user/signup") 
	@ApiOperation(value = "회원가입하기")
	public void signUp(@RequestBody User user) {
		//회원가입
		userservice.signUp(user);
	}

}
