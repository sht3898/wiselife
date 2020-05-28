package com.ssafy.wiselife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.wiselife.domain.MeetingImages;

@Repository
public interface MeetingImagesRepository extends JpaRepository<MeetingImages, Integer>{

}
