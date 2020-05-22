package com.ssafy.wiselife.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Setter;

import lombok.Getter;

@Entity
@Getter
@Setter
@Table(name = "survey")
public class Survey {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int surveyId;
	
	private int openness;
	private int conscientiousness ;
	private int extraversion;
	private int agreeableness;
	private int neuroticism;
	
	@ManyToOne
	@JoinColumn(name = "uid")
	private User user;
}
