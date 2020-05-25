package com.ssafy.wiselife.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.wiselife.domain.Category;
import com.ssafy.wiselife.domain.LikeMeeting;
import com.ssafy.wiselife.domain.Meeting;
import com.ssafy.wiselife.domain.User;
import com.ssafy.wiselife.domain.UserMeeting;
import com.ssafy.wiselife.dto.MeetingDTO.CreateMeeting;
import com.ssafy.wiselife.dto.MeetingDTO.DetailMeeting;
import com.ssafy.wiselife.dto.MeetingDTO.UpdateMeeting;
import com.ssafy.wiselife.mapper.EntityMapper;
import com.ssafy.wiselife.repository.CategoryRepository;
import com.ssafy.wiselife.repository.LikeMeetingRepository;
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
	private ModelMapper modelMapper; // DTO -> Entity

	@Autowired
	private EntityMapper entityMapper;

	@Override
	public int createMeeting(long uid, CreateMeeting meeting) {
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
			
			String[] tags = meetingEntity.getTags().split(" ");
			String new_tags = "";
			for (int i = 0; i < tags.length; i++) {
				new_tags += tags[i].substring(1, tags[1].length()) + " ";
			}

			new_tags = new_tags.substring(0, new_tags.length()-1);
			meetingEntity.setTags(new_tags);
			
			meetingrepo.save(meetingEntity);

			int meeting_id = meetingrepo.findLastMeetingId();
			Meeting findMeeting = meetingrepo.findById(meeting_id).get();
			UserMeeting usermeeting = new UserMeeting();
			usermeeting.setMeeting(findMeeting);
			usermeeting.setUser(meetingEntity.getUser());
			usermeeting.setIsActive(1); //모집중
			usermeetingrepo.save(usermeeting);

			return meeting_id;
		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	public int updateMeeting(int meeting_id, long uid, UpdateMeeting meeting) {
		User user = new User();
		try {
			user = userrepo.findById(uid).get();
			meetingrepo.findByMeetingIdAndUser(meeting_id, user);
		} catch (Exception e) {
			return 0;
		}

		try {
			Category category = new Category();

			try {
				category = categoryrepo.findById(meeting.getMainCategory()).get();
			} catch (Exception e) {
				return -2;
			}

			Meeting meetingEntity = meetingrepo.findById(meeting_id).get();
			List<UserMeeting> userMeetingList = usermeetingrepo.findByMeeting(meetingEntity);
			meetingEntity.setUser(user);
			meetingEntity.setCategory(category);
			meetingEntity.setMeetingId(meeting_id);
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
			meetingrepo.save(meetingEntity);
			
			// UserMeeting테이블 isActive 값도 변경
			for (int i = 0; i < userMeetingList.size(); i++) {
				userMeetingList.get(i).setIsActive(meeting.getIsActive());
			}
			usermeetingrepo.saveAll(userMeetingList);
			
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
			// 조회수 증가
			meetingEntity = meetingrepo.findById(meeting_id).get();
			int preViewCnt = meetingEntity.getViewCnt();
			meetingEntity.setViewCnt(preViewCnt + 1);
			meetingrepo.save(meetingEntity);
			
			
			meetingEntity = meetingrepo.findById(meeting_id).get();

			DetailMeeting meeting = entityMapper.convertToDomain(meetingrepo.findById(meeting_id).get(),
					DetailMeeting.class);

			// 좋아요 확인
			user = userrepo.findById(uid).get();
			
			// 로그인 안 한 사용자거나 사용자가 작성자라면
			if (uid == 0 || user == null) {
				meeting.setIsLike(0);
				meeting.setCheckUser(1); //일반사용자
				return meeting;
			} else if(uid == meetingEntity.getUser().getUid()) {
				meeting.setIsLike(0);
				meeting.setCheckUser(0); //작성자
				return meeting;
			}

			try {
				// LikeMeeting 테이블에 저장된 사용자라면
				likemeetingrepo.findByUserAndMeeting(user, meetingEntity);
				meeting.setIsLike(1);
			} catch (Exception e) {
				meeting.setIsLike(0);
			}

			return meeting;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean checkAuthentication(long uid) {
		try {
			userrepo.findById(uid).get();
			return true;
		} catch (Exception e) {
			return false;
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

		// 없는 사용자라면 0을 리턴
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

		try {
			likeMeeting = likemeetingrepo.findByUserAndMeeting(user, meeting);
			likemeetingrepo.delete(likeMeeting);
			return 0;
		} catch (Exception e) {
			likeMeeting = new LikeMeeting();
			likeMeeting.setMeeting(meeting);
			likeMeeting.setUser(user);
			likemeetingrepo.save(likeMeeting);
			return 1;
		}
	}
}
