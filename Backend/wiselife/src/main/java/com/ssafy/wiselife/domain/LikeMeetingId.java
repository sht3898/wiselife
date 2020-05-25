package com.ssafy.wiselife.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeMeetingId implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long user;
	private int meeting;
}
