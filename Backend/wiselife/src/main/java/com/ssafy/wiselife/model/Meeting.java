package com.ssafy.wiselife.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class Meeting {
	
	@Getter @Setter
	public static class CreateMeeting {
		private String writer;
		private Date created_at;
		private Date updated_at;
		private int is_period;
		private Date meeting_date;
		private String period_date;
		private int is_class;
		private int max_person;
		private int now_person;
		private String content;
		private String ref_url;
		private String address;
		private int fee;
		private String unit;
		private int main_category;
		private String tags;
		private long uid;
	}
	
	@Getter @Setter
	public static class UpdateMeeting {
		private Date updated_at;
		private int is_period;
		private Date meeting_date;
		private String period_date;
		private int is_class;
		private String content;
		private String ref_url;
		private String address;
		private int fee;
		private String unit;
		private int is_active;
		private int main_category;
		private String tags;
	}
	
	@Getter @Setter
	public static class DetailMeeting {
		private int meeting_id;
		private String writer;
		private Date updated_at;
		private int is_period;
		private Date meeting_date;
		private String period_date;
		private int is_class;
		private int max_person;
		private int now_person;
		private String content;
		private String ref_url;
		private String address;
		private int fee;
		private String unit;
		private int is_active;
		private int like_cnt;
		private int view_cnt;
		private int main_category;
		private String tags;
	}
	
	@Getter @Setter
	public static class InsertUserMeeting {
		private long uid;
		private int meeting_id;
	}
}
