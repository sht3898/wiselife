package com.ssafy.wiselife.service;

import com.ssafy.wiselife.dto.MeetingDTO.CreateMeeting;
import com.ssafy.wiselife.dto.MeetingDTO.DetailMeeting;
import com.ssafy.wiselife.dto.MeetingDTO.UpdateMeeting;

public interface IMeetingService {
	public int createMeeting(long uid, CreateMeeting meeting);
	
	public int updateMeeting(int meeting_id, long uid, UpdateMeeting meeting);
	
	public DetailMeeting detailMeeting(int meeting_id);
	
	public boolean checkAuthentication(long uid);

	public int deleteMeeting(int meeting_id, long uid);
	
}
