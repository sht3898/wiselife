package com.ssafy.wiselife.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "category")
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int categoryId;
	
	private String categoryName;
	
	@Lob
	private String categoryDescription;
	
	@OneToMany(mappedBy = "category")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private List<InterestCategory> interestCategoryList = new ArrayList<>();
	
	@OneToMany(mappedBy = "category")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private List<Meeting> meetingList = new ArrayList<>();
	
	@Override
	 public String toString() {
	     return ToStringBuilder
	     .reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
