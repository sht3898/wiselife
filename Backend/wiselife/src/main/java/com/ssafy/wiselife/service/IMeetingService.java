package com.ssafy.wiselife.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ssafy.wiselife.dto.MeetingDTO.CreateMeeting;
import com.ssafy.wiselife.dto.MeetingDTO.DetailMeeting;
import com.ssafy.wiselife.dto.MeetingDTO.UpdateMeeting;
import com.ssafy.wiselife.dto.UserDTO;

public interface IMeetingService {
	int createMeeting(long uid, CreateMeeting meeting, MultipartHttpServletRequest files);
	
	int updateMeeting(int meeting_id, long uid, UpdateMeeting meeting, MultipartHttpServletRequest files);
	
	DetailMeeting detailMeeting(int meeting_id, long uid);
	
	int deleteMeeting(int meeting_id, long uid);

	int saveLikeMeeting(int meeting_id, long uid);
	
	Map<String, List<DetailMeeting>> userOfJoinMeetingList(long uid);

	List<UserDTO> getMeetingOfAttendantList(int meeting_id);
	
	int joinMeeting(long uid, int meeting_id);
}
