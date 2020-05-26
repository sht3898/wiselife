package com.ssafy.wiselife.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.wiselife.domain.User;
import com.ssafy.wiselife.dto.MeetingDTO.ShortMeeting;
import com.ssafy.wiselife.repository.LikeMeetingRepository;
import com.ssafy.wiselife.repository.MeetingRepository;
import com.ssafy.wiselife.repository.UserRepository;

@Service
public class TopFiveMeetingServiceImpl implements ITopFiveMeetingService {
	@Autowired
	private LikeMeetingRepository likemeetingrepo;

	@Autowired
	private MeetingRepository meetingrepo;

	@Autowired
	private UserRepository userrepo;

	@Override
	public Map<String, List<ShortMeeting>> findGenderTopLank() {
		List<Integer> topFiveMeeting = null;
		String title = "";
		int meeting_id = 0;
		ShortMeeting topMeeting = new ShortMeeting();
		Map<String, List<ShortMeeting>> resultMap = new HashMap<>();

		for (int c = 1; c <= 2; c++) {
			ArrayList<ShortMeeting> resultList = new ArrayList<>();
			topFiveMeeting = likemeetingrepo.findByGender(c); // 남자
			
			if(topFiveMeeting.size() == 0) {
				String gender = c == 1 ? "남" : "여";
				resultMap.put(gender, null);
				continue;
			}

			for (int i = 0; i < topFiveMeeting.size(); i++) {
				meeting_id = topFiveMeeting.get(i);
				title = meetingrepo.findTitleByMeetingId(meeting_id);
				topMeeting.setMeetingId(meeting_id);
				topMeeting.setTitle(title);
				resultList.add(topMeeting);
			}

			if (c == 1)
				resultMap.put("남", resultList);
			else
				resultMap.put("여", resultList);
		}

		return resultMap;
	}

	@Override
	public Map<String, List<ShortMeeting>> findAgesTopLank(long uid) {
		List<Integer> topFiveMeeting = null;
		String title = "";
		int meeting_id = 0;
		ShortMeeting topMeeting = new ShortMeeting();
		int ages = userrepo.findById(uid).get().getAges();
		Map<String, List<ShortMeeting>> resultMap = new HashMap<>();

		ArrayList<ShortMeeting> resultList = new ArrayList<>();
		topFiveMeeting = likemeetingrepo.findByAges(ages);
		
		if(topFiveMeeting.size() == 0) {
			resultMap.put(ages+"", null);
			return resultMap;
		}

		for (int i = 0; i < topFiveMeeting.size(); i++) {
			meeting_id = topFiveMeeting.get(i);
			title = meetingrepo.findTitleByMeetingId(meeting_id);
			topMeeting.setMeetingId(meeting_id);
			topMeeting.setTitle(title);
			resultList.add(topMeeting);
		}
		
		resultMap.put(ages+"", resultList);
		return resultMap;
	}

	@Override
	public Map<String, List<ShortMeeting>> findAreaTopLank(long uid) {
		List<Integer> topFiveMeeting = null;
		String title = "";
		int meeting_id = 0;
		ShortMeeting topMeeting = new ShortMeeting();
		User user = userrepo.findById(uid).get();
		String firstArea = user.getArea1();
		String secondArea = user.getArea2();
		Map<String, List<ShortMeeting>> resultMap = new HashMap<>();
		
		ArrayList<ShortMeeting> resultList = new ArrayList<>();
		topFiveMeeting = likemeetingrepo.findByArea(firstArea, secondArea);
		
		if(topFiveMeeting.size() == 0) {
			resultMap.put(firstArea+" "+secondArea, null);
			return resultMap;
		}
		
		for (int i = 0; i < topFiveMeeting.size(); i++) {
			meeting_id = topFiveMeeting.get(i);
			topMeeting.setMeetingId(meeting_id);
			title = meetingrepo.findTitleByMeetingId(meeting_id);
			topMeeting.setMeetingId(meeting_id);
			topMeeting.setTitle(title);
			resultList.add(topMeeting);
		}
		
		resultMap.put(firstArea+" "+secondArea, resultList);
		return resultMap;
	}

}
