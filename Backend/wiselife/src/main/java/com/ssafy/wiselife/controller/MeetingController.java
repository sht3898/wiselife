package com.ssafy.wiselife.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.wiselife.dto.MeetingDTO.CreateMeeting;
import com.ssafy.wiselife.dto.MeetingDTO.DetailMeeting;
import com.ssafy.wiselife.dto.MeetingDTO.UpdateMeeting;
import com.ssafy.wiselife.dto.UserDTO.MeetingOfJoinAttendant;
import com.ssafy.wiselife.service.IKakaoService;
import com.ssafy.wiselife.service.IMeetingService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = ("*"), maxAge = 6000)
@RequestMapping("/api")

public class MeetingController {

	@Autowired
	private IMeetingService meetingservice;

	@Autowired
	private IKakaoService kakaoservice;

	@PostMapping("/meeting/create")
	@ApiOperation(value = "모임/강좌 개설하기", consumes = "multipart/form-data", produces = "application/text;charset=utf-8")
	public ResponseEntity<Map<Object, Object>> createMeeting(HttpServletRequest req,
			@ModelAttribute CreateMeeting meeting) {
		System.out.println("-----모임/강좌 개설-----");
		System.out.println(meeting.toString());
		System.out.println("오늘날짜는 : " + new Date());

		Map<Object, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		String access_token = null;
		HashMap<String, Object> userInfo = null;
		long uid = 0;

		try {
			access_token = req.getHeader("access_token");
			userInfo = kakaoservice.getUserInfo(access_token);
			uid = (long) userInfo.get("id");
		} catch (Exception e) {
			status = HttpStatus.UNAUTHORIZED;
			resultMap.put(status, "로그인을 먼저 진행해주세요");
			return new ResponseEntity<>(resultMap, status);
		}
		int result = meetingservice.createMeeting(uid, meeting);

		// meeting_id 값을 return
		if (result > 0) {
			status = HttpStatus.OK;
			resultMap.put("meeting_id", result);
		} else if (result == -1) {
			status = HttpStatus.BAD_REQUEST;
			resultMap.put(status, "FAIL");
		} else if (result == -2) {
			status = HttpStatus.BAD_REQUEST;
			resultMap.put(status, "존재하지 않는 카테고리입니다");
		}

		return new ResponseEntity<>(resultMap, status);
	}

	@PutMapping("/meeting/update")
	@ApiOperation(value = "모임/강좌 수정하기")
	public ResponseEntity<Map<Object, Object>> updateMeeting(@RequestParam String meeting_id, HttpServletRequest req,
			@ModelAttribute UpdateMeeting meeting) {
		System.out.println("-----모임/강좌 수정-----");
		Map<Object, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		String access_token = null;
		HashMap<String, Object> userInfo = null;
		long uid = 0;

		try {
			access_token = req.getHeader("access_token");
			userInfo = kakaoservice.getUserInfo(access_token);
			uid = (long) userInfo.get("id");
		} catch (Exception e) {
			status = HttpStatus.UNAUTHORIZED;
			resultMap.put(status, "로그인을 먼저 진행해주세요");
			return new ResponseEntity<>(resultMap, status);
		}

		int result = meetingservice.updateMeeting(Integer.parseInt(meeting_id), uid, meeting);

		if (result > 0) {
			status = HttpStatus.OK;
			resultMap.put("meeting_id", result);
		} else if (result == -1) {
			status = HttpStatus.BAD_REQUEST;
			resultMap.put(status, "FAIL");
		} else if (result == -2) {
			status = HttpStatus.BAD_REQUEST;
			resultMap.put(status, "존재하지 않는 카테고리입니다");
		} else {
			status = HttpStatus.UNAUTHORIZED;
			resultMap.put(status, "NOT PERMISSION");
		}

		return new ResponseEntity<>(resultMap, status);
	}

	@GetMapping("/meeting/{meeting_id}")
	@ApiOperation(value = "모임/강좌 상세 조회")
	@ResponseBody
	public Object detailMeeting(@PathVariable String meeting_id, HttpServletRequest req) {
		System.out.println("-----모임/강좌 상세 조회-----");
		Map<Object, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		String access_token = null;
		HashMap<String, Object> userInfo = null;
		long uid = 0;

		try {
			access_token = req.getHeader("access_token");
			userInfo = kakaoservice.getUserInfo(access_token);
			uid = (long) userInfo.get("id");
		} catch (Exception e) {
			status = HttpStatus.UNAUTHORIZED;
			resultMap.put(status, "로그인을 먼저 진행해주세요");
			return new ResponseEntity<>(resultMap, status);
		}

		DetailMeeting meeting = meetingservice.detailMeeting(Integer.parseInt(meeting_id), uid);

		if (meeting == null) {
			status = HttpStatus.NOT_FOUND;
			resultMap.put(status, "삭제되었거나 존재하지 않는 게시물");
			return new ResponseEntity<>(resultMap, status);
		} else {
			return meeting;
		}
	}

