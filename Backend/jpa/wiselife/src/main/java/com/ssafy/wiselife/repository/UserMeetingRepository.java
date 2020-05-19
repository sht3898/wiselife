package com.ssafy.wiselife.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.wiselife.domain.UserMeeting;
import com.ssafy.wiselife.domain.UserMeetingId;

public interface UserMeetingRepository extends JpaRepository<UserMeeting, UserMeetingId>{
	
}
