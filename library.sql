-- MySQL dump 10.13  Distrib 5.6.45, for Win32 (AMD64)
--
-- Host: localhost    Database: library
-- ------------------------------------------------------
-- Server version	5.6.45-log

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
-- Table structure for table `book_data`
--

DROP TABLE IF EXISTS `book_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `book_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `author` varchar(255) DEFAULT NULL,
  `publication_year` int(11) DEFAULT NULL,
  `publisher` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `total` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book_data`
--

LOCK TABLES `book_data` WRITE;
/*!40000 ALTER TABLE `book_data` DISABLE KEYS */;
INSERT INTO `book_data` VALUES (1,'Eko Suhartanto',2013,'Elex Media Komputindo','Technopreneurship',50),(2,'Khairul Jasmi',2020,'Republika Penerbit','Pendekar Tanpa Pedang',20),(3,'Amathevs',2020,'Elex Media Komputindo','Day Trading',10);
/*!40000 ALTER TABLE `book_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book_loan_data`
--

DROP TABLE IF EXISTS `book_loan_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `book_loan_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `loan_date` datetime DEFAULT NULL,
  `loan_limit_date` datetime DEFAULT NULL,
  `return_date` datetime DEFAULT NULL,
  `book_data_id` bigint(20) NOT NULL,
  `student_data_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKswhg49tg9pyvo2b8rysrehopv` (`book_data_id`),
  KEY `FKle3om15nogkrof04uk164x1e4` (`student_data_id`),
  CONSTRAINT `FKle3om15nogkrof04uk164x1e4` FOREIGN KEY (`student_data_id`) REFERENCES `student_data` (`id`),
  CONSTRAINT `FKswhg49tg9pyvo2b8rysrehopv` FOREIGN KEY (`book_data_id`) REFERENCES `book_data` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book_loan_data`
--

LOCK TABLES `book_loan_data` WRITE;
/*!40000 ALTER TABLE `book_loan_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `book_loan_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_data`
--

DROP TABLE IF EXISTS `student_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `class_name` varchar(255) DEFAULT NULL,
  `fine` double DEFAULT NULL,
  `limit` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_data`
--

LOCK TABLES `student_data` WRITE;
/*!40000 ALTER TABLE `student_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `student_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'library'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-06-25  9:36:56
