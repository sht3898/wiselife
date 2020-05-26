package com.ssafy.wiselife.service;

import java.util.List;
import java.util.Map;

import com.ssafy.wiselife.dto.MeetingDTO.ShortMeeting;

public interface ITopFiveMeetingService {
	Map<String, List<ShortMeeting>> findGenderTopLank();
	
	Map<String, List<ShortMeeting>> findAgesTopLank(long uid);
	
	Map<String, List<ShortMeeting>> findAreaTopLank(long uid);
}
