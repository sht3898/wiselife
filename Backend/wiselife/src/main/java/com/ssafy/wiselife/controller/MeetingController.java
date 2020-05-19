package com.ssafy.wiselife.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.wiselife.service.IMeetingService;

@RestController
@CrossOrigin(origins = ("*"), maxAge = 6000)
@RequestMapping("/api")
public class MeetingController {
	
	@Autowired
	private IMeetingService meetingservice;
	
//	@PostMapping("/meeting/create")
//	@ApiOperation(value = "모임/강좌 개설하기")
//	public ResponseEntity<CreateMeeting> createMeeting(CreateMeeting meeting) {
//		boolean result = meetingservice.createMeeting(meeting);
//		
//		if(result)
//			return new ResponseEntity<>(HttpStatus.OK);
//		
//		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//	}
//	
//	@PutMapping("/meeting/update/{meeting_id}")
//	@ApiOperation(value = "모임/강좌 수정하기")
//	public ResponseEntity<Map<HttpStatus, String>> updateMeeting(@PathVariable int meeting_id, @RequestParam long uid, @RequestBody UpdateMeeting meeting) {
//		return null;
//	}
	
	@GetMapping(path = "/helloWorld")
	public String helloWorld() {
		return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
	}
}
