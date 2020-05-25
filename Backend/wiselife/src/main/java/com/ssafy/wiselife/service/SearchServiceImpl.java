package com.ssafy.wiselife.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.wiselife.domain.Meeting;
import com.ssafy.wiselife.dto.MeetingDTO.CardMeeting;
import com.ssafy.wiselife.mapper.EntityMapper;
import com.ssafy.wiselife.repository.CategoryRepository;
import com.ssafy.wiselife.repository.MeetingRepository;

@Service
public class SearchServiceImpl implements ISearchService {

	@Autowired
	private MeetingRepository meetingrepo;

	@Autowired
	private EntityMapper entityMapper;
	
	@Autowired
	private CategoryRepository categoryrepo;

	//키워드가 있을때
	@Override
	public List<CardMeeting> searchByKeyword(int category_id, String keyword) {
		System.out.println(keyword);
		List<Meeting> meetingList = null;
		String keywords = "";
		// 메인페이지에서 검색
		if (category_id == 0) {
			if (keyword.contains("#")) { // 해시태그 검색
				String[] tags = keyword.split(" ");
				for (int i = 0; i < tags.length; i++) {
					keywords += tags[i].substring(1, tags[i].length()) + " ";
				}
				keywords = keywords.substring(0, keywords.length() - 1);
				System.out.println(keywords);

				try {
					meetingList = meetingrepo.findByTags(keyword);
				} catch (Exception e) {
					return null;
				}
			} else {
				String[] input = keyword.split(" ");
				for (int i = 0; i < input.length; i++) {
					keywords += "+" + input[i] + "* ";
				}
				
				keywords = keywords.substring(0, keywords.length() - 1);
				System.out.println(keywords);
				
				try {
					meetingList = meetingrepo.findByAllFullText(keywords);
				} catch (Exception e2) {
					return null;
				}
			}
		}

		// 카테고리 별 검색
		else {
			if(keyword.contains("#")) {
				String[] tags = keyword.split(" ");
				for (int i = 0; i < tags.length; i++) {
					keywords += tags[i].substring(1, tags[i].length()) + " ";
				}
				keywords = keywords.substring(0, keywords.length() - 1);
				System.out.println(keywords);
				
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
				System.out.println(keywords);
				
				try {
					meetingList = meetingrepo.findByCategoryAndAllFullText(category_id, keywords);
				} catch (Exception e2) {
					return null;
				}
			}
		}
		
		return meetingList.stream().map(e -> entityMapper.convertToDomain(e, CardMeeting.class))
				.collect(Collectors.toList());
	}

	//키워드가 없을때 최신순으로 보여줌
	@Override
	public List<CardMeeting> searchByCategory(int category_id) {
		try {
			List<Meeting> meetingList = meetingrepo.findByCategoryOrderByMeetingIdDesc(categoryrepo.findById(category_id).get());
			return meetingList.stream().map(e->entityMapper.convertToDomain(e, CardMeeting.class)).collect(Collectors.toList());
		} catch (Exception e) {
			return null;
		}
	}
}
