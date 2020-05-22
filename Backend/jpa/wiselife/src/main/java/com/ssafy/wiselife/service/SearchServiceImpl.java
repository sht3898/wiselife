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
	private CategoryRepository categoryrepo;
	
	@Autowired
	private MeetingRepository meetingrepo;
	
	@Autowired
	private EntityMapper entityMapper;

	@Override
	public List<CardMeeting> searchKeyword(int category_id, String keyword) {
		try {
			//메인페이지에서 검색
			List<String> meetingList = null;
			if(category_id == 0 || categoryrepo.findById(category_id).get() != null) {
				if(keyword.contains("#")) { //해시태그 검색
					String[] tags = keyword.split(" ");
					String keywords = "";
					for (int i = 0; i < tags.length; i++) {
						keywords += tags[i].substring(1, tags[i].length()) + " ";
					}
					
					keywords = keywords.substring(0,keyword.length()-1);
					System.out.println(keywords);
					meetingList = meetingrepo.findByTags(keyword);
					System.out.println(meetingList.toString());
					return meetingList.stream().map(e -> entityMapper.convertToDomain(e, CardMeeting.class)).collect(Collectors.toList());
				}
			}
		} catch (Exception e) {
			return null;
		}
		
		return null;
	}
	

}
