package com.ssafy.wiselife.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InterestCategoryId implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long user;
	private int category;
	
//https://cjh5414.github.io/jpa-manytomany-relationship-with-@onetomany-and-compositekey/
	
}
