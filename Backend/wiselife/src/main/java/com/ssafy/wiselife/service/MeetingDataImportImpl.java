package com.ssafy.wiselife.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.wiselife.domain.Meeting;
import com.ssafy.wiselife.domain.MeetingImages;
import com.ssafy.wiselife.domain.User;
import com.ssafy.wiselife.domain.UserMeeting;
import com.ssafy.wiselife.repository.CategoryRepository;
import com.ssafy.wiselife.repository.MeetingImagesRepository;
import com.ssafy.wiselife.repository.MeetingRepository;
import com.ssafy.wiselife.repository.UserMeetingRepository;
import com.ssafy.wiselife.repository.UserRepository;

@Service
public class MeetingDataImportImpl implements IMeetingDataImport {
	@Autowired
	private MeetingRepository meetingrepo;

	@Autowired
	private UserMeetingRepository usermeetingrepo;

	@Autowired
	private CategoryRepository categoryrepo;

	@Autowired
	private UserRepository userrepo;

	@Autowired
	private MeetingImagesRepository meetingimagesrepo;

	public void meetingDataImport() {
		System.out.println("------모임/강좌 데이터 insert------");
		long[] admin = { 1362679482, 1369412523, 1363992434, 1367864624, 1366098022 };

		BufferedReader br = null, brImg = null;
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date day = null;

		try {
			br = Files.newBufferedReader(Paths.get("C:\\meetings.csv"));
			brImg = Files.newBufferedReader(Paths.get("C:\\images.csv"));

			String line = "", imgLine = "";
			int index = 0;
			int cnt = 0;

			while ((line = br.readLine()) != null) {
				if(cnt > 100)
					break;
				
				imgLine = brImg.readLine();
				if (cnt != 0) {
					String array[] = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
					String imgArray[] = imgLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);

					// 관리자 배정
					array[1] = admin[index++] + "";
					if (index == 5)
						index = 0;

					// 주소 가공
					array[13] = array[13].replace("\"", "");

					// 내용 수정
					array[11] = array[11].substring(1, array[11].length());
					array[11] = array[11].substring(239, array[11].length()-1);

					// 카테고리 분류
					int main_category = findCategory(array[22]);
					if (main_category == 8)
						main_category = findCategory(array[11]);
					array[20] = main_category + "";

					// 태그 저장
					String tags = array[22];
					String[] new_tags = tags.split(" ");
					String save_tags = "";
					int len = 0;
					if (new_tags.length <= 5)
						len = new_tags.length;
					else
						len = 5;
					for (int j = 0; j < len; j++) { // 최대 5개 tag만 저장
						String tmp = new_tags[j].replace("[", "").replace("]", "").replace("(", "").replace(")", "")
								.replace(".", "").replace("-", "").replace("#", "").replace("!", "").replace("\'", "")
								.replace("_", "").replace("\"", "").replace("&", "");

						if (!tmp.isBlank())
							save_tags += tmp + " ";
					}

					array[21] = save_tags.substring(0, save_tags.length() - 1);
					array[23] = array[23].replace(",", "").replace("\"", "");
					array[24] = array[24].replace(",", "").replace("\"", "");

					Meeting meeting = new Meeting();
					int i = 1;
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
					meeting.setContent(array[i++]);
					meeting.setRefUrl(array[i++]);
					meeting.setAddress(array[i++]);
					meeting.setFee(Integer.parseInt(array[i++]));
					meeting.setUnit(array[i++]);
					meeting.setIsActive(Integer.parseInt(array[i++]));
					meeting.setLikeCnt(Integer.parseInt(array[i++]));
					meeting.setViewCnt(Integer.parseInt(array[i++]));
					meeting.setScore(Double.parseDouble(array[i++]));
					meeting.setCategory(categoryrepo.findById(Integer.parseInt(array[i++])).get());
					meeting.setTags(array[i++]);
					meeting.setTitle(array[i++]);
					meeting.setArea1(array[i++]);
					meeting.setArea2(array[i++]);
					meetingrepo.save(meeting);

					UserMeeting usermeeting = new UserMeeting();
					usermeeting.setMeeting(meeting);
					usermeeting.setUser(user);
					usermeeting.setIsActive(1);
					usermeetingrepo.save(usermeeting);

					try {
						if(!imgArray[1].equals("None") && !imgArray[1].equals(" ") && imgArray[1] != null
								&& !imgArray[1].isBlank()) {
							imgArray[1] = imgArray[1].substring(1, imgArray[1].length()-1);
							MeetingImages meetingimages = new MeetingImages();
							meetingimages.setMeeting(meeting);
							meetingimages.setImageUrl(imgArray[1]);
							meetingimagesrepo.save(meetingimages);
						}
					} catch (Exception e) {
						System.out.println("미팅이미지 저장 실패");
					}
				}
				cnt++;
			}
		} catch (Exception e) {
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
	
	public static String patternMatches(String content) {
		String[] tmp = content.split(" ");
		String result = "";
		
		if (Pattern.matches("[!]{2,}", content) || Pattern.matches("[.]{2,}", content)) {
			System.out.println(content);
			for (int i = 0; i < tmp.length; i++) {
				if (Pattern.matches("[!]{2,}", tmp[i]) || Pattern.matches("[.]{2,}", tmp[i])) { // !가 한 번 보다 많으면 true를 출력
					tmp[i] += "<br>";
				}
				result += tmp[i] + " ";
			}
		} else {
			for (int i = 0; i < tmp.length; i++) {
				char[] t = tmp[i].toCharArray();
				if(t[t.length-1] == '.' || t[t.length-1] == '!' || t[0] == 'ㅡ') {
					tmp[i] += "<br>";
				}
				result += tmp[i] + " ";
			}
		}
		return result.substring(0, result.length() - 1);
	}
}
