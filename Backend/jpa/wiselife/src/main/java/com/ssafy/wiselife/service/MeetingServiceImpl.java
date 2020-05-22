package com.ssafy.wiselife.service;

import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.wiselife.domain.Category;
import com.ssafy.wiselife.domain.Meeting;
import com.ssafy.wiselife.domain.User;
import com.ssafy.wiselife.domain.UserMeeting;
import com.ssafy.wiselife.dto.MeetingDTO.CreateMeeting;
import com.ssafy.wiselife.dto.MeetingDTO.DetailMeeting;
import com.ssafy.wiselife.dto.MeetingDTO.UpdateMeeting;
import com.ssafy.wiselife.mapper.EntityMapper;
import com.ssafy.wiselife.repository.CategoryRepository;
import com.ssafy.wiselife.repository.MeetingRepository;
import com.ssafy.wiselife.repository.UserMeetingRepository;
import com.ssafy.wiselife.repository.UserRepository;

@Service
public class MeetingServiceImpl implements IMeetingService {

	@Autowired
	private MeetingRepository meetingrepo;

	@Autowired
	private UserMeetingRepository usermeetingrepo;
	
	@Autowired
	private CategoryRepository categoryrepo;
	
	@Autowired
	private UserRepository userrepo;

	@Autowired
	private ModelMapper modelMapper; // DTO -> Entity
	
	@Autowired
	private EntityMapper entityMapper;
	
	@Override
	public int createMeeting(long uid, CreateMeeting meeting) {
		//meetingDate 프런트랑 얘기해보기
		try {
			User user = userrepo.findById(uid).get();
			Category category = new Category();
			try {
				category = categoryrepo.findById(meeting.getMainCategory()).get();
			} catch (Exception e) {
				return -2;
			}
			
			Meeting meetingEntity = modelMapper.map(meeting, Meeting.class);
			Date date = new Date();
			meetingEntity.setCreatedAt(date);
			meetingEntity.setUpdatedAt(date);
			meetingEntity.setNowPerson(1);
			meetingEntity.setUser(user);
			meetingEntity.setCategory(category);
			
			meetingrepo.save(meetingEntity);
			
			int meeting_id = meetingrepo.findLaseMeetingId();
			Meeting findMeeting = meetingrepo.findById(meeting_id).get();
			UserMeeting usermeeting = new UserMeeting();
			usermeeting.setMeeting(findMeeting);
			usermeeting.setUser(meetingEntity.getUser());
			usermeetingrepo.save(usermeeting);
			
			return meeting_id;
		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	public int updateMeeting(int meeting_id, long uid, UpdateMeeting meeting) {
		User user = new User();
		try {
			user = userrepo.findById(uid).get();
			meetingrepo.findByMeetingIdAndUser(meeting_id, user);
		} catch (Exception e) {
			return 0;
		}
		
		try {
			Category category = new Category();
			
			try {
				category = categoryrepo.findById(meeting.getMainCategory()).get();
			} catch (Exception e) {
				return -2;
			}
		
			Meeting meetingEntity = meetingrepo.findById(meeting_id).get();
			meetingEntity.setUpdatedAt(new Date());
			meetingEntity.setUser(user);
			meetingEntity.setCategory(category);
			meetingEntity.setMeetingId(meeting_id);
			meetingEntity.setIsPeriod(meeting.getIsPeriod());
			meetingEntity.setMeetingDate(meeting.getMeetingDate());
			meetingEntity.setPeriodDate(meeting.getPeriodDate());
			meetingEntity.setIsClass(meeting.getIsClass());
			meetingEntity.setContent(meeting.getContent());
			meetingEntity.setRefUrl(meeting.getRefUrl());
			meetingEntity.setAddress(meeting.getAddress());
			meetingEntity.setFee(meeting.getFee());
			meetingEntity.setUnit(meeting.getUnit());
			meetingEntity.setIsActive(meeting.getIsActive());
			meetingEntity.setTags(meeting.getTags());
			meetingrepo.save(meetingEntity);
			return meeting_id;
		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	public DetailMeeting detailMeeting(int meeting_id) {
		//리뷰도 같이 보여줘야함
		try {
			DetailMeeting meeting = entityMapper.convertToDomain(meetingrepo.findById(meeting_id).get(), DetailMeeting.class);
			return meeting;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean checkAuthentication(long uid) {
		try {
			userrepo.findById(uid).get();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public int deleteMeeting(int meeting_id, long uid) {
		Meeting meeting = new Meeting();
		try {
			meeting = meetingrepo.findById(meeting_id).get();
		} catch (Exception e) {
			return -2;
		}
		
		try {
			User user = userrepo.findById(uid).get();
			meetingrepo.findByMeetingIdAndUser(meeting_id, user);
		} catch (Exception e) {
			return 0;
		}

		try {
			meetingrepo.delete(meeting);
			return 1;
		} catch (Exception e) {
			return -1;
		}
	}
}
