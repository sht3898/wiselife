//package com.ssafy.wiselife.controller;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.ssafy.wiselife.service.IMeetingDataImport;
//
//import io.swagger.annotations.ApiOperation;
//
//@RestController
//@CrossOrigin(origins = ("*"), maxAge = 6000)
//@RequestMapping("/api")
//public class MeetingDataImportController {
//
//	@Autowired
//	private IMeetingDataImport meetingdataimportservice;
//
//	@PostMapping("/meetingdata")
//	@ApiOperation("모임/강좌 데이터 저장")
//	public ResponseEntity<Map<Object, Object>> insertMeetingData(String code) {
//		System.out.println("-----모임/강좌 데이터 넣기-----");
//		Map<Object, Object> resultMap = new HashMap<>();
//		HttpStatus status = null;
//
//		if (!code.equals("wiselife27085")) {
//			status = HttpStatus.UNAUTHORIZED;
//			resultMap.put(status, "NOT PERMISSION");
//			return new ResponseEntity<>(resultMap, status);
//		}
//
//		try {
//			meetingdataimportservice.meetingDataImport();
//		} catch (Exception e) {
//			System.out.println("-----모임/강좌 데이터 넣기 실패-----");
//			status = HttpStatus.BAD_REQUEST;
//			resultMap.put(status, "모임/강좌 데이터 넣기 실패");
//			return new ResponseEntity<>(resultMap, status);
//		}
//		
////		try {
////			meetingdataimportservice.meetingImageDateImport();
////		} catch (Exception e) {
////			System.out.println("-----모임/강좌 이미지 데이터 넣기 실패-----");
////			status = HttpStatus.BAD_REQUEST;
////			resultMap.put(status, "모임/강좌 이미지 데이터 넣기 실패");
////			return new ResponseEntity<>(resultMap, status);
////		}
//		
//		status = HttpStatus.OK;
//		resultMap.put(status, "SUCCESS");
//		return new ResponseEntity<>(resultMap, status);
//	}
//}
