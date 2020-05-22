package com.ssafy.wiselife.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.wiselife.domain.LikeMeeting;
import com.ssafy.wiselife.domain.LikeMeetingId;
import com.ssafy.wiselife.domain.Meeting;
import com.ssafy.wiselife.domain.User;

public interface LikeMeetingRepository extends JpaRepository<LikeMeeting, LikeMeetingId>{
	public LikeMeeting findByUserAndMeeting(User user, Meeting meeting);
}
