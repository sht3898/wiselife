package com.ssafy.wiselife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.wiselife.dto.UserDTO;
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
	public void signUp(@RequestBody UserDTO user) { //req로 받는 객체는 DTO
		//회원가입
		userservice.signUp(user);
	}

}
