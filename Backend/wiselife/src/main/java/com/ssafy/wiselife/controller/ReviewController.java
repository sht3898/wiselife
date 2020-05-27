package com.ssafy.wiselife.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.wiselife.dto.MeetingDTO.ShortMeeting;
import com.ssafy.wiselife.dto.ReviewDTO.DetailReview;
import com.ssafy.wiselife.dto.ReviewDTO.WriteReview;
import com.ssafy.wiselife.service.IKakaoService;
import com.ssafy.wiselife.service.IReviewService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = ("*"), maxAge = 6000)
@RequestMapping("/api")
public class ReviewController {
	@Autowired
	private IReviewService reviewservice;

	@Autowired
	private IKakaoService kakaoservice;

	@GetMapping("/review/check")
	@ApiOperation("작성 가능한 리뷰 목록 조회") // 마이페이지에서 리뷰작성하기 버튼 누르면 요청들어옴
	@ResponseBody
	public Object showReviewList(HttpServletRequest req) {
		System.out.println("-----작성 가능한 리뷰 목록 조회-----");
		Map<HttpStatus, String> resultMap = new HashMap<>();
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

		List<ShortMeeting> resultList = reviewservice.userOfJoinMeetingList(uid);
		status = HttpStatus.OK;

		if (resultList == null) {
			resultMap.put(status, "NO DATA");
			return new ResponseEntity<>(resultMap, status);
		} else {
			return resultList;
		}
	}

	@PostMapping("/review/write")
	@ApiOperation("리뷰 작성 또는 수정")
	public ResponseEntity<Map<HttpStatus, String>> saveReview(HttpServletRequest req, WriteReview review, MultipartFile files) {
		System.out.println("-----리뷰 작성 또는 수정-----");
		Map<HttpStatus, String> resultMap = new HashMap<>();
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
		
		int result = reviewservice.saveReview(uid, review, files);

		if (result == 1) {
			status = HttpStatus.BAD_REQUEST;
			resultMap.put(status, "마감된 게시물 아님");
		} else if (result == -2) {
			status = HttpStatus.BAD_REQUEST;
			resultMap.put(status, "삭제되었거나 존재하지 않는 모임/강좌");
		} else if (result == -1) {
			status = HttpStatus.BAD_REQUEST;
			resultMap.put(status, "리뷰 작성 권한 없음");
		} else {
			status = HttpStatus.OK;
			resultMap.put(status, "SUCCESS");
		}

		return new ResponseEntity<>(resultMap, status);
	}

	@DeleteMapping("/review/delete")
	@ApiOperation(value = "리뷰 삭제")
	public ResponseEntity<Map<HttpStatus, String>> deleteReview(@RequestParam int review_id, HttpServletRequest req) {
		System.out.println("-----리뷰 삭제-----");
		Map<HttpStatus, String> resultMap = new HashMap<>();
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

		int result = reviewservice.deleteReview(review_id, uid);

		if (result == 1) {
			status = HttpStatus.OK;
			resultMap.put(status, "SUCCESS");
		} else if (result == -1) {
			status = HttpStatus.BAD_REQUEST;
			resultMap.put(status, "FAIL");
		} else if (result == -2) {
			status = HttpStatus.NOT_FOUND;
			resultMap.put(status, "존재하지 않는 리뷰입니다");
		} else {
			status = HttpStatus.UNAUTHORIZED;
			resultMap.put(status, "NOT PERMISSION");
		}

		return new ResponseEntity<>(resultMap, status);
	}

	@GetMapping("/review/list")
	@ApiOperation(value = "하나의 모임/강좌에 대한 리뷰 목록 조회")
	@ResponseBody
	public Object showMeetingOfReviewList(@RequestParam int meeting_id) {
		System.out.println("-----모임/강좌 하나에 대한 리뷰 목록 조회-----");
		Map<HttpStatus, String> resultMap = new HashMap<>();
		HttpStatus status = null;

		List<DetailReview> resultList = reviewservice.showMeetingOfReviewList(meeting_id);

		if (resultList == null) {
			status = HttpStatus.OK;
			resultMap.put(status, "NO DATA");
			return new ResponseEntity<>(resultMap, status);
		} else {
			return resultList;
		}
	}
}
