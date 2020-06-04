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
	public Map<String, List<ShortMeeting>> findGenderTopRank() {
		List<Integer> topFiveMeeting = new ArrayList<>();
		String title = "";
		Map<String, List<ShortMeeting>> resultMap = new HashMap<>();

		for (int c = 1; c <= 2; c++) {
			ArrayList<ShortMeeting> resultList = new ArrayList<>();
			topFiveMeeting = likemeetingrepo.findByGender(c); // 남자

			for (int id : topFiveMeeting) {
				ShortMeeting topMeeting = new ShortMeeting();
				title = meetingrepo.findTitleByMeetingId(id);
				topMeeting.setMeetingId(id);
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
	public List<ShortMeeting> findAgesTopRank(long uid) {
		List<Integer> topFiveMeeting = new ArrayList<>();
		String title = "";
		int ages = userrepo.findById(uid).get().getAges();

		ArrayList<ShortMeeting> resultList = new ArrayList<>();
		topFiveMeeting = likemeetingrepo.findByAges(ages);
		
		for (int id : topFiveMeeting) {
			ShortMeeting topMeeting = new ShortMeeting();
			title = meetingrepo.findTitleByMeetingId(id);
			topMeeting.setMeetingId(id);
			topMeeting.setTitle(title);
			resultList.add(topMeeting);
		}

		return resultList;
	}

	@Override
	public List<ShortMeeting> findAreaTopRank(long uid) {
		List<Integer> topFiveMeeting = new ArrayList<>();
		String title = "";
		User user = userrepo.findById(uid).get();
		String firstArea = user.getArea1();
		String secondArea = user.getArea2();

		ArrayList<ShortMeeting> resultList = new ArrayList<>();
		topFiveMeeting = likemeetingrepo.findByArea(firstArea, secondArea);

		for (int id : topFiveMeeting) {
			ShortMeeting topMeeting = new ShortMeeting();
			title = meetingrepo.findTitleByMeetingId(id);
			topMeeting.setMeetingId(id);
			topMeeting.setTitle(title);
			resultList.add(topMeeting);
		}
		
		return resultList;
	}

}
