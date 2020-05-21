package com.ssafy.wiselife.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ssafy.wiselife.dto.UserDTO;
import com.ssafy.wiselife.service.IKakaoService;
import com.ssafy.wiselife.service.IUserService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = ("*"), maxAge = 6000)
@RequestMapping("/api")
@RestController
public class UserController {

	@Autowired
	private IUserService userservice;

	@Autowired
	private IKakaoService kakaoservice;

	@PostMapping("/user/login")
	@ApiOperation(value = "로그인하기")
	public ResponseEntity<Map<String, Object>> login(@RequestParam("code") String code, HttpServletResponse res) {

		String access_token = kakaoservice.getAccessToken(code);

		System.out.println("code :" + code);
		// 카카오계정으로 로그인
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = null;

		System.out.println("access_token :" + access_token);

		HashMap<String, Object> userInfo = kakaoservice.getUserInfo(access_token);

		long uid = (long) userInfo.get("id");
		String nickname = userInfo.get("nickname").toString();
//		String profile_image = userInfo.get("profile_image").toString();

		System.out.println(uid);
		System.out.println(nickname);
//		System.out.println(profile_image);
		try {
			UserDTO user = new UserDTO();
			user.setUid(uid);
			user.setUsername(nickname);
//			user.setProfileImage(profile_image);

			if (userservice.uidDuplicateCheck(uid)) {
				resultMap.put("status", true);
				status = HttpStatus.ACCEPTED;
				System.out.println("login");

			} else {
				// 카카오 계정으로 회원가입
				resultMap.put("status", false);
				resultMap.put("log", "회원가입이 필요합니다.");
				status = HttpStatus.ACCEPTED;
				System.out.println("signup");

			}
			res.setHeader("access-token", access_token);
			resultMap.put("access-token", access_token);

		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/user/signup")
	@ApiOperation(value = "회원가입하기")
	public ResponseEntity<Map<String, Object>> kakaosignup(@RequestBody UserDTO user, HttpServletRequest req) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = null;

		String accessToken = req.getHeader("access-token");
		try {
			user = userservice.signUp(user);

			resultMap.put("status", true);
			resultMap.put("info", user);
			status = HttpStatus.ACCEPTED;

		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

}
