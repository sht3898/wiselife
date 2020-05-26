package com.ssafy.wiselife.service;

import java.util.List;

import com.ssafy.wiselife.dto.MeetingDTO.CardMeeting;
import com.ssafy.wiselife.dto.MeetingDTO.CreateMeeting;
import com.ssafy.wiselife.dto.MeetingDTO.DetailMeeting;
import com.ssafy.wiselife.dto.MeetingDTO.UpdateMeeting;

public interface IMeetingService {
	int createMeeting(long uid, CreateMeeting meeting);
	
	int updateMeeting(int meeting_id, long uid, UpdateMeeting meeting);
	
	DetailMeeting detailMeeting(int meeting_id, long uid);
	
	int deleteMeeting(int meeting_id, long uid);

	int saveLikeMeeting(int meeting_id, long uid);
	
	List<CardMeeting> userOfJoinMeetingList(long uid);
	
}
