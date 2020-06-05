package com.ssafy.wiselife.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.wiselife.domain.Area;
import com.ssafy.wiselife.domain.Meeting;
import com.ssafy.wiselife.domain.MeetingImages;
import com.ssafy.wiselife.domain.User;
import com.ssafy.wiselife.domain.UserMeeting;
import com.ssafy.wiselife.repository.AreaRepository;
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
	private AreaRepository arearepo;

	@Autowired
	private MeetingImagesRepository meetingimagesrepo;

	static List<String> area5, area4;
	static String[] area2 = {"충북", "충남", "전북", "전남", "경북", "경남"};
	
	public void meetingDataImport() {
		System.out.println("------모임/강좌 데이터 insert------");
		long[] admin = { 1362679482, 1369412523, 1363992434, 1367864624, 1366098022 };

		BufferedReader br = null, brImg = null;
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date day = null;
		int cnt = 0;
		
		List<Area> areaList = arearepo.findAll();
		area5 = new ArrayList<>();
		area4 = new ArrayList<>();
		
		for (Area area : areaList) {
			String firstArea = area.getFirstArea();
			if(firstArea.length() > 4)
				area5.add(firstArea);
			else
				area4.add(firstArea);
		}
		
		
		try {
			br = Files.newBufferedReader(Paths.get("C:\\meetings.csv"));
			brImg = Files.newBufferedReader(Paths.get("C:\\images.csv"));

			String line = "", imgLine = "";
			int index = 0;

			while ((line = br.readLine()) != null) {
				imgLine = brImg.readLine();
				if (cnt != 0) {
					String array[] = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
					
					//주소가 이상하면 그냥 넘김
					if(!checkArea(array[23]))
						continue;
					
					String imgArray[] = imgLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);

					// 관리자 배정
					array[1] = admin[index++] + "";
					if (index == 5)
						index = 0;

					// 주소 가공
					array[13] = array[13].replace("\"", "");

					// 제목 가공
					array[22] = array[22].replace("\"", "");

					// 내용 수정
					array[11] = array[11].substring(1, array[11].length());
					array[11] = array[11].substring(239, array[11].length() - 1);

					// 카테고리 분류
					int main_category = findCategory(array[22]);
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
								.replace("_", "").replace("\"", "").replace("&", "").replace(",", "");

						if (!tmp.isBlank())
							save_tags += tmp + " ";
					}

					array[21] = save_tags.substring(0, save_tags.length() - 1);

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
						if (!imgArray[1].equals("None") && !imgArray[1].equals(" ") && imgArray[1] != null
								&& !imgArray[1].isBlank()) {
							imgArray[1] = imgArray[1].substring(1, imgArray[1].length() - 1);
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
				System.out.println("총 "+(cnt-1) +"개의 데이터 저장 성공");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static boolean checkArea(String content) {
		if(content.length() == 2){
			for (String area : area2) {
				if(area.equals(content)) return true;
			}
			for (String area : area5) {
				area = area.substring(0, 2);
				if(area.equals(content)) return true;
			}
		} else if(content.length() > 2 && content.length() <= 4) {
			for (String area : area4) {
				if(area.equals(content)) return true;
			}
		} else if(content.length() > 4) {
			for (String area : area5) {
				if(area.equals(content)) return true;
			}
		} 
		return false;
	}

	public static int findCategory(String title) {
		List<String> category = new ArrayList<>();
		category.add("레저 스포츠 축구 등산 요가 골프 수상레저 필라테스 클라이밍 보드 야구 농구 축구 암벽등반 보드 스케이트 롤러스케이트 헬스 패들보드 승마 탁구 윈드서핑 복싱 크림치즈");
		category.add("요리 쿠킹 베이킹 맛있는 마카롱 전통주 스콘 케이크 맥주 와인 바리스타 위스키");
		category.add("수공예 공방 가죽공예 목굥예 금속공예 꽃꽂이 향초 도자기 의류 가죽 도예 꽃다발 플라워 프랑스자수 자수 그릇 협탁 천연 천연비누 꽃바구니 라탄 캔들 아기옷 공예 원목 굿즈 디퓨저 제작 수제 귀걸이 팔찌 목걸이 원목도마");
		category.add("놀이 게임 방탈출 보드게임 pc방 오락");
		category.add("문화 영화 책 공연 전시 콘서트 뮤지컬");
		category.add("예술 음악 미술 사진 영상 댄스 연주 훌라 KPOP 보컬 피아노 아크릴화 수채화 포토샵 풍경화 그림 아트클래스 드로잉 인물화 노래 기초발성 바이올린 그리기 가야금 드럼 스케치");
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
}
