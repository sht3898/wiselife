package com.ssafy.wiselife.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.wiselife.model.Meeting.CreateMeeting;
import com.ssafy.wiselife.model.Meeting.UpdateMeeting;
import com.ssafy.wiselife.service.IMeetingService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = ("*"), maxAge = 6000)
@RequestMapping("/api")
public class MeetingController {

	@Autowired
	private IMeetingService meetingservice;

	@PostMapping("/meeting/create")
	@ApiOperation(value = "모임/강좌 개설하기")
	public ResponseEntity<Map<Integer, String>> createMeeting(CreateMeeting meeting) {
		int result = meetingservice.createMeeting(meeting);
		Map<Integer, String> resultMap = new HashMap<Integer, String>();
		HttpStatus status = null;

		if (result > 0) {
			resultMap.put(result, "success");
			status = HttpStatus.OK;
		} else {
			resultMap.put(result, "SQL error");
			status = HttpStatus.BAD_REQUEST;
		}
		
		return new ResponseEntity<>(resultMap, status);
	}

	@PutMapping("/meeting/update/{meeting_id}")
	@ApiOperation(value = "모임/강좌 수정하기")
	public ResponseEntity<Map<HttpStatus, String>> updateMeeting(@PathVariable int meeting_id, @RequestParam long uid,
			@RequestBody UpdateMeeting meeting) {
		return null;
	}
	
	@GetMapping("/test")
	public int test(@RequestParam long uid) {
		return meetingservice.selectMeetingId(uid);
	}
	

}
