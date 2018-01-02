-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: localhost    Database: scoredaddy
-- ------------------------------------------------------
-- Server version	5.7.20

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `street` varchar(100) NOT NULL,
  `city` varchar(45) NOT NULL,
  `state` varchar(2) NOT NULL,
  `zipcode` varchar(10) NOT NULL,
  `phone` varchar(10) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'Vinh\'s Shooting Academy','11634 Cannington Circle','Fishers','IN','46037','5743298039'),(2,'Tim\'s Shooting Academy','17777 Commerce Dr','Westfield','IN','46074','3173997918');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `competition`
--

DROP TABLE IF EXISTS `competition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `competition` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `description` varchar(200) NOT NULL,
  `status` varchar(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `account_id_idx` (`account_id`),
  KEY `status_idx` (`status`),
  CONSTRAINT `account_id` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `status` FOREIGN KEY (`status`) REFERENCES `status_codes` (`code`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `competition`
--

LOCK TABLES `competition` WRITE;
/*!40000 ALTER TABLE `competition` DISABLE KEYS */;
INSERT INTO `competition` VALUES (1,1,'GSSF Competition','Winter Competition','I');
/*!40000 ALTER TABLE `competition` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `competition_codes`
--

DROP TABLE IF EXISTS `competition_codes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `competition_codes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(20) NOT NULL,
  `description` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`code`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `competition_codes`
--

LOCK TABLES `competition_codes` WRITE;
/*!40000 ALTER TABLE `competition_codes` DISABLE KEYS */;
INSERT INTO `competition_codes` VALUES (1,'GSSFI','GSSF Indoor League');
/*!40000 ALTER TABLE `competition_codes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `competition_competitors`
--

DROP TABLE IF EXISTS `competition_competitors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `competition_competitors` (
  `competition_id` int(11) NOT NULL,
  `competitor_id` int(11) NOT NULL,
  PRIMARY KEY (`competition_id`,`competitor_id`),
  KEY `competitor_id_idx` (`competitor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `competition_competitors`
--

