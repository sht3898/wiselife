package com.ssafy.wiselife.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.wiselife.domain.Meeting;
import com.ssafy.wiselife.domain.User;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Integer>{
	
	@Query(value = "SELECT LAST_INSERT_ID() FROM MEETING", nativeQuery = true)
	public int findLaseMeetingId();
	
	public Meeting findByMeetingIdAndUser(int meeting_id, User user);
	
	@Query(value = "SELECT address FROM MEETING m WHERE MATCH(m.tags) AGAINST(:tags)", nativeQuery = true)
	public List<String> findByTags(@Param("tags") String tags); //전체 검색일때
}
