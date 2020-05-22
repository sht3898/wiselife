package com.ssafy.wiselife.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.wiselife.dto.MeetingDTO.CardMeeting;
import com.ssafy.wiselife.service.ISearchService;

import io.swagger.annotations.ApiOperation;


@RestController
@CrossOrigin(origins = ("*"), maxAge = 6000)
@RequestMapping("/api")
public class SearchController {
	
	@Autowired
	private ISearchService searchservice;
	
	@GetMapping("/search/{category_id}")
	@ApiOperation(value = "메인페이지에서 검색")
	@ResponseBody
	public ResponseEntity<Map<Object, String>> searchKeyword(@PathVariable int category_id, @RequestParam String keyword) {
		Map<Object, String> resultMap = new HashMap<>();
		HttpStatus status = null;
		
		List<CardMeeting> meetingList = searchservice.searchKeyword(category_id, keyword);
		resultMap.put(meetingList, "SUC");
		status = HttpStatus.OK;
		return new ResponseEntity<>(resultMap, status);
	}
}
