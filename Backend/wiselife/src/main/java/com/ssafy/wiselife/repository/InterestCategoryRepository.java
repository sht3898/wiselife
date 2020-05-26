package com.ssafy.wiselife.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.wiselife.domain.InterestCategory;
import com.ssafy.wiselife.domain.InterestCategoryId;
import com.ssafy.wiselife.domain.User;

public interface InterestCategoryRepository extends JpaRepository<InterestCategory, InterestCategoryId>{

	List<InterestCategory> findByUser(User user);

}
