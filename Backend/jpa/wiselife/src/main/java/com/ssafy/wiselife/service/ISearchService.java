package com.ssafy.wiselife.service;

import java.util.List;

import com.ssafy.wiselife.dto.MeetingDTO.CardMeeting;

public interface ISearchService {

	List<CardMeeting> searchKeyword(int category_id, String keyword);
	
}
