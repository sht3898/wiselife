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
import com.ssafy.wiselife.repository.MeetingRepository;
import com.ssafy.wiselife.repository.UserMeetingRepository;

@Service
public class MeetingServiceImpl implements IMeetingService {

	@Autowired
	private MeetingRepository meetingrepo;

	@Autowired
	private UserMeetingRepository usermeetingrepo;

	@Autowired
	private ModelMapper modelMapper; // DTO -> Entity

	@Override
	public int createMeeting(CreateMeeting meeting) {
		try {
			User user = new User();
			user.setUid(meeting.getUid());
			Category category = new Category();
			category.setCategoryId(meeting.getMainCategory());
			Meeting meetingEntity = modelMapper.map(meeting, Meeting.class);
			Date date = new Date();
			meetingEntity.setCreatedAt(date);
			meetingEntity.setUpdatedAt(date);
			meetingEntity.setNowPerson(1);
			meetingrepo.save(meetingEntity);
			return 1;
		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	public void updateMeeting(UpdateMeeting meeting) {

	}

	@Override
	public DetailMeeting detailMeeting(int meeting_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int findByLastMeetingId() {
		return meetingrepo.findMeetingId();
	}

}
