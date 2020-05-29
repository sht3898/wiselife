package com.ssafy.wiselife.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ssafy.wiselife.domain.Category;
import com.ssafy.wiselife.domain.LikeMeeting;
import com.ssafy.wiselife.domain.Meeting;
import com.ssafy.wiselife.domain.MeetingImages;
import com.ssafy.wiselife.domain.User;
import com.ssafy.wiselife.domain.UserMeeting;
import com.ssafy.wiselife.dto.MeetingDTO.CreateMeeting;
import com.ssafy.wiselife.dto.MeetingDTO.DetailMeeting;
import com.ssafy.wiselife.dto.MeetingDTO.UpdateMeeting;
import com.ssafy.wiselife.dto.UserDTO.MeetingOfJoinAttendant;
import com.ssafy.wiselife.mapper.EntityMapper;
import com.ssafy.wiselife.repository.CategoryRepository;
import com.ssafy.wiselife.repository.LikeMeetingRepository;
import com.ssafy.wiselife.repository.MeetingImagesRepository;
import com.ssafy.wiselife.repository.MeetingRepository;
import com.ssafy.wiselife.repository.UserMeetingRepository;
import com.ssafy.wiselife.repository.UserRepository;

@Service
public class MeetingServiceImpl implements IMeetingService {

	@Autowired
	private MeetingRepository meetingrepo;

	@Autowired
	private UserMeetingRepository usermeetingrepo;

	@Autowired
	private CategoryRepository categoryrepo;

	@Autowired
	private UserRepository userrepo;

	@Autowired
	private LikeMeetingRepository likemeetingrepo;

	@Autowired
	private MeetingImagesRepository meetingimagesrepo;

	@Autowired
	private ModelMapper modelMapper; // DTO -> Entity

	@Autowired
	private EntityMapper entityMapper; // Entity -> DTO

