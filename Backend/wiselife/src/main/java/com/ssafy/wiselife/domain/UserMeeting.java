package com.ssafy.wiselife.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
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
@IdClass(UserMeetingId.class)
@Table(name = "userMeeting")
public class UserMeeting {
	@Id
	@ManyToOne
	@JoinColumn(name = "uid")
	private User user;

	@Id
	@ManyToOne
	@JoinColumn(name = "meetingId")
	private Meeting meeting;
	
	//0-마감, 1-모집중, 2-진행중 / 0 또는 2는 리뷰를 작성할 수 있음
	private int isActive;
	
	@Override
	 public String toString() {
	     return ToStringBuilder
	     .reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
