package com.ssafy.wiselife.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.wiselife.domain.Survey;

public interface SurveyRepository extends JpaRepository<Survey, Integer> {

}