LOCK TABLES `competition_competitors` WRITE;
/*!40000 ALTER TABLE `competition_competitors` DISABLE KEYS */;
INSERT INTO `competition_competitors` VALUES (1,1),(2,1),(1,2),(2,2),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),(1,10),(1,11),(1,12);
/*!40000 ALTER TABLE `competition_competitors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `competition_details`
--

DROP TABLE IF EXISTS `competition_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `competition_details` (
  `id` int(11) NOT NULL,
  `code` int(11) NOT NULL,
  `date` date NOT NULL,
  `course` varchar(1) NOT NULL,
  PRIMARY KEY (`id`,`code`,`date`),
  KEY `code_idx` (`code`),
  KEY `date_idx` (`date`),
  KEY `course_idx` (`course`),
  CONSTRAINT `code` FOREIGN KEY (`id`) REFERENCES `competition_codes` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `course` FOREIGN KEY (`course`) REFERENCES `course_codes` (`course`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `id` FOREIGN KEY (`id`) REFERENCES `competition` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `competition_details`
--

LOCK TABLES `competition_details` WRITE;
/*!40000 ALTER TABLE `competition_details` DISABLE KEYS */;
INSERT INTO `competition_details` VALUES (1,1,'2018-01-06','A');
/*!40000 ALTER TABLE `competition_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `competition_results`
--

DROP TABLE IF EXISTS `competition_results`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `competition_results` (
  `id` int(11) NOT NULL,
  `code` int(11) NOT NULL,
  `date` date NOT NULL,
  `competitor_id` int(11) NOT NULL,
  `firearm_id` int(11) NOT NULL,
  `stock_division` bit(1) NOT NULL,
  `unlimited_division` bit(1) NOT NULL,
  `pocket_division` bit(1) NOT NULL,
  `woman_division` bit(1) NOT NULL,
  `senior_division` bit(1) NOT NULL,
  `junior_division` bit(1) NOT NULL,
  `target_one_x` int(2) NOT NULL,
  `target_one_ten` int(2) NOT NULL,
  `target_one_eight` int(2) NOT NULL,
  `target_one_five` int(2) NOT NULL,
  `target_one_misses` int(2) NOT NULL,
  `target_two_x` int(2) NOT NULL,
  `target_two_ten` int(2) NOT NULL,
  `target_two_eight` int(2) NOT NULL,
  `target_two_five` int(2) NOT NULL,
  `target_two_misses` int(2) NOT NULL,
  `penalty` int(2) NOT NULL,
  `final_score` int(3) NOT NULL,
  `total_x` int(3) NOT NULL,
  `range_officer_initials` varchar(3) NOT NULL,
  `competitor_initials` varchar(3) NOT NULL,
  PRIMARY KEY (`id`,`code`,`date`,`competitor_id`,`firearm_id`,`stock_division`,`unlimited_division`,`pocket_division`),
  KEY `competitor_id_idx` (`competitor_id`),
  KEY `code_idx` (`code`),
  KEY `id_idx` (`id`),
  KEY `firearm_id_idx` (`firearm_id`),
  KEY `competition_date_idx` (`date`),
  CONSTRAINT `competition_date` FOREIGN KEY (`date`) REFERENCES `competition_details` (`date`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `competition_detail_code` FOREIGN KEY (`code`) REFERENCES `competition_details` (`code`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `competition_detail_id` FOREIGN KEY (`id`) REFERENCES `competition_details` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `competitor_id` FOREIGN KEY (`competitor_id`) REFERENCES `competitor` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `firearm_id` FOREIGN KEY (`firearm_id`) REFERENCES `firearm_models` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `competition_results`
--

LOCK TABLES `competition_results` WRITE;
/*!40000 ALTER TABLE `competition_results` DISABLE KEYS */;
INSERT INTO `competition_results` VALUES (1,1,'2018-01-06',1,20,'','\0','\0','\0','\0','\0',20,0,0,0,0,22,7,1,0,0,0,498,42,'RO','VD'),(1,1,'2018-01-06',2,1,'','\0','\0','\0','\0','\0',20,0,0,0,0,10,7,10,3,0,0,465,30,'RO','BB'),(1,1,'2018-01-06',4,26,'','\0','\0','\0','\0','\0',20,0,0,0,0,9,8,10,3,0,0,465,29,'RO','EF'),(1,1,'2018-01-06',5,18,'','\0','\0','\0','\0','\0',19,0,1,0,0,18,9,2,1,0,0,489,37,'RO','DD'),(1,1,'2018-01-06',6,14,'','\0','\0','\0','\0','\0',19,0,1,0,0,18,9,3,0,0,0,492,37,'RO','DD'),(1,1,'2018-01-06',7,9,'','\0','\0','\0','\0','\0',15,3,2,0,0,15,5,5,5,0,0,461,30,'RO','DD'),(1,1,'2018-01-06',8,9,'','\0','\0','\0','\0','\0',15,3,2,0,0,16,4,5,5,0,0,461,31,'RO','MM'),(1,1,'2018-01-06',9,9,'','\0','\0','\0','\0','\0',16,4,0,0,0,16,4,5,5,0,0,465,32,'RO','MM'),(1,1,'2018-01-06',10,9,'','\0','\0','\0','\0','\0',15,4,1,0,0,16,4,4,6,0,0,460,31,'RO','KF'),(1,1,'2018-01-06',11,9,'','\0','\0','\0','\0','\0',15,4,1,0,0,15,4,4,7,0,0,455,30,'RO','JM'),(1,1,'2018-01-06',12,21,'','\0','\0','\0','\0','\0',18,1,1,0,0,20,5,3,2,0,8,474,38,'RO','TC');
/*!40000 ALTER TABLE `competition_results` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `competitor`
--

DROP TABLE IF EXISTS `competitor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `competitor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(20) NOT NULL,
  `last_name` varchar(20) NOT NULL,
  `street` varchar(100) NOT NULL,
  `city` varchar(45) NOT NULL,
  `state` varchar(2) NOT NULL,
  `zipcode` varchar(10) NOT NULL,
  `phone` varchar(10) DEFAULT NULL,
  `email` varchar(256) DEFAULT NULL,
  `gssf_id` varchar(8) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `competitor`
--

LOCK TABLES `competitor` WRITE;
/*!40000 ALTER TABLE `competitor` DISABLE KEYS */;
INSERT INTO `competitor` VALUES (1,'Vinh','Dang','11634 Cannington Circle','Fishers','IN','46037','5743298039','vinh@envisageconsulting.com','12345678'),(2,'Bugs','Bunny','1000 Looney Tunes','Hollywood','CA','12345','5555551212','','1'),(4,'Elmer','Fudd','1000 Looney Tunes','Hollywood','CA','12345','5555551212','','2'),(5,'Daffy','Duck','1000 Looney Tunes','Hollywood','CA','12345','5555551212','','3'),(6,'Porky','Pig','1000 Looney Tunes','Hollywood','CA','12345','5555551212','','4'),(7,'Donald','Duck','1000 Looney Tunes','Hollywood','CA','12345','5555551212','','5'),(8,'Mickey','Mouse','1000 Looney Tunes','Hollywood','CA','12345','5555551212','','6'),(9,'Minnie','Mouse','1000 Looney Tunes','Hollywood','CA','12345','5555551212','','7'),(10,'Kermit','Frog','1000 Looney Tunes','Hollywood','CA','12345','5555551212','','8'),(11,'Jerry','Mouse','1000 Looney Tunes','Hollywood','CA','12345','5555551212','','8'),(12,'Tom','Cat','1000 Looney Tunes','Hollywood','CA','12345','5555551212','','8');
/*!40000 ALTER TABLE `competitor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course_codes`
--

DROP TABLE IF EXISTS `course_codes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course_codes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `course` varchar(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `course_UNIQUE` (`course`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_codes`
--

LOCK TABLES `course_codes` WRITE;
/*!40000 ALTER TABLE `course_codes` DISABLE KEYS */;
INSERT INTO `course_codes` VALUES (2,'A'),(3,'B'),(4,'C'),(5,'D'),(6,'E'),(1,'O');
/*!40000 ALTER TABLE `course_codes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `division_codes`
--

DROP TABLE IF EXISTS `division_codes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `division_codes` (
  `id` int(11) NOT NULL,
  `code` varchar(20) NOT NULL,
  `description` varchar(25) NOT NULL,
  PRIMARY KEY (`code`,`id`),
  KEY `id_idx` (`id`),
  CONSTRAINT `competition_code_id` FOREIGN KEY (`id`) REFERENCES `competition_codes` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `division_codes`
--

LOCK TABLES `division_codes` WRITE;
/*!40000 ALTER TABLE `division_codes` DISABLE KEYS */;
INSERT INTO `division_codes` VALUES (1,'GSSF_JUNIOR','GSSF Junior Division'),(1,'GSSF_POCKET','GSSF Pocket Division'),(1,'GSSF_SENIOR','GSSF Senior Divsion'),(1,'GSSF_STOCK','GSSF Stock Division'),(1,'GSSF_UNLIMITED','GSSF Unlimited Division'),(1,'GSSF_WOMAN','GSSF Woman Division');
/*!40000 ALTER TABLE `division_codes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `firearm_brands`
--

DROP TABLE IF EXISTS `firearm_brands`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `firearm_brands` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `brand` varchar(25) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `firearm_brands`
--

LOCK TABLES `firearm_brands` WRITE;
/*!40000 ALTER TABLE `firearm_brands` DISABLE KEYS */;
INSERT INTO `firearm_brands` VALUES (1,'Glock');
/*!40000 ALTER TABLE `firearm_brands` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `firearm_models`
--

DROP TABLE IF EXISTS `firearm_models`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `firearm_models` (
  `id` int(11) NOT NULL,
  `brand_id` int(11) NOT NULL,
  `model` varchar(15) NOT NULL,
  `caliber` varchar(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `firearm_model_id_idx` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `firearm_models`
--

LOCK TABLES `firearm_models` WRITE;
/*!40000 ALTER TABLE `firearm_models` DISABLE KEYS */;
INSERT INTO `firearm_models` VALUES (1,1,'G17','9X19'),(2,1,'G17C','9X19'),(3,1,'G17L','9X19'),(4,1,'G19','9X19'),(5,1,'G19C','9X19'),(6,1,'G20','10mm Auto'),(7,1,'G21','.45 Auto'),(8,1,'G22','.40'),(9,1,'G22C','.40'),(10,1,'G23','.40'),(11,1,'G23C','.40'),(12,1,'G24','.40'),(13,1,'G26','9X19'),(14,1,'G27','.40'),(15,1,'G29','10mm Auto'),(16,1,'G30','.45 Auto'),(17,1,'G31','.357'),(18,1,'G32','.357'),(19,1,'G33','.357'),(20,1,'G34','9X19'),(21,1,'G35','.40'),(22,1,'G36 ','.45 Auto'),(23,1,'G37','.45 G.A.P.'),(24,1,'G38','.45 G.A.P.'),(25,1,'G39','.45 G.A.P.'),(26,1,'G40','10mm Auto'),(27,1,'G41','.45 Auto'),(28,1,'G42','.380 Auto'),(29,1,'G43','9X19');
/*!40000 ALTER TABLE `firearm_models` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_codes`
--

DROP TABLE IF EXISTS `role_codes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_codes` (
  `code` varchar(1) NOT NULL,
  `description` varchar(45) NOT NULL,
  PRIMARY KEY (`code`),
  UNIQUE KEY `code_UNIQUE` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_codes`
--

LOCK TABLES `role_codes` WRITE;
/*!40000 ALTER TABLE `role_codes` DISABLE KEYS */;
INSERT INTO `role_codes` VALUES ('A','Admin'),('C','Competitor'),('M','Manager');
/*!40000 ALTER TABLE `role_codes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status_codes`
--

DROP TABLE IF EXISTS `status_codes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `status_codes` (
  `code` varchar(1) NOT NULL,
  `description` varchar(45) NOT NULL,
  PRIMARY KEY (`code`),
  UNIQUE KEY `id_UNIQUE` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status_codes`
--

LOCK TABLES `status_codes` WRITE;
/*!40000 ALTER TABLE `status_codes` DISABLE KEYS */;
INSERT INTO `status_codes` VALUES ('C','Complete'),('I','In Progress'),('N','Not Started');
/*!40000 ALTER TABLE `status_codes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_code` varchar(1) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `username` varchar(10) NOT NULL,
  `password` varchar(166) NOT NULL,
  `email` varchar(256) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `last_login` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  KEY `role_code_idx` (`role_code`),
  CONSTRAINT `role_code` FOREIGN KEY (`role_code`) REFERENCES `role_codes` (`code`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (3,'A','Vinh','Dang','vinh','1000:c897941d33b757fa15979d4f2f2087b6:4d8f5ddbba694e1c8a66b9349d07875bab6c620d86029426908e2977e158b4e14b3db5876bfbeec13242cfb85c2904aef5f1ff8cf0b3d6bf7911c1984136a4a6','vinh@envisageconsulting.com','2017-08-09 22:04:40',NULL),(4,'A','Vinh','Dang','mitsu98gt','1000:60d85ba22889d434be9b90e187b05ee1:4fa0026edde9156f1065171b8ed2a3ae02289af13b852c8624da90c67634815e21eb0b04377c1d3636def116b60dd6d4eeb74460a55f0481b13f992fc5da6c63','vinh@envisageconsulting.com','2017-09-26 23:41:02',NULL),(8,'A','Vinh','Dang','mitsu98gt2','1000:60d85ba22889d434be9b90e187b05ee1:4fa0026edde9156f1065171b8ed2a3ae02289af13b852c8624da90c67634815e21eb0b04377c1d3636def116b60dd6d4eeb74460a55f0481b13f992fc5da6c63','vinh@envisageconsulting.com','2017-09-27 00:03:37',NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-02  1:36:11
