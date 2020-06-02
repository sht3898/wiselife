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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.wiselife.dto.MeetingDTO.CardMeeting;
import com.ssafy.wiselife.service.IKakaoService;
import com.ssafy.wiselife.service.ISearchService;

import io.swagger.annotations.ApiOperation;


@RestController
@CrossOrigin(origins = ("*"), maxAge = 6000)
@RequestMapping("/api")
public class SearchController {
	
	@Autowired
	private ISearchService searchservice;
	
	@Autowired
	private IKakaoService kakaoservice;
	
	//category = 0, keyword = "" 는 빅데이터에서 처리해야함
	@GetMapping("/search/{category_id}")
	@ApiOperation(value = "검색")
	@ResponseBody
	public Object searchKeyword(HttpServletRequest req, @PathVariable int category_id, String keyword) {
		System.out.println("-----검색-----");
		List<CardMeeting> meetingList = null;
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
		
		if(keyword == null || keyword == "") {
			meetingList = searchservice.searchByCategory(category_id);
			if(meetingList==null) {
				resultMap.put(HttpStatus.OK, "NO DATA");
				return resultMap;
			}
			 
			return meetingList;
		}
		
		meetingList = searchservice.searchByKeyword(category_id, keyword);
		if(meetingList==null) {
			resultMap.put(HttpStatus.OK, "일치하는 내용 없음");
			return resultMap;
		}
		
		return meetingList;
	}
}
