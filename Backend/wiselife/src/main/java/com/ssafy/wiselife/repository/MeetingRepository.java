package com.ssafy.wiselife.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.wiselife.domain.Category;
import com.ssafy.wiselife.domain.Meeting;
import com.ssafy.wiselife.domain.User;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Integer>{
	
	public Meeting findByMeetingIdAndUser(int meeting_id, User user);
	
	@Query(value = "SELECT * FROM MEETING m WHERE MATCH(m.tags) AGAINST(:tags in boolean mode) ORDER BY m.meeting_id DESC", nativeQuery = true)
	public List<Meeting> findByTags(@Param("tags") String tags); //전체 검색일때
	
	@Query(value = "SELECT * FROM MEETING m WHERE MATCH(m.title, m.content, "
			+ "m.tags, m.area1, m.area2) AGAINST(:keywords in boolean mode) ORDER BY m.meeting_id DESC", nativeQuery = true)
	public List<Meeting> findByAllFullText(@Param("keywords") String keywords);
	
	@Query(value = "SELECT * FROM MEETING m WHERE m.main_category = :category_id "
			+ "AND MATCH(m.tags) AGAINST(:tags in boolean mode) ORDER BY m.meeting_id DESC", nativeQuery = true)
	public List<Meeting> findByCategoryAndTags(@Param("category_id") int category_id, @Param("tags") String tags);
	
	@Query(value = "SELECT * FROM MEETING m WHERE m.main_category = :category_id AND "
			+ "MATCH(m.title, m.content, m.tags, m.area1, m.area2) AGAINST(:keywords in boolean mode) ORDER BY m.meeting_id DESC", nativeQuery = true)
	public List<Meeting> findByCategoryAndAllFullText(@Param("category_id") int category_id, @Param("keywords") String keywords);

	public List<Meeting> findByCategoryOrderByMeetingIdDesc(Category category);
	
	public List<Meeting> findByOrderByMeetingIdDesc();
	
	@Query(value = "SELECT title FROM MEETING m WHERE m.meeting_id = :meeting_id", nativeQuery = true)
	public String findTitleByMeetingId(@Param("meeting_id") int meeting_id);
}
