package com.ssafy.wiselife.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.wiselife.dto.MeetingDTO.CardMeeting;
import com.ssafy.wiselife.dto.MeetingDTO.CreateMeeting;
import com.ssafy.wiselife.dto.MeetingDTO.DetailMeeting;
import com.ssafy.wiselife.dto.MeetingDTO.UpdateMeeting;
import com.ssafy.wiselife.service.IKakaoService;
import com.ssafy.wiselife.service.IMeetingService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = ("*"), maxAge = 6000)
@RequestMapping("/api")
public class MeetingController {

	@Autowired
	private IMeetingService meetingservice;
	
	@Autowired
	private IKakaoService kakaoservice;

	@PostMapping("/meeting/create")
	@ApiOperation(value = "모임/강좌 개설하기")
	public ResponseEntity<Map<Object, String>> createMeeting(HttpServletRequest req,
			@RequestBody CreateMeeting meeting) {
		Map<Object, String> resultMap = new HashMap<>();
		HttpStatus status = null;
		
		String access_token = null;
		HashMap<String, Object> userInfo = null;
		long uid = 0;
		
		try {
			access_token = req.getHeader("access_token");
			userInfo = kakaoservice.getUserInfo(access_token);
			uid = (long) userInfo.get("id");
		} catch (Exception e) {
			status = HttpStatus.UNAUTHORIZED;
			resultMap.put(status, "로그인을 먼저 진행해주세요");
			return new ResponseEntity<>(resultMap, status);
		}
		
		int result = meetingservice.createMeeting(uid, meeting);

		// meeting_id 값을 return
		if (result > 0) {
			status = HttpStatus.OK;
			resultMap.put(result, "SUCCESS");
		} else if (result == -1) {
			status = HttpStatus.BAD_REQUEST;
			resultMap.put(status, "FAIL");
		} else if (result == -2) {
			status = HttpStatus.BAD_REQUEST;
			resultMap.put(status, "존재하지 않는 카테고리입니다");
		}

		return new ResponseEntity<>(resultMap, status);
	}

	@PutMapping("/meeting/update")
	@ApiOperation(value = "모임/강좌 수정하기")
	public ResponseEntity<Map<Object, String>> updateMeeting(@RequestParam int meeting_id, HttpServletRequest req,
			@RequestBody UpdateMeeting meeting) {
		Map<Object, String> resultMap = new HashMap<>();
		HttpStatus status = null;
		
		String access_token = null;
		HashMap<String, Object> userInfo = null;
		long uid = 0;
		
		try {
			access_token = req.getHeader("access_token");
			userInfo = kakaoservice.getUserInfo(access_token);
			uid = (long) userInfo.get("id");
		} catch (Exception e) {
			status = HttpStatus.UNAUTHORIZED;
			resultMap.put(status, "로그인을 먼저 진행해주세요");
			return new ResponseEntity<>(resultMap, status);
		}

		int result = meetingservice.updateMeeting(meeting_id, uid, meeting);

		if (result > 0) {
			status = HttpStatus.OK;
			resultMap.put(result, "SUCCESS");
		} else if (result == -1) {
			status = HttpStatus.BAD_REQUEST;
			resultMap.put(status, "FAIL");
		} else if (result == -2) {
			status = HttpStatus.BAD_REQUEST;
			resultMap.put(status, "존재하지 않는 카테고리입니다");
		} else {
			status = HttpStatus.UNAUTHORIZED;
			resultMap.put(status, "NOT PERMISSION");
		}

		return new ResponseEntity<>(resultMap, status);
	}

	@GetMapping("/meeting/detail/{meeting_id}")
	@ApiOperation(value = "모임/강좌 상세 조회")
	@ResponseBody
	public Object detailMeeting(@PathVariable int meeting_id, HttpServletRequest req) {
		Map<Object, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		
		String access_token = null;
		HashMap<String, Object> userInfo = null;
		long uid = 0;
		
		try {
			access_token = req.getHeader("access_token");
			userInfo = kakaoservice.getUserInfo(access_token);
			uid = (long) userInfo.get("id");
		} catch (Exception e) {
			status = HttpStatus.UNAUTHORIZED;
			resultMap.put(status, "로그인을 먼저 진행해주세요");
			return new ResponseEntity<>(resultMap, status);
		}

		DetailMeeting meeting = meetingservice.detailMeeting(meeting_id, uid);
		if (meeting == null) {
			status = HttpStatus.NOT_FOUND;
			resultMap.put(status, "삭제되었거나 존재하지 않는 게시물");
			return new ResponseEntity<>(resultMap, status);
		} else {
			return meeting;
		}
	}

