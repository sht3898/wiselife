package com.ssafy.wiselife.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.wiselife.domain.Meeting;
import com.ssafy.wiselife.domain.User;
import com.ssafy.wiselife.domain.UserMeeting;
import com.ssafy.wiselife.domain.UserMeetingId;

@Repository
public interface UserMeetingRepository extends JpaRepository<UserMeeting, UserMeetingId>{
	public List<UserMeeting> findByMeeting(Meeting meeting);
	
	public UserMeeting findByUserAndMeeting(User user, Meeting meeting);
	
	public List<UserMeeting> findByUser(User user);
}