	@Override
	public int createMeeting(long uid, CreateMeeting meeting, MultipartHttpServletRequest files) {
		try {
			User user = userrepo.findById(uid).get();
			Category category = new Category();
			try {
				category = categoryrepo.findById(meeting.getMainCategory()).get();
			} catch (Exception e) {
				return -2;
			}

			Meeting meetingEntity = modelMapper.map(meeting, Meeting.class);
			meetingEntity.setNowPerson(1);
			meetingEntity.setUser(user);
			meetingEntity.setCategory(category);
			meetingEntity.setUpdatedAt(new Date());

			String[] tags = meetingEntity.getTags().split(" ");
			String new_tags = "";
			for (int i = 0; i < tags.length; i++) {
				new_tags += tags[i].substring(1, tags[1].length()) + " ";
			}

			new_tags = new_tags.substring(0, new_tags.length() - 1);
			meetingEntity.setTags(new_tags);
			meetingrepo.save(meetingEntity);
			int meeting_id = meetingrepo.findLastMeetingId();
			Meeting findMeeting = meetingrepo.findById(meeting_id).get();

			// 미팅 이미지 저장 과정
			System.out.println("미팅 이미지 파일:" + files);
			List<String> fileUrlList = null;
			if (files != null) {
				try {
					fileUrlList = meetingImgConversion(files, uid);
				} catch (Exception e) {
					System.out.println("미팅 이미지 파일 업로드 실패");
				}
			}

			for (String fileUrl : fileUrlList) {
				MeetingImages meetingImage = new MeetingImages();
				meetingImage.setImageUrl(fileUrl);
				meetingImage.setMeeting(findMeeting);
				meetingimagesrepo.save(meetingImage);
			}

			// 모임/강좌 참여자 테이블에 작성자 저장
			UserMeeting usermeeting = new UserMeeting();
			usermeeting.setMeeting(findMeeting);
			usermeeting.setUser(meetingEntity.getUser());
			usermeeting.setIsActive(1); // 모집중
			usermeetingrepo.save(usermeeting);

			return meeting_id;
		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	public int updateMeeting(int meeting_id, long uid, UpdateMeeting meeting) {
		User user = userrepo.findById(uid).get();
		Meeting meetingEntity = null;

		meetingEntity = meetingrepo.findById(meeting_id).get();

		if (meetingEntity == null) {
			return 0;
		}

		try {
			Category category = null;
			category = categoryrepo.findById(meeting.getMainCategory()).get();

			if (category == null) {
				return -2;
			}

			List<UserMeeting> userMeetingList = usermeetingrepo.findByMeeting(meetingEntity);
			meetingEntity.setUser(user);
			meetingEntity.setCategory(category);
			meetingEntity.setTitle(meeting.getTitle());
			meetingEntity.setIsPeriod(meeting.getIsPeriod());
			meetingEntity.setMeetingDate(meeting.getMeetingDate());
			meetingEntity.setPeriodDate(meeting.getPeriodDate());
			meetingEntity.setIsClass(meeting.getIsClass());
			meetingEntity.setContent(meeting.getContent());
			meetingEntity.setRefUrl(meeting.getRefUrl());
			meetingEntity.setAddress(meeting.getAddress());
			meetingEntity.setFee(meeting.getFee());
			meetingEntity.setUnit(meeting.getUnit());
			meetingEntity.setIsActive(meeting.getIsActive());
			meetingEntity.setTags(meeting.getTags());
			meetingEntity.setPhone(meeting.getPhone());
			meetingEntity.setUpdatedAt(new Date());
			meetingrepo.save(meetingEntity);

			// 미팅 이미지 저장 과정
//			System.out.println("미팅 이미지 파일:" + files);
//			List<String> fileUrlList = null;
//			if (files != null) {
//				try {
//					fileUrlList = meetingImgConversion(files, uid);
//				} catch (Exception e) {
//					System.out.println("미팅 이미지 파일 업로드 실패");
//				}
//			}
//
//			for (String fileUrl : fileUrlList) {
//				MeetingImages meetingImage = new MeetingImages();
//				meetingImage.setImageUrl(fileUrl);
//				meetingImage.setMeeting(meetingEntity);
//				meetingimagesrepo.save(meetingImage);
//			}

			// UserMeeting테이블 isActive 값도 변경
			if (meetingEntity.getIsActive() != meeting.getIsActive()) {
				for (int i = 0; i < userMeetingList.size(); i++) {
					userMeetingList.get(i).setIsActive(meeting.getIsActive());
				}
				usermeetingrepo.saveAll(userMeetingList);
			}
			return meeting_id;
		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	public DetailMeeting detailMeeting(int meeting_id, long uid) {
		// 리뷰도 같이 보여줘야함
		Meeting meetingEntity = null;
		User user = null;

		try {
			meetingEntity = meetingrepo.findById(meeting_id).get();
			// 조회수 증가
			int preViewCnt = meetingEntity.getViewCnt();
			meetingEntity.setViewCnt(preViewCnt + 1);
			meetingrepo.save(meetingEntity);

			DetailMeeting meeting = entityMapper.convertToDomain(meetingEntity, DetailMeeting.class);
			meeting.setMainCategory(meetingEntity.getCategory().getCategoryId());
			
			// 좋아요 확인
			user = userrepo.findById(uid).get();

			// 로그인 안 한 사용자거나 사용자가 작성자라면
			if (uid == meetingEntity.getUser().getUid()) {
				meeting.setIsLike(0);
				meeting.setCheckUser(0); // 작성자
				return meeting;
			}

			// 참가자인지 확인
			try {
				usermeetingrepo.findByUserAndMeeting(user, meetingEntity);
				meeting.setCheckUser(2); // 참가자
			} catch (Exception e) {
				meeting.setCheckUser(1);
			}

			LikeMeeting likemeeting = null;
			likemeeting = likemeetingrepo.findByUserAndMeeting(user, meetingEntity);
			if (likemeeting != null) {
				// LikeMeeting 테이블에 저장된 사용자라면
				meeting.setIsLike(1);
			} else {
				meeting.setIsLike(0);
			}
			return meeting;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public int deleteMeeting(int meeting_id, long uid) {
		Meeting meeting = new Meeting();
		try {
			meeting = meetingrepo.findById(meeting_id).get();
		} catch (Exception e) {
			return -2;
		}

		// 권한이 없는 사용자라면
		try {
			User user = userrepo.findById(uid).get();
			meeting = meetingrepo.findByMeetingIdAndUser(meeting_id, user);
		} catch (Exception e) {
			return 0;
		}

		try {
			meetingrepo.delete(meeting);
			return 1;
		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	public int saveLikeMeeting(int meeting_id, long uid) {
		LikeMeeting likeMeeting = null;
		Meeting meeting = null;
		User user = userrepo.findById(uid).get();

		try {
			meeting = meetingrepo.findById(meeting_id).get();
		} catch (Exception e) {
			return -1;
		}

		likeMeeting = likemeetingrepo.findByUserAndMeeting(user, meeting);

		if (likeMeeting != null) {
			likemeetingrepo.delete(likeMeeting);
			meeting.setLikeCnt(meeting.getLikeCnt() - 1);
			meetingrepo.save(meeting);
			return 0;
		} else {
			likeMeeting = new LikeMeeting();
			likeMeeting.setMeeting(meeting);
			likeMeeting.setUser(user);
			meeting.setLikeCnt(meeting.getLikeCnt() + 1);
			meetingrepo.save(meeting);
			likemeetingrepo.save(likeMeeting);
			return 1;
		}
	}

	@Override
	public Map<String, List<DetailMeeting>> userOfJoinMeetingList(long uid) {
		User user = userrepo.findById(uid).get();
		List<UserMeeting> userMeetingList = new ArrayList<>();
		userMeetingList = user.getUserMeetingList();

		Meeting meetingEntity = null;
		DetailMeeting meeting = null;
		LikeMeeting likeMeeting = null;
		Map<String, List<DetailMeeting>> resultMap = new HashMap<>();

		if (userMeetingList.size() != 0) {
			ArrayList<DetailMeeting> attendList = new ArrayList<>();
			ArrayList<DetailMeeting> postList = new ArrayList<>();

			for (int i = 0; i < userMeetingList.size(); i++) {
				long writer_id = userMeetingList.get(i).getMeeting().getUser().getUid();
				int meeting_id = userMeetingList.get(i).getMeeting().getMeetingId();
				meetingEntity = meetingrepo.findById(meeting_id).get();

				meeting = modelMapper.map(meetingEntity, DetailMeeting.class);
				int value = writer_id == uid ? 0 : 2;
				meeting.setCheckUser(value); // 작성자

				// 좋아요 유무
				likeMeeting = likemeetingrepo.findByUserAndMeeting(user, meetingEntity);
				if (likeMeeting == null) {
					meeting.setIsLike(0);
				} else {
					meeting.setIsLike(1);
				}

				// 참여/모집 나누기
				if (value == 0) {
					postList.add(meeting);
				} else {
					attendList.add(meeting);
				}
			}

			if (postList.size() == 0)
				postList = null;

			if (attendList.size() == 0)
				attendList = null;

			resultMap.put("등록", postList);
			resultMap.put("참여", attendList);

			return resultMap;
		} else
			return null;
	}

	@Override
	public List<MeetingOfJoinAttendant> getMeetingOfAttendantList(int meeting_id) {
		try {
			List<UserMeeting> userMeetingList = usermeetingrepo.findByMeeting(meetingrepo.findById(meeting_id).get());

			MeetingOfJoinAttendant user = null;
			List<MeetingOfJoinAttendant> userList = new ArrayList<>();
			User userEntity = null;
			Meeting meeting = null;

			for (int i = 0; i < userMeetingList.size(); i++) {
				userEntity = userMeetingList.get(i).getUser();
				user = entityMapper.convertToDomain(userEntity, MeetingOfJoinAttendant.class);
				meeting = userMeetingList.get(i).getMeeting();
				int value = meeting.getUser().getUid() == user.getUid() ? 0 : 1;
				user.setCheckUser(value);
				userList.add(user);
			}

			return userList;
		} catch (Exception e) {
			return null;
		}
	}

	// 리뷰 이미지 저장 함수
	public static List<String> meetingImgConversion(MultipartHttpServletRequest files, long uid) throws IOException {
		System.out.println("-----Save Meeting Image-----");
		List<MultipartFile> fileList = files.getFiles("file");
		String fileName = "";
		String originFileName = "";
		String url = "";
		String fileUrl = "";
		FileOutputStream fileOutputStream = null;
		List<String> resultFileList = new ArrayList<>();

		String path = "C:/Users/multicampus/Desktop/test/meeting/";

		for (MultipartFile mf : fileList) {
			byte[] imageData = mf.getBytes();
			originFileName = mf.getOriginalFilename(); // 원본 파일명

			try {
				fileName = uid + RandomStringUtils.randomAlphanumeric(32) + "." + originFileName;
				url = path + fileName;
				File newfile = new File(url);
				fileOutputStream = new FileOutputStream(newfile);
				fileOutputStream.write(imageData);
			} catch (Throwable e) {
				e.printStackTrace(System.out);
				System.out.println("-----Fail Save Meeting Image-----");
			} finally {
				fileOutputStream.close();
				fileUrl = path + fileName;
				resultFileList.add(fileUrl);
			}
		}
		return resultFileList;
	}

	@Override
	public int joinMeeting(long uid, int meeting_id) {
		Meeting meeting = null;
		User user = userrepo.findByUid(uid);

		try {
			meeting = meetingrepo.findById(meeting_id).get();
		} catch (Exception e) {
			return -1; // 존재하지 않는 Meeting
		}

		try {
			UserMeeting userMeeting = usermeetingrepo.findByUserAndMeeting(user, meeting);
			usermeetingrepo.delete(userMeeting);
			return 0; // 미팅참여취소
		} catch (Exception e) {
			if (meeting.getNowPerson() == meeting.getMaxPerson()) {
				return -2; // 모집인원 초과
			} else {
				meeting.setNowPerson(meeting.getNowPerson() + 1);
				meetingrepo.save(meeting);
			}

			UserMeeting userMeeting = new UserMeeting();
			userMeeting.setIsActive(1);
			userMeeting.setMeeting(meeting);
			userMeeting.setUser(user);
			usermeetingrepo.save(userMeeting);
			return 1;
		}
	}
}
