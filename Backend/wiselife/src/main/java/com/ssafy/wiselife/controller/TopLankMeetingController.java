package com.ssafy.wiselife.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.wiselife.dto.MeetingDTO.TopMeeting;
import com.ssafy.wiselife.service.ITopLankMeetingService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = ("*"), maxAge = 6000)
@RequestMapping("/api")
public class TopLankMeetingController {
	@Autowired
	private ITopLankMeetingService toplankmeetingservice;
	
	@GetMapping("/toplank")
	@ApiOperation(value = "Top5 모임/강좌 전체")
	public ResponseEntity<Map<String, Map<String, List<TopMeeting>>>> showTopFiveMeeting() {
		Map<String, Map<String, List<TopMeeting>>> resultMap = new HashMap<>();
		
		Map<String, List<TopMeeting>> resultTopMeetingList = new HashMap<>();
		resultTopMeetingList = toplankmeetingservice.findGenderTopLank();
		resultMap.put("성별", resultTopMeetingList);
		
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
	
	//ResponseEntity<Map<String, List<TopMeeting>>>
	
}