	@DeleteMapping("/meeting/delete")
	@ApiOperation(value = "모임/강좌 삭제")
	public ResponseEntity<Map<Object, String>> deleteMeeting(@RequestParam int meeting_id, HttpServletRequest req) {
		Map<Object, String> resultMap = new HashMap<>();
		HttpStatus status = null;
		
		String access_token = null;
		HashMap<String, Object> userInfo = null;
		long uid = 0;
		
		try {
			access_token = req.getHeader("access_token");
			userInfo = kakaoservice.getUserInfo(access_token);
			uid = (long) userInfo.get("id");
		} catch (Exception e) {
			status = HttpStatus.UNAUTHORIZED;
			resultMap.put(status, "로그인을 먼저 진행해주세요");
			return new ResponseEntity<>(resultMap, status);
		}
		
		int result = meetingservice.deleteMeeting(meeting_id, uid);
		
		if(result > 0) {
			status = HttpStatus.OK;
			resultMap.put(status, "SUCCESS");
		} else if(result == -1) {
			status = HttpStatus.BAD_REQUEST;
			resultMap.put(status, "FAIL");
		} else if(result == -2) {
			status = HttpStatus.NOT_FOUND;
			resultMap.put(status, "삭제되었거나 존재하지 않는 게시물");
		} else {
			status = HttpStatus.UNAUTHORIZED;
			resultMap.put(status, "NOT PERMISSION");
		}
		
		return new ResponseEntity<>(resultMap, status);
	}
	
	@PostMapping("/meeting/like")
	@ApiOperation(value = "모임/강좌 좋아요")
	public ResponseEntity<Map<Object, String>> saveLikeMeeting(@RequestParam int meeting_id, HttpServletRequest req) {
		Map<Object, String> resultMap = new HashMap<>();
		HttpStatus status = null;
		
		String access_token = null;
		HashMap<String, Object> userInfo = null;
		long uid = 0;
		
		try {
			access_token = req.getHeader("access_token");
			userInfo = kakaoservice.getUserInfo(access_token);
			uid = (long) userInfo.get("id");
		} catch (Exception e) {
			status = HttpStatus.UNAUTHORIZED;
			resultMap.put(status, "로그인을 먼저 진행해주세요");
			return new ResponseEntity<>(resultMap, status);
		}
		
		int result = meetingservice.saveLikeMeeting(meeting_id, uid);
		
		if(result == 1) {
			status = HttpStatus.OK;
			resultMap.put(status, "좋아요 추가");
		} else if(result == 0) {
			status = HttpStatus.OK;
			resultMap.put(status, "좋아요 취소");
		} else if(result == -1) {
			status = HttpStatus.NOT_FOUND;
			resultMap.put(status, "삭제되었거나 존재하지 않는 모임/강좌");
		} else {
			status = HttpStatus.BAD_REQUEST;
			resultMap.put(status, "FAIL");
		}
		
		return new ResponseEntity<>(resultMap, status);
	}
	
	@GetMapping("/meeting/list")
	@ApiOperation(value = "사용자가 참여한 모임/강좌 목록 조회")
	@ResponseBody
	public Object userOfJoinMeetingList(HttpServletRequest req) {
		Map<Object, String> resultMap = new HashMap<>();
		HttpStatus status = null;
		
		String access_token = null;
		HashMap<String, Object> userInfo = null;
		long uid = 0;
		
		try {
			access_token = req.getHeader("access_token");
			userInfo = kakaoservice.getUserInfo(access_token);
			uid = (long) userInfo.get("id");
		} catch (Exception e) {
			status = HttpStatus.UNAUTHORIZED;
			resultMap.put(status, "로그인을 먼저 진행해주세요");
			return new ResponseEntity<>(resultMap, status);
		}
		
		List<CardMeeting> resultList = meetingservice.userOfJoinMeetingList(uid);
		status = HttpStatus.OK;
		
		if(resultList == null) {
			resultMap.put(status, "NO DATA");
			return new ResponseEntity<>(resultMap, status);
		} else {
			return resultList;
		}
	}
}
