package com.ssafy.wiselife.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public Object searchKeyword(@PathVariable int category_id, String keyword) {
		List<CardMeeting> meetingList = null;
		
		if(keyword == null || keyword == "") {
			meetingList = searchservice.searchByCategory(category_id);
		}
		
		meetingList = searchservice.searchByKeyword(category_id, keyword);
		if(meetingList.size() == 0) {
			Map<Object, Object> resultMap = new HashMap<>();
			resultMap.put(HttpStatus.OK, "일치하는 내용 없음");
			return resultMap;
		}
		
		return meetingList;
	}
}
