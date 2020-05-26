package com.ssafy.wiselife.service;

import java.util.List;

import com.ssafy.wiselife.dto.MeetingDTO.ShortMeeting;
import com.ssafy.wiselife.dto.ReviewDTO.DetailReview;
import com.ssafy.wiselife.dto.ReviewDTO.WriteReview;

public interface IReviewService {
	List<ShortMeeting> userOfJoinMeetingList(long uid);
	
	int saveReview(long uid, int meeting_id, WriteReview review);

	int deleteReview(int review_id, long uid);

	List<DetailReview> showMeetingOfReviewList(int meeting_id);
}
