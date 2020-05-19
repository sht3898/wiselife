package com.ssafy.wiselife.service;

import com.ssafy.wiselife.model.Meeting.CreateMeeting;
import com.ssafy.wiselife.model.Meeting.DetailMeeting;
import com.ssafy.wiselife.model.Meeting.UpdateMeeting;

public interface IMeetingService {
	public boolean createMeeting(CreateMeeting meeting);
	
	public void updateMeeting(UpdateMeeting meeting);
	
	public DetailMeeting detailMeeting(int meeting_id);
	
}
