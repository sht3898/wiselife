package com.ssafy.wiselife.service;

import com.ssafy.wiselife.dto.MeetingDTO.CreateMeeting;
import com.ssafy.wiselife.dto.MeetingDTO.DetailMeeting;
import com.ssafy.wiselife.dto.MeetingDTO.UpdateMeeting;

public interface IMeetingService {
	public int createMeeting(CreateMeeting meeting);
	
	public void updateMeeting(UpdateMeeting meeting);
	
	public DetailMeeting detailMeeting(int meeting_id);
	
	public int findByLastMeetingId();
	
}
