package com.ssafy.wiselife.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.wiselife.model.Meeting.CreateMeeting;
import com.ssafy.wiselife.model.Meeting.InsertUserMeeting;

@Repository
public class MeetingDao {
	String ns = "wiselife.meeting.";
	
	@Autowired
	private SqlSession sqlSession;
	
	public void insertMeeting(CreateMeeting meeting) {
		sqlSession.insert(ns+"insertMeeting", meeting);
	}
	
	public void insertUserMeeting(InsertUserMeeting user_meeting) {
		sqlSession.insert(ns+"insertUserMeeting", user_meeting);
	}
	
	public int selectMeetingId(long uid) {
		return sqlSession.selectOne(ns+"select", uid);
	}
	
}
