package com.ssafy.wiselife.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.wiselife.domain.Meeting;
import com.ssafy.wiselife.domain.MeetingImages;
import com.ssafy.wiselife.domain.User;
import com.ssafy.wiselife.domain.UserMeeting;
import com.ssafy.wiselife.repository.CategoryRepository;
import com.ssafy.wiselife.repository.MeetingRepository;
import com.ssafy.wiselife.repository.UserMeetingRepository;
import com.ssafy.wiselife.repository.UserRepository;

@Service
public class MeetingDataImportImpl implements IMeetingDataImport {
	@Autowired
	private MeetingRepository meetingrepo;

	@Autowired
	private UserRepository userrepo;

	@Autowired
	private CategoryRepository categoryrepo;

	@Autowired
	private UserMeetingRepository usermeetingrepo;

	public void meetingDataImport() throws Exception {
		System.out.println("------모임/강좌 데이터 insert------");
		long[] admin = { 1362679482, 1369412523, 1363992434, 1367864624, 1366098022 };

		BufferedReader br = null;
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			br = Files.newBufferedReader(Paths.get("C:\\meetings.csv"));
			Charset.forName("UTF-8");
			String line = "";
			Date day = null;
			int index = 0;
			int cnt = 0;
			while ((line = br.readLine()) != null) {
				if (cnt != 0) {
					String array[] = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
					// 관리자 배정
					array[1] = admin[index++] + "";
					if (index == 5)
						index = 0;

					// 카테고리 분류
					int main_category = findCategory(array[22]);
					if (main_category == 8)
						main_category = findCategory(array[11]);
					array[20] = main_category + "";

					Meeting meeting = new Meeting();
					int i = 0;
					meeting.setMeetingId(Integer.parseInt(array[i++]));
					User user = userrepo.findById(Long.parseLong(array[i++])).get();
					meeting.setUser(user);
					meeting.setWriter(array[i++]);
					day = transFormat.parse(array[i++]);
					meeting.setCreatedAt(day);
					day = transFormat.parse(array[i++]);
					meeting.setUpdatedAt(day);
					meeting.setIsPeriod(Integer.parseInt(array[i++]));
					day = transFormat.parse(array[i++]);
					meeting.setMeetingDate(day);
					meeting.setPeriodDate(array[i++]);
					meeting.setIsClass(Integer.parseInt(array[i++]));
					meeting.setMaxPerson(Integer.parseInt(array[i++]));
					meeting.setNowPerson(Integer.parseInt(array[i++]));
					String content = array[i++];
					meeting.setContent(content.substring(1, content.length()-1));
					meeting.setRefUrl(array[i++]);
					meeting.setAddress(array[i++]);
					meeting.setFee(Integer.parseInt(array[i++]));
					meeting.setUnit(array[i++]);
					meeting.setIsActive(Integer.parseInt(array[i++]));
					meeting.setLikeCnt(Integer.parseInt(array[i++]));
					meeting.setViewCnt(Integer.parseInt(array[i++]));
					meeting.setScore(Double.parseDouble(array[i++]));
					meeting.setCategory(categoryrepo.findById(Integer.parseInt(array[i++])).get());
					String tags = array[22].replace("[", "").replace("]", "");
					String[] new_tags = tags.split(" ");
					String save_tags = "";
					for (int j = 0; j < 5; j++) { // 최대 5개 tag만 저장
						save_tags += new_tags[j] + " ";
					}
					meeting.setTags(save_tags.substring(0, save_tags.length() - 1));
					i++;
					meeting.setTitle(array[i++]);
					meeting.setArea1(array[i++]);
					meeting.setArea2(array[i++]);
					meetingrepo.save(meeting);

					UserMeeting usermeeting = new UserMeeting();
					usermeeting.setMeeting(meeting);
					usermeeting.setUser(user);
					usermeeting.setIsActive(1);
					usermeetingrepo.save(usermeeting);
				}
				cnt++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static int findCategory(String title) {
		List<String> category = new ArrayList<>();
		category.add("레저 스포츠 축구 등산 요가 골프 수상레저");
		category.add("요리 쿠킹 베이킹");
		category.add("수공예 공방 가죽공예 목굥예 금속공예 꽃꽂이 향초 도자기 의류");
		category.add("놀이 게임 방탈출 보드게임 pc방 오락");
		category.add("문화 영화 책 공연 전시");
		category.add("예술 음악 미술 사진 영상 댄스 연주");
		category.add("축제 행사 지역행사");

		for (int i = 0; i < category.size(); i++) {
			String[] contents = category.get(i).split(" ");

			for (int j = 0; j < contents.length; j++) {
				if (title.contains(contents[j])) {
					return i + 1;
				}
			}
		}

		return 8; // 기타
	}

	@Override
	public void meetingImageDateImport() {
		System.out.println("------모임/강좌 이미지 데이터 insert------");
		BufferedReader br = null;
		Meeting meeting = null;

		try {
			br = Files.newBufferedReader(Paths.get("C:\\images.csv"));
			String line = "";
			int cnt = 0;
			while ((line = br.readLine()) != null) {
				if (cnt != 0) {
					// CSV 1행을 저장하는 리스트
					String array[] = line.split(",");
					meeting = meetingrepo.findById(Integer.parseInt(array[0])).get();
					MeetingImages meetingImages = new MeetingImages();
					meetingImages.setImageUrl(array[1]);
					meetingImages.setMeeting(meeting);
				}
				cnt++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
