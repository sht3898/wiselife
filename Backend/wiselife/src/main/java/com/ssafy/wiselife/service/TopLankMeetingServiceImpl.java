package com.ssafy.wiselife.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.wiselife.dto.MeetingDTO.TopMeeting;
import com.ssafy.wiselife.repository.LikeMeetingRepository;
import com.ssafy.wiselife.repository.MeetingRepository;

@Service
public class TopLankMeetingServiceImpl implements ITopLankMeetingService {
	@Autowired
	private LikeMeetingRepository likemeetingrepo;
	
	@Autowired
	private MeetingRepository meetingrepo;

	@Override
	public Map<String, List<TopMeeting>> findGenderTopLank() {
		List<Integer> topFiveMeeting = null;
		String title = "";
		int meeting_id = 0;
		TopMeeting topMeeting = new TopMeeting();	
		Map<String, List<TopMeeting>> resultMap = new HashMap<>();
		
		for (int c = 1; c <= 2; c++) {
			ArrayList<TopMeeting> resultList = new ArrayList<>();
			topFiveMeeting = likemeetingrepo.findByGender(c); //남자
			
			for (int i = 0; i < topFiveMeeting.size(); i++) {
				meeting_id = topFiveMeeting.get(i);
				title = meetingrepo.findTitleByMeetingId(meeting_id);
				System.out.println("title:"+title);
				topMeeting.setMeetingId(meeting_id);
				topMeeting.setTitle(title);
				resultList.add(topMeeting);
			}
			
			if(c == 1)
				resultMap.put("남", resultList);
			else
				resultMap.put("여", resultList);
		}
		
		return resultMap;
	}

	@Override
	public List<TopMeeting> findAgesTopLank() {
		List<Integer> topFiveMeeting = null;
		return null;
	}

	@Override
	public List<TopMeeting> findAreaTopLank() {
		return null;
	}

}
