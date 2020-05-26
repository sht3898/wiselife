package com.ssafy.wiselife.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.wiselife.domain.Survey;
import com.ssafy.wiselife.domain.User;

public interface SurveyRepository extends JpaRepository<Survey, Integer> {

	Survey findByUser(User user);

}
