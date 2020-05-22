package com.ssafy.wiselife.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Setter;

import lombok.Getter;

@Entity
@Getter
@Setter
@Table(name = "review")
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int reviewId;
	
	private String writer;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;
	
	private int score;
	private String imageUrl;
	
	@ManyToOne
	@JoinColumn(name = "uid")
	private User user;
	
}
