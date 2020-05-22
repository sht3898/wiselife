package com.ssafy.wiselife.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "user")
public class User {
	@Id
	private long uid;
	
	private String username;
	
	private String profileImage;
	
	private int isInst;
	
	private int gender;
	
	private int year;
	
	private int ages;
	
	private String area1;
	
	private String area2;
	
	@OneToMany(mappedBy = "user")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private List<InterestCategory> userCategories = new ArrayList<>();
	
	@OneToMany(mappedBy = "user")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private List<UserMeeting> userMeetings = new ArrayList<>();
	
	@OneToMany(mappedBy = "user")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private List<Review> reviewList = new ArrayList<>();
	
//	@OneToOne
//	@JoinColumn(name="surveyId")
//	@OnDelete(action = OnDeleteAction.CASCADE)
//	@JsonIgnore
//	private Survey survey = new Survey();
	
	@OneToMany(mappedBy = "user")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private List<Meeting> meetingList = new ArrayList<>();
	
}
