package com.ssafy.wiselife.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MeetingDataImport {
	public static void main(String[] args) {
		meetingDataImport();
		meetingImageDataImport();
	}

	public static void meetingDataImport() {
		System.out.println("------모임/강좌 데이터 insert------");
		long[] admin = { 1362679482, 1369412523, 1363992434, 1367864624, 1366098022 };

		BufferedReader br = null;
		BufferedWriter bw = null;

		try {
			br = Files.newBufferedReader(Paths.get("C:\\meetings.csv"));
			bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream("C:\\Users\\we963\\Desktop\\s02p31b105\\Data\\new_meeting.csv"), "euckr"));
			String line = "";
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
					
					// 제목 가공
					array[22] = array[22].replace(",", "/");
					
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
						String tmp = new_tags[j].replace("[", "").replace("]", "").replace("(", "")
								.replace(")", "").replace("/", "").replace("&", "").replace(".", "")
								.replace(":", "").replace("-", "").replace("_", "").replace("\"", "");
						
						if(!tmp.isBlank())
							save_tags += tmp + " ";
					}
					array[21] = save_tags.substring(0, save_tags.length() - 1);
					
					array[23] = array[23].replace(",", "");
					array[24] = array[24].replace(",", "");
					if(array[23].isBlank())
						array[23] = "X";
					if(array[24].isBlank())
						array[24] = "X";
					
					int i = 0;
					
					bw.write(array[i++] + "," + array[i++] + "," + array[i++] + "," + array[i++] + "," + array[i++]
							+ "," + array[i++] + "," + array[i++] + ",");
					i++;
					bw.write(array[i++] + "," + array[i++] + ","
							+ array[i++] + "," + array[i++] + "," + array[i++] + "," + array[i++] + "," + array[i++]
							+ "," + array[i++] + "," + array[i++] + "," + array[i++] + "," + array[i++] + ","
							+ array[i++] + "," + array[i++] + "," + array[i++] + "," + array[i++] + "," + array[i++]
							+ "," + array[i++] + "\n");
				}
				cnt++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
					bw.close();
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

	public static void meetingImageDataImport() {
		System.out.println("------모임/강좌 이미지 데이터 insert------");
		long[] admin = { 1362679482, 1369412523, 1363992434, 1367864624, 1366098022 };

		BufferedReader br = null;
		BufferedWriter bw = null;

		try {
			br = Files.newBufferedReader(Paths.get("C:\\images.csv"));
			bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream("C:\\Users\\we963\\Desktop\\s02p31b105\\Data\\new_images.csv"), "euckr"));
			String line = "";
			int index = 0;
			int cnt = 0;
			while ((line = br.readLine()) != null) {
				if (cnt != 0) {
					String array[] = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);

					// 관리자 배정
					long adminId = admin[index++];
					if (index == 5)
						index = 0;

					int i = 0;
					bw.write(adminId + "," + array[i++] + "," + array[i++]);
					bw.newLine();
				}
				cnt++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
					bw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
