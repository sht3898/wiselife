package com.ssafy.wiselife.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.wiselife.domain.Area;
import com.ssafy.wiselife.dto.AreaDTO;

public interface AreaRepository extends JpaRepository<Area, Integer>{

	public List<Area> findByfirstArea(String area);
}
