package com.ssafy.wiselife.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.wiselife.domain.LikeMeeting;
import com.ssafy.wiselife.domain.LikeMeetingId;
import com.ssafy.wiselife.domain.Meeting;
import com.ssafy.wiselife.domain.User;

@Repository
public interface LikeMeetingRepository extends JpaRepository<LikeMeeting, LikeMeetingId>{
	public LikeMeeting findByUserAndMeeting(User user, Meeting meeting);
	
	@Query(value = "SELECT m.meeting_id FROM USER u, LIKE_MEETING l, MEETING m WHERE u.uid = l.uid AND l.meeting_id = m.meeting_id "
			+ "AND u.gender = :gender GROUP BY l.meeting_id ORDER BY COUNT(*) DESC LIMIT 5", nativeQuery = true)
	public List<Integer> findByGender(@Param("gender") int gender);
	
	@Query(value = "SELECT m.meeting_id FROM USER u, LIKE_MEETING l, MEETING m WHERE u.uid = l.uid AND l.meeting_id = m.meeting_id "
			+ "AND u.ages = :ages GROUP BY l.meeting_id ORDER BY COUNT(*) DESC LIMIT 5", nativeQuery = true)
	public List<Integer> findByAges(@Param("ages") int ages);
	
	@Query(value = "SELECT m.meeting_id FROM USER u, LIKE_MEETING l, MEETING m WHERE u.uid = l.uid AND l.meeting_id = m.meeting_id "
			+ "AND m.area1 = :area1 AND m.area2 = :area2 GROUP BY l.meeting_id ORDER BY COUNT(*) DESC LIMIT 5", nativeQuery = true)
	public List<Integer> findByArea(@Param("area1") String firstArea, @Param("area2") String secondArea);
	
	@Query(value = "SELECT m.meeting_id FROM USER u, LIKE_MEETING l, MEETING m WHERE u.uid = l.uid AND l.meeting_id = m.meeting_id "
			+ "AND m.area2 = :area2 AND (m.area1 = :area1 OR m.area1 LIKE :shortFirstArea) GROUP BY l.meeting_id ORDER BY COUNT(*) DESC LIMIT 5", nativeQuery = true)
	public List<Integer> findByAreas(@Param("area1") String firstArea, @Param("shortFirstArea") String shortFirstArea, @Param("area2") String secondArea);

}
