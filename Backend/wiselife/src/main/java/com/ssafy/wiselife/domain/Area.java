package com.ssafy.wiselife.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "area")
public class Area {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int areaId;
	
	private String firstArea;
	
	private String secondArea;
	
}
