package com.ssafy.wiselife.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.wiselife.domain.Meeting;
import com.ssafy.wiselife.domain.Review;
import com.ssafy.wiselife.domain.User;
import com.ssafy.wiselife.domain.UserMeeting;
import com.ssafy.wiselife.dto.MeetingDTO.ShortMeeting;
import com.ssafy.wiselife.dto.ReviewDTO.DetailReview;
import com.ssafy.wiselife.dto.ReviewDTO.WriteReview;
import com.ssafy.wiselife.mapper.EntityMapper;
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
	private EntityMapper entityMapper;

	@Override
	public List<ShortMeeting> userOfJoinMeetingList(long uid) {
		List<UserMeeting> userMeeting = new ArrayList<>();
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
	public int saveReview(long uid, WriteReview review, MultipartFile files) {
		// 리뷰 작성가능한 사람인지 먼저 확인
		User user = userrepo.findById(uid).get();
		Meeting meeting = null;
		Review reviewEntity = null;
		UserMeeting userMeeting = null;
		
		try {
			meeting = meetingrepo.findById(review.getMeetingId()).get();
		} catch (Exception e) {
			return -2; // 삭제된 게시물
		}

		try {
			userMeeting = usermeetingrepo.findByUserAndMeeting(user, meeting);
			if (userMeeting.getIsActive() == 1)
				return 1;
		} catch (Exception e) {
			return -1;
		}

		try {
			reviewEntity = reviewrepo.findByUserAndMeeting(user, meeting);
		} catch (Exception e) {
			reviewEntity = new Review();
			reviewEntity.setUser(user);
			reviewEntity.setMeeting(meeting);
			reviewEntity.setWriter(user.getUsername());
		}
		
		// 리뷰 이미지 저장 과정
		String fileUrl = "";
		if(files != null) {
			try {
				fileUrl = reviewImgConversion(files);
			} catch (Exception e) {
				System.out.println("리뷰 이미지 파일 업로드 실패");
			}
		}

		// 리뷰 업데이트
		reviewEntity.setScore(Double.parseDouble(String.format("%.1f", review.getScore())));
		reviewEntity.setContent(review.getContent());
		reviewEntity.setImageUrl(fileUrl);
		reviewrepo.save(reviewEntity);
		
		// 별점 평균 저장
		int avgScore = reviewrepo.avgScoreMeeting(review.getMeetingId());
		meeting.setScore(avgScore);
		meetingrepo.save(meeting);
		
		return userMeeting.getIsActive();

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
		if (!meetingrepo.existsById(meeting_id)) {
			return null;
		} else {
			return reviewrepo.findByMeetingId(meeting_id).stream()
					.map(e -> entityMapper.convertToDomain(e, DetailReview.class)).collect(Collectors.toList());
		}
	}
	
	//리뷰 이미지 저장 함수
	public static String reviewImgConversion(MultipartFile files) throws IOException {
		System.out.println("-----Save Review Images-----");
		String path = "C:/Users/multicampus/Desktop/test/review/";
		String fileName = files.getOriginalFilename();
		byte[] imageData = files.getBytes();
		String fileUrl = "";

		FileOutputStream fileOutputStream = null;
		String url = "";
		try {
			fileName = RandomStringUtils.randomAlphanumeric(32) + "." + fileName;
			url = path + fileName;
			File newfile = new File(url);
			fileOutputStream = new FileOutputStream(newfile);
			fileOutputStream.write(imageData);
		} catch (Throwable e) {
			System.out.println("-----Fail Save Review Images-----");
			e.printStackTrace(System.out);
		} finally {
			fileOutputStream.close();
			fileUrl = path + fileName;
		}
		
		return fileUrl;
	}
}
