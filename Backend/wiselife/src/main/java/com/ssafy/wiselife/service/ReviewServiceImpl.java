package com.ssafy.wiselife.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.wiselife.domain.Meeting;
import com.ssafy.wiselife.domain.Review;
import com.ssafy.wiselife.domain.User;
import com.ssafy.wiselife.domain.UserMeeting;
import com.ssafy.wiselife.dto.MeetingDTO.CheckMeetingOfReview;
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
	
	@Autowired
	private ModelMapper modelMapper;

	@Override // Review 작성 가능한 Meeting 목록 조회
	public List<CheckMeetingOfReview> userOfJoinMeetingList(long uid) {
		List<UserMeeting> userMeeting = new ArrayList<>();
		User user = userrepo.findById(uid).get();

		userMeeting = usermeetingrepo.findByUser(user);

		ArrayList<CheckMeetingOfReview> resultList = new ArrayList<>();
		for (UserMeeting um : userMeeting) {
			if (um.getIsActive() != 1 && um.getMeeting().getUser() != user) {
				CheckMeetingOfReview meeting = modelMapper.map(um.getMeeting(), CheckMeetingOfReview.class);
				meeting.setMainCategory(um.getMeeting().getCategory().getCategoryId());
				resultList.add(meeting);
			}
		}
		return resultList;
	}

	@Override
	public int saveReview(long uid, WriteReview review) {
		// 리뷰 작성가능한 사람인지 먼저 확인
		User user = userrepo.findById(uid).get();
		Meeting meeting = null;
		Review reviewEntity = null;
		UserMeeting userMeeting = null;

		try {
			meeting = meetingrepo.findById(review.getMeetingId()).get();
			userMeeting = usermeetingrepo.findByUserAndMeeting(user, meeting);
			
			if (userMeeting == null)
				return -1;

			if (userMeeting.getIsActive() == 1)
				return 1;

			reviewEntity = reviewrepo.findByUserAndMeeting(user, meeting);

			if (reviewEntity == null) {
				reviewEntity = new Review();
				reviewEntity.setUser(user);
				reviewEntity.setMeeting(meeting);
				reviewEntity.setWriter(user.getUsername());
			}

			// 리뷰 이미지 저장 과정
			String fileUrl = "";
			if (review.getImageFile() != null) {
				try {
					fileUrl = reviewImgConversion(review.getImageFile());
					System.out.println("리뷰 이미지 파일 업로드 성공");
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
		} catch (NoSuchElementException e) {
			return -2; // 삭제된 게시물
		}
	}

	@Override
	public int deleteReview(int review_id, long uid) {
		try {
			Review review = reviewrepo.findById(review_id).get();
			if (review.getUser().getUid() == uid) {
				reviewrepo.delete(review);
				return 1;
			} else {
				return 0;
			}
		} catch (Exception e) {
			return -2;
		}
	}

	@Override
	public List<DetailReview> showMeetingOfReviewList(int meeting_id) {
		List<DetailReview> resultList = new ArrayList<>();
		if (!meetingrepo.existsById(meeting_id)) { // 존재하지 않는 미팅이면 빈값을
			System.out.println("존재하지 않는 모임/강좌입니다.");
			return null;
		} else {
			List<Review> reviewEntityList = reviewrepo.findByMeetingId(meeting_id);
			DetailReview review = null;
			Meeting meeting = null;
			User user = null;

			for (Review reviewEntity : reviewEntityList) {
				review = entityMapper.convertToDomain(reviewEntity, DetailReview.class);
				user = reviewEntity.getUser();
				review.setProfileImage(user.getProfileImage());
				review.setUsername(user.getUsername());
				meeting = reviewEntity.getMeeting();
				int value = meeting.getUser().getUid() == user.getUid() ? 0 : 1;
				review.setCheckUser(value); // 작성자
				resultList.add(review);
			}
			return resultList;
		}
	}

	// 리뷰 이미지 저장 함수
	public static String reviewImgConversion(MultipartFile files) throws IOException {
		System.out.println("-----Save Review Images-----");
		String path = "/home/ubuntu/images/review/";
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
