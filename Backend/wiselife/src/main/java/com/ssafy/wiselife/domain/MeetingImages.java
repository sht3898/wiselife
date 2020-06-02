package com.ssafy.wiselife.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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
	
	@ManyToOne
	@JoinColumn(name = "meetingId")
	private Meeting meeting;
	
	@Override
	 public String toString() {
	     return ToStringBuilder
	     .reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
