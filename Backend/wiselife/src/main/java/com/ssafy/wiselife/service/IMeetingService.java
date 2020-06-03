package com.ssafy.wiselife.service;

import java.util.List;
import java.util.Map;

import com.ssafy.wiselife.dto.MeetingDTO.CreateMeeting;
import com.ssafy.wiselife.dto.MeetingDTO.DetailMeeting;
import com.ssafy.wiselife.dto.MeetingDTO.UpdateMeeting;
import com.ssafy.wiselife.dto.UserDTO.MeetingOfJoinAttendant;

public interface IMeetingService {
	int createMeeting(long uid, CreateMeeting meeting);
	
	int updateMeeting(int meeting_id, long uid, UpdateMeeting meeting);
	
	DetailMeeting detailMeeting(int meeting_id, long uid);
	
	int deleteMeeting(int meeting_id, long uid);

	int saveLikeMeeting(int meeting_id, long uid);
	
	Map<String, List<DetailMeeting>> userOfJoinMeetingList(long uid);

	List<MeetingOfJoinAttendant> getMeetingOfAttendantList(int meeting_id);
	
	int joinMeeting(long uid, int meeting_id);

	int putMeetingOfUpdateIsActive(long uid, int meeting_id, int isActive);
}
