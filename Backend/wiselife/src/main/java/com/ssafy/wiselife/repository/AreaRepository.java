package com.ssafy.wiselife.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.wiselife.domain.Area;

@Repository
public interface AreaRepository extends JpaRepository<Area, Integer>{

	public List<Area> findByfirstArea(String area);
	
	public List<Area> findAll();
}
