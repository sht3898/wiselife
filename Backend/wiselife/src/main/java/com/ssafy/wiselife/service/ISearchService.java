package com.ssafy.wiselife.service;

import java.util.List;

import com.ssafy.wiselife.dto.MeetingDTO.CardMeeting;

public interface ISearchService {

	List<CardMeeting> searchByKeyword(int category_id, String keyword, long uid);

	List<CardMeeting> searchByCategory(int category_id, long uid);
	
}