	@DeleteMapping("/meeting/delete")
	@ApiOperation(value = "모임/강좌 삭제")
	public ResponseEntity<Map<Object, String>> deleteMeeting(@RequestParam String meeting_id, HttpServletRequest req) {
		System.out.println("-----모임/강좌 삭제-----");
		System.out.println(meeting_id);
		Map<Object, String> resultMap = new HashMap<>();
		HttpStatus status = null;

		String access_token = null;
		HashMap<String, Object> userInfo = null;
		long uid = 0;

		try {
			access_token = req.getHeader("access_token");
			userInfo = kakaoservice.getUserInfo(access_token);
			uid = (long) userInfo.get("id");
		} catch (Exception e) {
			status = HttpStatus.UNAUTHORIZED;
			resultMap.put(status, "로그인을 먼저 진행해주세요");
			return new ResponseEntity<>(resultMap, status);
		}

		int result = meetingservice.deleteMeeting(Integer.parseInt(meeting_id), uid);

		if (result > 0) {
			status = HttpStatus.OK;
			resultMap.put(status, "SUCCESS");
		} else if (result == -1) {
			status = HttpStatus.BAD_REQUEST;
			resultMap.put(status, "FAIL");
		} else if (result == -2) {
			status = HttpStatus.NOT_FOUND;
			resultMap.put(status, "삭제되었거나 존재하지 않는 게시물");
		} else {
			status = HttpStatus.UNAUTHORIZED;
			resultMap.put(status, "NOT PERMISSION");
		}

		return new ResponseEntity<>(resultMap, status);
	}

	@PostMapping("/meeting/like")
	@ApiOperation(value = "모임/강좌 좋아요")
	public ResponseEntity<Map<Object, String>> saveLikeMeeting(@RequestParam String meeting_id,
			HttpServletRequest req) {
		System.out.println("-----모임/강좌 좋아요 요청-----");
		System.out.println("좋아요 누를 Meeting Id : " + meeting_id);
		Map<Object, String> resultMap = new HashMap<>();
		HttpStatus status = null;

		String access_token = null;
		HashMap<String, Object> userInfo = null;
		long uid = 0;

		try {
			access_token = req.getHeader("access_token");
			userInfo = kakaoservice.getUserInfo(access_token);
			uid = (long) userInfo.get("id");
		} catch (Exception e) {
			status = HttpStatus.UNAUTHORIZED;
			resultMap.put(status, "로그인을 먼저 진행해주세요");
			return new ResponseEntity<>(resultMap, status);
		}

		int result = meetingservice.saveLikeMeeting(Integer.parseInt(meeting_id), uid);

		if (result == 1) {
			status = HttpStatus.OK;
			resultMap.put(status, "좋아요 추가");
		} else if (result == 0) {
			status = HttpStatus.OK;
			resultMap.put(status, "좋아요 취소");
		} else if (result == -1) {
			status = HttpStatus.NOT_FOUND;
			resultMap.put(status, "삭제되었거나 존재하지 않는 모임/강좌");
		} else {
			status = HttpStatus.BAD_REQUEST;
			resultMap.put(status, "FAIL");
		}

		return new ResponseEntity<>(resultMap, status);
	}

	@GetMapping("/meeting/list")
	@ApiOperation(value = "사용자가 참여한 모임/강좌 목록 조회")
	@ResponseBody
	public Object userOfJoinMeetingList(HttpServletRequest req) {
		System.out.println("-----사용자가 참여한 모임/강좌 목록 조회-----");
		Map<Object, String> resultMap = new HashMap<>();
		HttpStatus status = null;

		String access_token = null;
		HashMap<String, Object> userInfo = null;
		long uid = 0;

		try {
			access_token = req.getHeader("access_token");
			userInfo = kakaoservice.getUserInfo(access_token);
			uid = (long) userInfo.get("id");
		} catch (Exception e) {
			status = HttpStatus.UNAUTHORIZED;
			resultMap.put(status, "로그인을 먼저 진행해주세요");
			return new ResponseEntity<>(resultMap, status);
		}

		Map<String, List<DetailMeeting>> result = meetingservice.userOfJoinMeetingList(uid);

		status = HttpStatus.OK;
		if (result.isEmpty()) {
			resultMap.put(status, "NO DATA");
			return new ResponseEntity<>(resultMap, status);
		} else {
			return result;
		}
	}

