package com.ssafy.wiselife.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.wiselife.domain.Meeting;
import com.ssafy.wiselife.domain.MeetingImages;

@Repository
public interface MeetingImagesRepository extends JpaRepository<MeetingImages, Integer>{
	List<MeetingImages> findByMeeting(Meeting meeting);
}
