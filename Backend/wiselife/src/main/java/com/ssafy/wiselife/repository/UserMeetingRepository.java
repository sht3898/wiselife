package com.ssafy.wiselife.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.wiselife.domain.Meeting;
import com.ssafy.wiselife.domain.UserMeeting;
import com.ssafy.wiselife.domain.UserMeetingId;

public interface UserMeetingRepository extends JpaRepository<UserMeeting, UserMeetingId>{
	public List<UserMeeting> findByMeeting(Meeting meeting);
}
