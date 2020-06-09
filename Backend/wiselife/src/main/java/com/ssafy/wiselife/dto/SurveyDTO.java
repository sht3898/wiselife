package com.ssafy.wiselife.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
//uid, username, profile_image, is_inst, gender, year, ages, area1, area2
public class SurveyDTO {
	private int survey_id;
	private int openness; //개방성
	private int conscientiousness; //성실성
	private int extraversion; //외향성
	private int agreeableness; //친화성
	private int neuroticism; //신경성
}
