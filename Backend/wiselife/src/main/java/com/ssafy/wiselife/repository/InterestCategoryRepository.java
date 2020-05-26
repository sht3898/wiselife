package com.ssafy.wiselife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.wiselife.domain.InterestCategory;
import com.ssafy.wiselife.domain.InterestCategoryId;

@Repository
public interface InterestCategoryRepository extends JpaRepository<InterestCategory, InterestCategoryId>{

}
