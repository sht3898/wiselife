package com.ssafy.wiselife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ssafy.wiselife.domain.Meeting;
import com.ssafy.wiselife.domain.Review;
import com.ssafy.wiselife.domain.User;

public interface ReviewRepository extends JpaRepository<Review, Integer>{
	public Review findByUserAndMeeting(User user, Meeting meeting);
	
	public Review findByUser(User user);
	
	@Query(value = "SELECT avg(r.score) FROM REVIEW r WHERE r.meeting_id = :meeting_id", nativeQuery = true)
	public int avgScoreMeeting(@Param("meeting_id") int meeting_id);
}
