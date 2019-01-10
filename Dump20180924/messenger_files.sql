CREATE DATABASE  IF NOT EXISTS `messenger` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `messenger`;
-- MySQL dump 10.13  Distrib 8.0.11, for Win64 (x86_64)
--
-- Host: localhost    Database: messenger
-- ------------------------------------------------------
-- Server version	8.0.11

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `files`
--

DROP TABLE IF EXISTS `files`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `files` (
  `name` varchar(50) NOT NULL,
  `txt_file` longblob,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `files`
--

LOCK TABLES `files` WRITE;
/*!40000 ALTER TABLE `files` DISABLE KEYS */;
INSERT INTO `files` VALUES ('2018-09-23.txt',' Message ID: 136\n From User : 1\n To   User : 2\n Date      : 23/09/2018 - 23:33:25\n Message   : hi bm \n\r\n Message ID: 137\n From User : 1\n To   User : 2\n Date      : 23/09/2018 - 23:53:31\n Message   : how are you?\n\r\n Message ID: 138\n From User : 2\n To   User : 1\n Date      : 23/09/2018 - 23:56:20\n Message   : hi. Fine you?\n\r\n'),('2018-09-24.txt',' Message ID: 139\n From User : 2\n To   User : 5\n Date      : 24/09/2018 - 00:00:40\n Message   : hello\n\r\n Message ID: 140\n From User : 2\n To   User : 9\n Date      : 24/09/2018 - 00:01:03\n Message   : hi\n\r\n Message ID: 141\n From User : 4\n To   User : 2\n Date      : 24/09/2018 - 00:02:12\n Message   : hahaha\n\r\n Message ID: 142\n From User : 4\n To   User : 1\n Date      : 24/09/2018 - 00:02:25\n Message   : geia sou re admin :)\n\r\n');
/*!40000 ALTER TABLE `files` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-09-24  0:03:41
