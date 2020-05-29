package com.ssafy.wiselife.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Setter;
import lombok.ToString;
import lombok.Getter;

@Entity
@Getter
@Setter
@ToString
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
	
	@OneToOne
	@JoinColumn(name = "uid")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;
}
