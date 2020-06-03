package com.ssafy.wiselife.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.wiselife.domain.LikeMeeting;
import com.ssafy.wiselife.domain.Meeting;
import com.ssafy.wiselife.domain.MeetingImages;
import com.ssafy.wiselife.dto.MeetingDTO.CardMeeting;
import com.ssafy.wiselife.mapper.EntityMapper;
import com.ssafy.wiselife.repository.CategoryRepository;
import com.ssafy.wiselife.repository.LikeMeetingRepository;
import com.ssafy.wiselife.repository.MeetingImagesRepository;
import com.ssafy.wiselife.repository.MeetingRepository;

@Service
public class SearchServiceImpl implements ISearchService {

	@Autowired
	private MeetingRepository meetingrepo;

	@Autowired
	private EntityMapper entityMapper;

	@Autowired
	private CategoryRepository categoryrepo;
	
	@Autowired
	private MeetingImagesRepository meetingimagesrepo;
	
	@Autowired
	private LikeMeetingRepository likemeetingrepo;

	// 키워드가 있을때
	@Override
	public List<CardMeeting> searchByKeyword(int category_id, String keyword) {
		System.out.println(keyword);
		List<Meeting> meetingList = new ArrayList<>();
		String keywords = "";
		
		// 메인페이지에서 검색
		if (category_id == 0) {
			if (keyword.contains("#")) { // 해시태그 검색
				String[] tags = keyword.split(" ");
				for (int i = 0; i < tags.length; i++) {
					keywords += tags[i].substring(1, tags[i].length()) + " ";
				}
				keywords = keywords.substring(0, keywords.length() - 1);

				meetingList = meetingrepo.findByTags(keyword);
				if (meetingList.isEmpty())
					return null;
				
			} else {
				String[] input = keyword.split(" ");
				for (int i = 0; i < input.length; i++) {
					keywords += "+" + input[i] + "* ";
				}

				keywords = keywords.substring(0, keywords.length() - 1);

				try {
					meetingList = meetingrepo.findByAllFullText(keywords);
				} catch (Exception e2) {
					return null;
				}
			}
		}

		// 카테고리 별 검색
		else {
			if (keyword.contains("#")) {
				String[] tags = keyword.split(" ");
				for (int i = 0; i < tags.length; i++) {
					keywords += tags[i].substring(1, tags[i].length()) + " ";
				}
				keywords = keywords.substring(0, keywords.length() - 1);

				try {
					meetingList = meetingrepo.findByCategoryAndTags(category_id, keywords);
				} catch (Exception e) {
					return null;
				}
			} else {
				String[] input = keyword.split(" ");
				for (int i = 0; i < input.length; i++) {
					keywords += "+" + input[i] + "* ";
				}
				keywords = keywords.substring(0, keywords.length() - 1);

				try {
					meetingList = meetingrepo.findByCategoryAndAllFullText(category_id, keywords);
				} catch (Exception e2) {
					return null;
				}
			}
		}
		
		// 좋아요 , 사진 확인
		List<CardMeeting> resultList = meetingList.stream().map(e -> entityMapper.convertToDomain(e, CardMeeting.class))
				.collect(Collectors.toList());
		LikeMeeting likeMeeting = null;
		List<MeetingImages> meetingImagesList = new ArrayList<>();
		List<String> imgUrlList = new ArrayList<>();
		
		for (int i = 0; i < meetingList.size(); i++) {
			Meeting meeting = meetingList.get(i);
			likeMeeting = likemeetingrepo.findByUserAndMeeting(meeting.getUser(), meeting);
			meetingImagesList = meetingimagesrepo.findByMeeting(meeting);
			
			int value = 0;
			if(likeMeeting != null) value = 1;
			else value = 0;
			
			resultList.get(i).setIsLike(value);
			if(!meetingImagesList.isEmpty()) {
				for (int j = 0; j < meetingImagesList.size(); j++) {
					imgUrlList.add(meetingImagesList.get(j).getImageUrl());
				}
			}
			
			resultList.get(i).setMeetingImages(imgUrlList);
		}
		
		return resultList;
	}

	// 키워드가 없을때 최신순으로 보여줌
	@Override
	public List<CardMeeting> searchByCategory(int category_id) {
		try {
			List<Meeting> meetingList = null;
			if(category_id == 0) {
				meetingList = meetingrepo.findByOrderByMeetingIdDesc();
			} else {
				meetingList = meetingrepo
						.findByCategoryOrderByMeetingIdDesc(categoryrepo.findById(category_id).get());
			}
			
			// 좋아요 확인
			List<CardMeeting> resultList = meetingList.stream().map(e -> entityMapper.convertToDomain(e, CardMeeting.class))
					.collect(Collectors.toList());
			LikeMeeting likeMeeting = null;
			for (int i = 0; i < meetingList.size(); i++) {
				Meeting meeting = meetingList.get(i);
				likeMeeting = likemeetingrepo.findByUserAndMeeting(meeting.getUser(), meeting);
				int value = 0;
				if(likeMeeting != null) value = 1;
				else value = 0;
				
				resultList.get(i).setIsLike(value);
			}
			
			return meetingList.stream().map(e -> entityMapper.convertToDomain(e, CardMeeting.class))
					.collect(Collectors.toList());
		} catch (Exception e) {
			return null;
		}
	}
}
