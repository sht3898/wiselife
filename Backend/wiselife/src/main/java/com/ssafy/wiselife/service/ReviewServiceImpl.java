package com.ssafy.wiselife.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.wiselife.domain.Meeting;
import com.ssafy.wiselife.domain.Review;
import com.ssafy.wiselife.domain.User;
import com.ssafy.wiselife.domain.UserMeeting;
import com.ssafy.wiselife.dto.MeetingDTO.ShortMeeting;
import com.ssafy.wiselife.dto.ReviewDTO.DetailReview;
import com.ssafy.wiselife.dto.ReviewDTO.WriteReview;
import com.ssafy.wiselife.repository.MeetingRepository;
import com.ssafy.wiselife.repository.ReviewRepository;
import com.ssafy.wiselife.repository.UserMeetingRepository;
import com.ssafy.wiselife.repository.UserRepository;

@Service
public class ReviewServiceImpl implements IReviewService {
	@Autowired
	private ReviewRepository reviewrepo;

	@Autowired
	private UserRepository userrepo;

	@Autowired
	private UserMeetingRepository usermeetingrepo;

	@Autowired
	private MeetingRepository meetingrepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<ShortMeeting> userOfJoinMeetingList(long uid) {
		List<UserMeeting> userMeeting = null;
		User user = userrepo.findById(uid).get();
		int meeting_id = 0;
		String title = "";

		userMeeting = usermeetingrepo.findByUser(user);

		if (userMeeting.size() == 0)
			return null;

		ArrayList<ShortMeeting> resultList = new ArrayList<>();
		for (int i = 0; i < userMeeting.size(); i++) {
			ShortMeeting shortMeeting = new ShortMeeting();
			meeting_id = userMeeting.get(i).getMeeting().getMeetingId();
			title = meetingrepo.findTitleByMeetingId(meeting_id);
			shortMeeting.setMeetingId(meeting_id);
			shortMeeting.setTitle(title);
			resultList.add(shortMeeting);
		}

		return resultList;
	}

	@Override
	public int saveReview(long uid, int meeting_id, WriteReview review) {
		// 리뷰 작성가능한 사람인지 먼저 확인
		User user = userrepo.findById(uid).get();
		Meeting meeting = null;
		Review reviewEntity = null;
		UserMeeting userMeeting = null;

		try {
			meeting = meetingrepo.findById(meeting_id).get();
		} catch (Exception e) {
			return -2; // 삭제된 게시물
		}

		try {
			userMeeting = usermeetingrepo.findByUserAndMeeting(user, meeting);
			reviewEntity = reviewrepo.findByUserAndMeeting(user, meeting);

			if (userMeeting.getIsActive() == 1)
				return 1;

			if (reviewEntity != null) { // 업데이트
				reviewEntity.setContent(review.getContent());
				reviewEntity.setImageUrl(review.getImageUrl());
				reviewEntity.setScore(Double.parseDouble(String.format("%.1f", review.getScore())));
			} else { // 새로 작성
				reviewEntity = new Review();
				reviewEntity = modelMapper.map(review, Review.class);
				reviewEntity.setScore(Double.parseDouble(String.format("%.1f", review.getScore())));
				reviewEntity.setUser(user);
				reviewEntity.setMeeting(meeting);
				reviewEntity.setWriter(user.getUsername());
			}
			reviewrepo.save(reviewEntity);
			int avgScore = reviewrepo.avgScoreMeeting(meeting_id);
			meeting.setScore(avgScore);
			return userMeeting.getIsActive();
		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	public int deleteReview(int review_id, long uid) {
		Review review = null;
		
		try {
			review = reviewrepo.findById(review_id).get();
		} catch (Exception e) {
			return -2;
		}

		if (review.getUser().getUid() == uid) {
			reviewrepo.delete(review);
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public List<DetailReview> showMeetingOfReviewList(int meeting_id) {
//		if(!meetingrepo.existsById(meeting_id)) {
//			return null;
//		} else {
//			
//		}
		return null;
	}
}
