package com.ssafy.wiselife.service;

import java.util.List;
import java.util.Map;

import com.ssafy.wiselife.dto.MeetingDTO.ShortMeeting;

public interface ITopFiveMeetingService {
	Map<String, List<ShortMeeting>> findGenderTopRank();
	
	List<ShortMeeting> findAgesTopRank(long uid);
	
	List<ShortMeeting> findAreaTopRank(long uid);
}
