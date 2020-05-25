package com.ssafy.wiselife.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.wiselife.domain.InterestCategory;
import com.ssafy.wiselife.domain.InterestCategoryId;

public interface InterestCategoryRepository extends JpaRepository<InterestCategory, InterestCategoryId>{

}
