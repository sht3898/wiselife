package com.ssafy.wiselife.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.wiselife.dao.MeetingDao;
import com.ssafy.wiselife.model.Meeting.CreateMeeting;
import com.ssafy.wiselife.model.Meeting.DetailMeeting;
import com.ssafy.wiselife.model.Meeting.UpdateMeeting;
import com.ssafy.wiselife.model.Meeting.InsertUserMeeting;

@Service
public class MeetingServiceImpl implements IMeetingService {
	
	@Autowired
	private MeetingDao meetingdao;

	@Override
	public int createMeeting(CreateMeeting meeting) {
		try {
			meetingdao.insertMeeting(meeting);
			int meeting_id = meetingdao.selectMeetingId(meeting.getUid());
			InsertUserMeeting ium = new InsertUserMeeting();
			ium.setUid(meeting.getUid());
			ium.setMeeting_id(meeting_id);
			meetingdao.insertUserMeeting(ium);
			return meeting_id;
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
	public int selectMeetingId(long uid) {
		return meetingdao.selectMeetingId(uid);
	}

}
