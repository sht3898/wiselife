package com.ssafy.wiselife.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Setter;

import lombok.Getter;

@Entity
@Getter
@Setter
@IdClass(InterestCategoryId.class)
@Table(name = "interestCategory")
public class InterestCategory {
	@Id
	@ManyToOne
	@JoinColumn(name = "uid")
	private User user;

	@Id
	@ManyToOne
	@JoinColumn(name = "categoryId")
	private Category category;
	
	@Override
	 public String toString() {
	     return ToStringBuilder
	     .reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
