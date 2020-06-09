package com.ssafy.wiselife.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.wiselife.domain.Area;
import com.ssafy.wiselife.domain.User;
import com.ssafy.wiselife.dto.MeetingDTO.ShortMeeting;
import com.ssafy.wiselife.repository.AreaRepository;
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

	@Autowired
	private AreaRepository arearepo;

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

		List<Area> areaList = arearepo.findAll();
		List<String> originArea4 = new ArrayList<>();
		List<String> originArea5 = new ArrayList<>();

		for (Area area : areaList) {
			String tmp = area.getFirstArea();
			if (tmp.length() == 4)
				originArea4.add(tmp);
			else if (tmp.length() >= 5)
				originArea5.add(tmp);
		}

		// 지역 찾기
		String[] shortArea4 = { "충북", "충남", "전북", "전남", "경북", "경남" };
		String[] shortArea5 = { "서울", "부산", "대구", "인천", "광주", "대전", "울산", "세종", "제주" };

		ArrayList<ShortMeeting> resultList = new ArrayList<>();

		if (firstArea.length() == 3) {
			topFiveMeeting = likemeetingrepo.findByArea(firstArea, secondArea);
		} else if (firstArea.length() >= 5) {
			String shortFirstArea = "";

			for (int i = 0; i < originArea4.size(); i++) {
				if (originArea4.get(i).equals(firstArea)) {
					shortFirstArea = shortArea4[i]+"%";
				}
			}
			topFiveMeeting = likemeetingrepo.findByAreas(firstArea, shortFirstArea, secondArea);
		} else {
			String shortFirstArea = "";

			for (int i = 0; i < originArea5.size(); i++) {
				if (originArea5.get(i).equals(firstArea)) {
					shortFirstArea = shortArea5[i]+"%";
				}
			}
			topFiveMeeting = likemeetingrepo.findByAreas(firstArea, shortFirstArea, secondArea);
		}

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
