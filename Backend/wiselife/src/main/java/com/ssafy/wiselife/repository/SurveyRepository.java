package com.ssafy.wiselife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.wiselife.domain.Survey;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Integer> {

}
