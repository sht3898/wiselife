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
import com.ssafy.wiselife.repository.UserRepository;

@Service
public class SearchServiceImpl implements ISearchService {

	@Autowired
	private MeetingRepository meetingrepo;

	@Autowired
	private EntityMapper entityMapper;

	@Autowired
	private CategoryRepository categoryrepo;

	@Autowired
	private UserRepository userrepo;

	@Autowired
	private MeetingImagesRepository meetingimagesrepo;

	@Autowired
	private LikeMeetingRepository likemeetingrepo;

	// 키워드가 있을때
	@Override
	public List<CardMeeting> searchByKeyword(int category_id, String keyword, long uid) {
		System.out.println(keyword);
		List<Meeting> meetingList = new ArrayList<>();
		String keywords = "";

		// 전체 검색
		if (category_id == 0) {
			if (keyword.contains("#")) { // 해시태그 검색
				String[] tags = keyword.split(" ");
				for (String tag : tags) {
					keywords += tag.substring(1, tag.length()) + " ";
				}

				keywords = keywords.substring(0, keywords.length() - 1);
				meetingList = meetingrepo.findByTags(keyword);
			} else { // 기본 검색
				String[] input = keyword.split(" ");
				for (String str : input) {
					keywords += "+" + str + "* ";
				}
				keywords = keywords.substring(0, keywords.length() - 1);
				meetingList = meetingrepo.findByAllFullText(keywords);
			}
		}

		// 카테고리 별 검색
		else {
			if (keyword.contains("#")) {
				String[] tags = keyword.split(" ");
				for (String tag : tags) {
					keywords += tag.substring(1, tag.length()) + " ";
				}
				keywords = keywords.substring(0, keywords.length() - 1);
				meetingList = meetingrepo.findByCategoryAndTags(category_id, keywords);
			} else {
				String[] input = keyword.split(" ");
				for (String str : input) {
					keywords += "+" + str + "* ";
				}
				keywords = keywords.substring(0, keywords.length() - 1);
				meetingList = meetingrepo.findByCategoryAndAllFullText(category_id, keywords);
			}
		}

		if (meetingList.isEmpty())
			return null;

		// 좋아요 , 사진 확인
		List<CardMeeting> resultList = meetingList.stream().map(e -> entityMapper.convertToDomain(e, CardMeeting.class))
				.collect(Collectors.toList());
		List<MeetingImages> meetingImagesList = new ArrayList<>();
		LikeMeeting likeMeeting = null;

		for (int i = 0; i < meetingList.size(); i++) {
			List<String> imgUrlList = new ArrayList<>();
			Meeting meeting = meetingList.get(i);
			likeMeeting = likemeetingrepo.findByUserAndMeeting(userrepo.findById(uid).get(), meeting);
			meetingImagesList = meetingimagesrepo.findByMeeting(meeting);

			// 좋아요
			int value = 0;
			if (likeMeeting != null)
				value = 1;
			else
				value = 0;
			resultList.get(i).setIsLike(value);
			
			// 저장된 이미지 확인
			if (!meetingImagesList.isEmpty()) {
				for (MeetingImages mi : meetingImagesList) {
					imgUrlList.add(mi.getImageUrl());
				}
			}
			resultList.get(i).setMeetingImages(imgUrlList);
		}

		return resultList;
	}

	// 키워드가 없을때 최신순으로 보여줌
	@Override
	public List<CardMeeting> searchByCategory(int category_id, long uid) {
			List<Meeting> meetingList = new ArrayList<>();
			if (category_id == 0) {
				meetingList = meetingrepo.findByOrderByMeetingIdDesc();
			} else {
				meetingList = meetingrepo.findByCategoryOrderByMeetingIdDesc(categoryrepo.findById(category_id).get());
			}
			
			if(meetingList.isEmpty())
				return null;

			// 좋아요 확인
			List<CardMeeting> resultList = meetingList.stream()
					.map(e -> entityMapper.convertToDomain(e, CardMeeting.class)).collect(Collectors.toList());
			LikeMeeting likeMeeting = null;
			List<MeetingImages> meetingImagesList = new ArrayList<>();

			for (int i = 0; i < meetingList.size(); i++) {
				List<String> imgUrlList = new ArrayList<>();
				Meeting meeting = meetingList.get(i);
				likeMeeting = likemeetingrepo.findByUserAndMeeting(userrepo.findById(uid).get(), meeting);
				meetingImagesList = meetingimagesrepo.findByMeeting(meeting);
				int value = 0;
				if (likeMeeting != null)
					value = 1;
				else
					value = 0;
				resultList.get(i).setIsLike(value);
				
				if (!meetingImagesList.isEmpty()) {
					for (MeetingImages mi : meetingImagesList) {
						imgUrlList.add(mi.getImageUrl());
					}
				}
				resultList.get(i).setMeetingImages(imgUrlList);
			}
			return resultList;
	}
}
