package com.ssafy.wiselife.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "meetingImages")
public class MeetingImages {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int meetingImagesId;
	
	private String imageUrl;
	
	@OneToOne
	@JoinColumn(name = "meetingId")
	private Meeting meeting;
}