	@GetMapping("/meeting/{meeting_id}/attendant")
	@ApiOperation(value = "모임/강좌의 참여자 목록 조회")
	@ResponseBody
	public Object meetingOfAttendantList(@PathVariable String meeting_id, HttpServletRequest req) {
		System.out.println("-----모임/강좌의 참여자 목록 조회-----");
		Map<Object, String> resultMap = new HashMap<>();
		HttpStatus status = null;

		String access_token = null;
		HashMap<String, Object> userInfo = null;

		try {
			access_token = req.getHeader("access_token");
			userInfo = kakaoservice.getUserInfo(access_token);
		} catch (Exception e) {
			status = HttpStatus.UNAUTHORIZED;
			resultMap.put(status, "로그인을 먼저 진행해주세요");
			return new ResponseEntity<>(resultMap, status);
		}

		List<MeetingOfJoinAttendant> resultList = meetingservice
				.getMeetingOfAttendantList(Integer.parseInt(meeting_id));
		if (resultList == null) {
			status = HttpStatus.NOT_FOUND;
			resultMap.put(status, "삭제되었거나 존재하지 않는 모임/강좌");
			return new ResponseEntity<>(resultMap, status);
		} else if (resultList.isEmpty()) {
			status = HttpStatus.BAD_REQUEST;
			resultMap.put(status, "FAIL");
			return new ResponseEntity<>(resultMap, status);
		} else {
			return resultList;
		}
	}

	@PostMapping("/meeting/attend")
	@ApiOperation(value = "모임/강좌에 참여하기")
	public ResponseEntity<Map<Object, String>> joinMeeting(@RequestParam int meeting_id, HttpServletRequest req) {
		System.out.println("-----모임/강좌에 참여하기-----");
		Map<Object, String> resultMap = new HashMap<>();
		HttpStatus status = null;

		String access_token = null;
		HashMap<String, Object> userInfo = null;
		long uid = 0;

		try {
			access_token = req.getHeader("access_token");
			userInfo = kakaoservice.getUserInfo(access_token);
			uid = (long) userInfo.get("id");
		} catch (Exception e) {
			status = HttpStatus.UNAUTHORIZED;
			resultMap.put(status, "로그인을 먼저 진행해주세요");
			return new ResponseEntity<>(resultMap, status);
		}

		int result = meetingservice.joinMeeting(uid, meeting_id);

		if (result == 1) {
			status = HttpStatus.OK;
			resultMap.put(status, "참가 신청 완료");
		} else if (result == -1) {
			status = HttpStatus.NOT_FOUND;
			resultMap.put(status, "삭제되었거나 존재하지 않는 모임/강좌");
		} else if (result == -2) {
			status = HttpStatus.BAD_REQUEST;
			resultMap.put(status, "모집인원 초과");
		} else if (result == -3) {
			status = HttpStatus.BAD_REQUEST;
			resultMap.put(status, "개설자는 참가 취소 할 수 없습니다.");
		} else {
			status = HttpStatus.OK;
			resultMap.put(status, "참가 취소");
		}

		return new ResponseEntity<>(resultMap, status);
	}

	@PostMapping("/meeting/update/isActive")
	@ApiOperation(value = "모임/강좌 상태 변경")
	public ResponseEntity<Map<Object, Object>> meetingOfUpdateIsActive(@RequestParam int meeting_id,
			@RequestParam int isActive, HttpServletRequest req) {
		System.out.println("-----모임/강좌 상태 변경-----");
		Map<Object, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		String access_token = null;
		HashMap<String, Object> userInfo = null;
		long uid = 0;

		try {
			access_token = req.getHeader("access_token");
			userInfo = kakaoservice.getUserInfo(access_token);
			uid = (long) userInfo.get("id");
		} catch (Exception e) {
			status = HttpStatus.UNAUTHORIZED;
			resultMap.put(status, "로그인을 먼저 진행해주세요");
			return new ResponseEntity<>(resultMap, status);
		}

		int result = meetingservice.putMeetingOfUpdateIsActive(uid, meeting_id, isActive);

		if (result == -1) {
			status = HttpStatus.NOT_FOUND;
			resultMap.put(status, "삭제되었거나 존재하지 않는 모임/강좌");
		} else if (result == 0) {
			status = HttpStatus.UNAUTHORIZED;
			resultMap.put(status, "NOT PERMISSION");
		} else {
			status = HttpStatus.OK;
			resultMap.put("meeting_id", result);
		}

		return new ResponseEntity<>(resultMap, status);
	}
}
