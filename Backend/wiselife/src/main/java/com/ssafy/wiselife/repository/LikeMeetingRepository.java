package com.ssafy.wiselife.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ssafy.wiselife.domain.LikeMeeting;
import com.ssafy.wiselife.domain.LikeMeetingId;
import com.ssafy.wiselife.domain.Meeting;
import com.ssafy.wiselife.domain.User;

public interface LikeMeetingRepository extends JpaRepository<LikeMeeting, LikeMeetingId>{
	public LikeMeeting findByUserAndMeeting(User user, Meeting meeting);
	
	@Query(value = "SELECT m.meeting_id FROM USER u, LIKE_MEETING l, MEETING m WHERE u.uid = l.uid AND l.meeting_id = m.meeting_id "
			+ "AND u.gender = :gender GROUP BY l.meeting_id ORDER BY COUNT(*) LIMIT 5", nativeQuery = true)
	public List<Integer> findByGender(@Param("gender") int gender);
}
