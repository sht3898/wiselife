-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: wiselife
-- ------------------------------------------------------
-- Server version	8.0.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `area`
--

DROP TABLE IF EXISTS `area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `area` (
  `area_id` int NOT NULL,
  `first_area` longtext NOT NULL,
  `second_area` longtext NOT NULL,
  PRIMARY KEY (`area_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `area`
--

LOCK TABLES `area` WRITE;
/*!40000 ALTER TABLE `area` DISABLE KEYS */;
/*!40000 ALTER TABLE `area` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `category_id` int NOT NULL AUTO_INCREMENT,
  `category_name` longtext NOT NULL COMMENT '카테고리 명',
  `category_description` longtext NOT NULL COMMENT '카테고리 설명',
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chatting`
--

DROP TABLE IF EXISTS `chatting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chatting` (
  `chatting_id` bigint NOT NULL AUTO_INCREMENT,
  `uid` bigint NOT NULL,
  `meeting_id` int NOT NULL,
  `writer` varchar(50) NOT NULL,
  `created_at` varchar(45) NOT NULL DEFAULT 'now()',
  `content` longtext NOT NULL,
  PRIMARY KEY (`chatting_id`),
  KEY `user_uid_idx` (`uid`),
  CONSTRAINT `chatting_user_uid_FK` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chatting`
--

LOCK TABLES `chatting` WRITE;
/*!40000 ALTER TABLE `chatting` DISABLE KEYS */;
/*!40000 ALTER TABLE `chatting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `interestcategory`
--

DROP TABLE IF EXISTS `interestcategory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `interestcategory` (
  `category_id` int NOT NULL,
  `uid` bigint NOT NULL,
  PRIMARY KEY (`category_id`,`uid`),
  KEY `user_uid_idx` (`uid`),
  CONSTRAINT `interestcategory_category_category_id_FK` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `interestcategory_user_uid_FK` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='user랑 category 관계에서 필요한 테이블';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `interestcategory`
--

LOCK TABLES `interestcategory` WRITE;
/*!40000 ALTER TABLE `interestcategory` DISABLE KEYS */;
/*!40000 ALTER TABLE `interestcategory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `meeting`
--

DROP TABLE IF EXISTS `meeting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `meeting` (
  `meeting_id` int NOT NULL AUTO_INCREMENT,
  `writer` varchar(50) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_period` int NOT NULL COMMENT '0 : 비정기 모임/강좌, 1 : 정기 모임/강좌',
  `meeting_date` timestamp NOT NULL COMMENT '비정기 일 경우',
  `period_date` timestamp NOT NULL COMMENT '주 n회 / 월 n회',
  `is_class` int NOT NULL COMMENT '0 : 모임 , 1 : 강좌',
  `max_person` int NOT NULL COMMENT '최대 모집인원',
  `now_person` int NOT NULL DEFAULT '1' COMMENT '현재 모집인원',
  `content` longtext NOT NULL,
  `ref_url` longtext COMMENT '참고 url',
  `address` longtext COMMENT '모임 위치 daum 주소 api 사용',
  `fee` int DEFAULT NULL COMMENT '회비 또는 금액을 작성시 숫자를 입력하도록',
  `unit` varchar(50) DEFAULT NULL COMMENT '원/미정/회비',
  `is_active` int NOT NULL COMMENT '강좌/모임 활성화 상태유무 ( 0 : 마감, 1 : 활성화 )',
  `like_cnt` int NOT NULL COMMENT '좋아요 수',
  `view_cnt` int NOT NULL COMMENT '조회 수',
  `main_category` int NOT NULL COMMENT '카테고리 선택',
  `tags` longtext NOT NULL COMMENT '해시태그 최대 5개 (띄어쓰기로 구분) / MySQL - Full Text Search 라는 것을 활용해보자! 검색시.',
  `score` double DEFAULT NULL,
  `uid` bigint NOT NULL,
  PRIMARY KEY (`meeting_id`),
  KEY `category_main_category_idx` (`main_category`),
  KEY `meeting_user_uid_FK_idx` (`uid`),
  CONSTRAINT `meeting_category_meeting_id_FK` FOREIGN KEY (`main_category`) REFERENCES `category` (`category_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `meeting_user_uid_FK` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='강좌/모임 글쓰기, 수정, 디테일';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `meeting`
--

LOCK TABLES `meeting` WRITE;
/*!40000 ALTER TABLE `meeting` DISABLE KEYS */;
/*!40000 ALTER TABLE `meeting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `meetingimages`
--

DROP TABLE IF EXISTS `meetingimages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `meetingimages` (
  `meeting_id` int NOT NULL,
  `image_url` longtext NOT NULL,
  KEY `meetingImages_meeting_meeting_id_FK_idx` (`meeting_id`),
  CONSTRAINT `meetingImages_meeting_meeting_id_FK` FOREIGN KEY (`meeting_id`) REFERENCES `meeting` (`meeting_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `meetingimages`
--

LOCK TABLES `meetingimages` WRITE;
/*!40000 ALTER TABLE `meetingimages` DISABLE KEYS */;
/*!40000 ALTER TABLE `meetingimages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `review`
--

DROP TABLE IF EXISTS `review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `review` (
  `review_id` int NOT NULL AUTO_INCREMENT,
  `uid` bigint NOT NULL,
  `writer` varchar(50) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `score` int NOT NULL COMMENT '평점',
  `image_url` longtext,
  PRIMARY KEY (`review_id`),
  KEY `user_uid_idx` (`uid`),
  CONSTRAINT `review_user_uid_FK` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `survey`
--

DROP TABLE IF EXISTS `survey`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `survey` (
  `survey_id` int NOT NULL AUTO_INCREMENT,
  `uid` bigint NOT NULL,
  `openness` int NOT NULL COMMENT '개방성 점수',
  `conscientiousness` int NOT NULL COMMENT '성실성 점수',
  `extraversion` int NOT NULL COMMENT '외향성 점수',
  `agreeableness` int NOT NULL COMMENT '친화성 점수',
  `neuroticism` int NOT NULL COMMENT '신경성 점수',
  PRIMARY KEY (`survey_id`),
  KEY `survey_user_uid_FK_idx` (`uid`),
  CONSTRAINT `survey_user_uid_FK` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `survey`
--

LOCK TABLES `survey` WRITE;
/*!40000 ALTER TABLE `survey` DISABLE KEYS */;
/*!40000 ALTER TABLE `survey` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `uid` bigint NOT NULL,
  `username` varchar(50) NOT NULL,
  `profile_images` longtext COMMENT '카카오 프로필 이미지 URL (640 or 480)',
  `is_inst` int NOT NULL COMMENT '강사 유무 ( 0 : X , 1 : O)',
  `gender` int NOT NULL COMMENT '성별 ( 1: 남, 2 : 녀)',
  `year` int NOT NULL,
  `ages` int NOT NULL COMMENT '연령대 ( 1,2,3,4,5,6 ) / 백에서 계산해서 넣어두자! now() - year % 10 if 6 이상이면 6 넣자',
  `area_id` int NOT NULL,
  PRIMARY KEY (`uid`),
  KEY `user_area_area_id_FK_idx` (`area_id`),
  CONSTRAINT `user_area_area_id_FK` FOREIGN KEY (`area_id`) REFERENCES `area` (`area_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='test user table / test용이므로 area1, area2 생성 안했음 / profile_images 값이 어떻게 넘어오는지 확인 후 NN처리 유무 결정.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usermeeting`
--

DROP TABLE IF EXISTS `usermeeting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usermeeting` (
  `uid` bigint NOT NULL,
  `meeting_id` int NOT NULL,
  KEY `userMeeting_user_uid_FK_idx` (`uid`),
  KEY `userMeeting_meeting_meeting_id_FK_idx` (`meeting_id`),
  CONSTRAINT `userMeeting_meeting_meeting_id_FK` FOREIGN KEY (`meeting_id`) REFERENCES `meeting` (`meeting_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `userMeeting_user_uid_FK` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='미팅 개설 시 개설자를 유저 미팅에 포함 시켜줘야합니다!';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usermeeting`
--

LOCK TABLES `usermeeting` WRITE;
/*!40000 ALTER TABLE `usermeeting` DISABLE KEYS */;
/*!40000 ALTER TABLE `usermeeting` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-05-19 11:40:06
