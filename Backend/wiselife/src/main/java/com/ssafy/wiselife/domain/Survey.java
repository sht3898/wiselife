package com.ssafy.wiselife.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Getter;
import lombok.Setter;

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
	
	@OneToOne
	@JoinColumn(name = "uid")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;
	
	@Override
	 public String toString() {
	     return ToStringBuilder
	     .reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
