package com.ssafy.wiselife.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
@Table(name = "meeting", indexes = {@Index(columnList = "tags"), @Index(columnList = "title"), @Index(columnList = "content")})
public class Meeting {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int meetingId;
	
	private String writer;
	
	private String title;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;
	
	private int isPeriod;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date meetingDate;
	
	private String periodDate;
	
	private int isClass;
	
	private int maxPerson;
	private int nowPerson;
	
	@Lob
	private String content;
	
	private String refUrl;
	private String address;
	private int fee;
	private String unit;
	private int isActive;
	private int likeCnt;
	private int viewCnt;
	private int score;
	private String tags;
	private String area1;
	private String area2;
	private int phone;
	
	@ManyToOne
	@JoinColumn(name = "uid")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "main_category")
	private Category category;
	
	@OneToMany(mappedBy = "meeting")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private List<UserMeeting> users = new ArrayList<>();
	
//	@OneToOne
//	@JsonIgnore
//	private MeetingImages meetingImages;
}
