package com.ssafy.wiselife.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.wiselife.dto.MeetingDTO.ShortMeeting;
import com.ssafy.wiselife.service.IKakaoService;
import com.ssafy.wiselife.service.ITopFiveMeetingService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = ("*"), maxAge = 6000)
@RequestMapping("/api")
public class TopFiveMeetingController {
	@Autowired
	private ITopFiveMeetingService topfivemeetingservice;
	
	@Autowired
	private IKakaoService kakaoservice;
	
	@GetMapping("/toplank")
	@ApiOperation(value = "Top5 모임/강좌 전체") //return타입이 안맞아서 로그인 에러처리 안함
	public Object showTopFiveMeeting(HttpServletRequest req) {
		Map<String, Map<String, List<ShortMeeting>>> resultMap = new HashMap<>();
		Map<String, List<ShortMeeting>> resultTopMeetingList = new HashMap<>();
		
		String access_token = null;
		HashMap<String, Object> userInfo = null;
		HttpStatus status = null;
		long uid = 0;
		
		try {
			access_token = req.getHeader("access_token");
			userInfo = kakaoservice.getUserInfo(access_token);
			uid = (long) userInfo.get("id");
		} catch (Exception e) {
			status = HttpStatus.UNAUTHORIZED;
			HashMap<HttpStatus, String> resultError = new HashMap<>();
			resultError.put(status, "로그인을 먼저 진행해주세요");
			return new ResponseEntity<>(resultError, status);
		}
		
		resultTopMeetingList = topfivemeetingservice.findGenderTopRank();
		resultMap.put("성별", resultTopMeetingList);
		
		resultTopMeetingList = topfivemeetingservice.findAgesTopRank(uid);
		resultMap.put("연령", resultTopMeetingList);
		
		resultTopMeetingList = topfivemeetingservice.findAreaTopRank(uid);
		resultMap.put("지역", resultTopMeetingList);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
}
