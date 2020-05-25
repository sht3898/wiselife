package com.ssafy.wiselife.service;

import java.util.List;
import java.util.Map;

import com.ssafy.wiselife.dto.MeetingDTO.TopMeeting;

public interface ITopLankMeetingService {
	Map<String, List<TopMeeting>> findGenderTopLank();
	
	List<TopMeeting> findAgesTopLank();
	
	List<TopMeeting> findAreaTopLank();
}
