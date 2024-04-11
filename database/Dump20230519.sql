-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: movieweb
-- ------------------------------------------------------
-- Server version	8.0.32

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
-- Table structure for table `cinema`
--

DROP TABLE IF EXISTS `cinema`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cinema` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `src` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cinema`
--

LOCK TABLES `cinema` WRITE;
/*!40000 ALTER TABLE `cinema` DISABLE KEYS */;
INSERT INTO `cinema` VALUES (1,'上海巨影Suprinema（万乐城店）','闵行区莲花南路3999弄7号楼2楼','https://p0.meituan.net/mmdb/fd146c7848a0ebca36eb869dfef7c9331034607.png@292w_292h_1e_1c'),(2,'AMG海上明珠影城（上海环球港RealDCinema店）','普陀区中山北路3300号4楼L4039','https://tbvideo-oss.taopiaopiao.com//15046/1615967442003%E7%8E%AF%E7%90%83%E6%B8%AF%E7%85%A7%E7%89%87.jpg'),(3,'幸福蓝海国际影城（彩生活店）','闵行区莘福路288号彩生活时代广场1号楼5层（近莘松路）','https://gw.alicdn.com/tfscom/i2/TB1GvikjdknBKNjSZKPXXX6OFXa_.jpg');
/*!40000 ALTER TABLE `cinema` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `favor`
--

DROP TABLE IF EXISTS `favor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `favor` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `movie_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `favor_user_id_idx` (`user_id`),
  KEY `favor_movie_id_idx` (`movie_id`),
  CONSTRAINT `favor_movie_id` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`id`),
  CONSTRAINT `favor_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favor`
--

LOCK TABLES `favor` WRITE;
/*!40000 ALTER TABLE `favor` DISABLE KEYS */;
/*!40000 ALTER TABLE `favor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie`
--

DROP TABLE IF EXISTS `movie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movie` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(45) NOT NULL,
  `time` date DEFAULT NULL,
  `director` varchar(45) DEFAULT NULL,
  `actor` varchar(45) DEFAULT NULL,
  `mark` decimal(3,1) DEFAULT NULL,
  `type` bigint DEFAULT NULL,
  `duration` time DEFAULT NULL,
  `src` varchar(255) DEFAULT NULL,
  `intro` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie`
--

LOCK TABLES `movie` WRITE;
/*!40000 ALTER TABLE `movie` DISABLE KEYS */;
INSERT INTO `movie` VALUES (1,'流浪地球2','2023-01-22','郭帆','吴京 刘德华 李雪健 沙溢',9.3,3,'02:53:00','https://p0.pipi.cn/mmdb/fb73868d9ab923338f300ba4e3ad51eb64f40.jpg?imageView2/1/w/464/h/644','太阳即将毁灭，人类在地球表面建造出巨大的推进器，寻找新的家园，然而宇宙之路危机四伏，为了拯救地球，流浪地球时代的年轻人再次挺身而出，展开争分夺秒的生死之战...'),(2,'满江红','2023-01-22','张艺谋','沈腾 易烊千玺 张译 雷佳音',9.4,12,'02:39:00','https://p0.pipi.cn/mmdb/fb7386028ea51bb12dd2363a4352b3f238c4e.jpg?imageView2/1/w/464/h/644','张艺谋最新高口碑悬疑喜剧感动热映中，沈腾、易烊千玺、张译、雷佳音、岳云鹏赴局探案有惊有喜，震撼演绎中国式传奇故事！南宋绍兴年间，岳飞死后四年，秦桧率兵与金国会谈。会谈前夜，金国使者死在宰相驻地，所携密信也不翼而飞。小兵张大（沈腾 饰）与亲兵营副统领孙均（易烊千玺 饰）机缘巧合被裹挟进这巨大阴谋之中，宰相秦桧（雷佳音 饰）命两人限一个时辰之内找到凶手。伴随危机四伏的深入调查，宰相府总管何立（张译 饰）、副总管武义淳（岳云鹏 饰）、舞姬瑶琴（王佳怡 饰）等人卷入局中，案件的背后似乎隐藏着一场更大的阴谋。局中有局、人心叵测，一夜之间风云变幻，各方势力暗流涌动……'),(3,'人生路不熟','2023-04-28','易小星','乔杉 范丞丞 马丽 张婧仪',9.4,8,'01:40:00','https://p0.pipi.cn/mmdb/fb73868771f2ff300b16bd2c198f17d0602eb.jpg?imageView2/1/w/464/h/644','超千万观众认证的高分喜剧！爆笑之外收获燃泪感动！乔杉、范丞丞、马丽、张婧仪、常远、田雨、尹正笑闹囧途！车队大佬周东海（乔杉 饰）和老婆霍梅梅（马丽 饰）阴差阳错地与女儿周微雨（张婧仪 饰）及女儿的男朋友万一帆（范丞丞 饰）同行自驾，踏上人生路不熟的探亲之旅，周东海决定借此机会给准女婿来一场全方位无死角的考察，万一帆也用生命演绎了什么叫做教科书级翻车。一路上先后遭遇周东海死对头“添堵专业户”贾主任（田雨 饰）的房车事故、微雨“青梅竹马”光子(常远 饰)的“野鸡”山庄醉酒闹剧、捉拿公路贼团“油耗子”的围堵大战，鸡飞狗跳的探亲之旅窘态百出，爆笑不断！'),(4,'银河护卫队','2023-05-05','詹姆斯·古德','克里斯·帕拉特 佐伊·索尔达娜',9.4,19,'02:30:00','https://p0.pipi.cn/mmdb/fb73868787ac69cbae71f711a2c78ce70aa1a.jpg?imageView2/1/w/464/h/644','银河护卫队全员回归，寻觅身世记忆，迎战全新危机，携手踏上最后一次旅程。'),(5,'铃芽之旅','2023-03-24','新海诚','原菜乃华 松村北斗 深津绘里',9.1,96,'02:02:00','https://p0.pipi.cn/mmdb/fb73869a3390fa281e395ba5f89fe7142e4ab.jpg?imageView2/1/w/464/h/644','生活在日本九州田舍的17岁少女・铃芽遇见了为了寻找“门”而踏上旅途的青年。追随着青年的脚步，铃芽来到了山上一片废墟之地，在这里静静伫立着一扇古老的门，仿佛是坍塌中存留的唯一遗迹。铃芽仿佛被什么吸引了一般，将手伸向了那扇门…不久之后，日本各地的门开始一扇一扇地打开。据说，开着的门必须关上，否则灾祸将会从门的那一边降临于现世。星星，夕阳，拂晓，误入之境的天空，仿佛溶解了一切的时间。在神秘之门的引导下，铃芽踏上了关门的旅途。'),(6,'你好，李焕英','2021-02-12','贾玲','贾玲 张小斐 沈腾 陈赫',9.5,8,'02:08:00','https://p0.pipi.cn/mmdb/fb73869aecd92351ba71f7fbd06f84db82a66.jpg?imageView2/1/w/464/h/644','2001年的某一天，刚刚考上大学的贾晓玲（贾玲 饰）经历了人生中的一次大起大落。一心想要成为母亲骄傲的她却因母亲突遭严重意外，而悲痛万分。在贾晓玲情绪崩溃的状态下，竟意外的回到了 1981 年，并与年轻的母亲李焕英（张小斐 饰）相遇，二人形影不离，宛如闺蜜。与此同时，也结识了一群天真善良的好朋友。晓玲以为来到了这片“广阔天地”，她可以凭借自己超前的思维，让母亲“大有作为”，但结果却让晓玲感到意外......');
/*!40000 ALTER TABLE `movie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `new_order`
--

DROP TABLE IF EXISTS `new_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `new_order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `time` datetime DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `total_price` decimal(10,2) DEFAULT NULL,
  `is_paid` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `new_order`
--

LOCK TABLES `new_order` WRITE;
/*!40000 ALTER TABLE `new_order` DISABLE KEYS */;
INSERT INTO `new_order` VALUES (15,'2023-05-12 22:07:23',1,79.00,1),(16,'2023-05-19 12:37:33',1,39.50,1);
/*!40000 ALTER TABLE `new_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `time` datetime DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `total_price` decimal(10,2) DEFAULT NULL,
  `is_paid` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `order_user_id_idx` (`user_id`),
  CONSTRAINT `order_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (3,'2023-05-20 00:00:01',1,79.00,1);
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `cinema_id` int NOT NULL,
  `row_num` int DEFAULT NULL,
  `col_num` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `room_cinema_id_idx` (`cinema_id`),
  CONSTRAINT `room_cinema_id` FOREIGN KEY (`cinema_id`) REFERENCES `cinema` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` VALUES (1,'放映厅1',1,8,9),(2,'放映厅2',1,8,9),(3,'放映厅1',2,8,9),(4,'放映厅1',3,8,9);
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `session`
--

DROP TABLE IF EXISTS `session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `session` (
  `id` int NOT NULL AUTO_INCREMENT,
  `room_id` int NOT NULL,
  `movie_id` int NOT NULL,
  `seat` varchar(255) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `session_room_id_idx` (`room_id`),
  KEY `session_movie_id_idx` (`movie_id`),
  CONSTRAINT `session_movie_id` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`id`),
  CONSTRAINT `session_room_id` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `session`
--

LOCK TABLES `session` WRITE;
/*!40000 ALTER TABLE `session` DISABLE KEYS */;
INSERT INTO `session` VALUES (1,1,1,'111111111111000111111111111111111111111111111111111111111111111111111111','2023-05-20 09:00:00',39.50),(2,1,1,'111111111111111111111111111111111111111111111111111111111111111111111111','2023-05-20 12:00:00',39.50),(3,1,1,'111111111111111111111111111111111111111111111111111111111111111111111111','2023-05-20 15:00:00',39.50),(4,2,1,'111111111111111111111111111111111111111111111111111111111111111111111111','2023-05-20 10:00:00',41.00),(5,3,1,'111111111111111111111111111111111111111111111111111111111111111111111111','2023-05-20 12:00:00',40.00),(6,4,1,'111111111111111111111111111111111111111111111111111111111111111111111111','2023-05-20 09:10:00',38.00);
/*!40000 ALTER TABLE `session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ticket` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` int DEFAULT NULL,
  `session_id` int DEFAULT NULL,
  `row_id` int DEFAULT NULL,
  `col_id` int DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `state` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ticket_order_id_idx` (`order_id`),
  KEY `ticket_session_id_idx` (`session_id`),
  CONSTRAINT `ticket_order_id` FOREIGN KEY (`order_id`) REFERENCES `new_order` (`id`),
  CONSTRAINT `ticket_session_id` FOREIGN KEY (`session_id`) REFERENCES `session` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket`
--

LOCK TABLES `ticket` WRITE;
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
INSERT INTO `ticket` VALUES (17,15,1,1,3,39.50,0),(18,15,1,1,4,39.50,0),(19,16,1,1,5,39.50,0);
/*!40000 ALTER TABLE `ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `gender` int DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `area` varchar(255) DEFAULT NULL,
  `intro` varchar(255) DEFAULT NULL,
  `role` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'customer1',0,'2004-01-01','爱沙尼亚','顾客1',0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_auth`
--

DROP TABLE IF EXISTS `user_auth`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_auth` (
  `id` int NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `user_auth_id` FOREIGN KEY (`id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_auth`
--

LOCK TABLES `user_auth` WRITE;
/*!40000 ALTER TABLE `user_auth` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_auth` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'movieweb'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-19 13:49:45
