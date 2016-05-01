CREATE DATABASE  IF NOT EXISTS `bookstore` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `bookstore`;
-- MySQL dump 10.13  Distrib 5.6.28, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: bookstore
-- ------------------------------------------------------
-- Server version	5.6.28-0ubuntu0.15.04.1

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
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `book` (
  `bookId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `detail` text,
  PRIMARY KEY (`bookId`),
  UNIQUE KEY `BOOK_ID_UNIQUE` (`bookId`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES (1,'book1','type1',1,'book1\'s description'),(2,'book2','type2',2,'book2\'s description'),(3,'book3','type3',3,'book3\'s description'),(4,'book4','type4',4,'book4\'s description'),(5,'book5','type5',5,'book5\'s description'),(6,'book11','type1',11,'book11\'s description'),(8,'book6','type6',6,'book6\'s description'),(10,'book39','type3',39.5,'book39\'s description'),(19,'book62','type6',2,'book62\'s description'),(22,'book63','type6',89,'book63\'s description'),(24,'book78','vip',100,'book78\'s description');
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order` (
  `orderId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userId` int(10) unsigned DEFAULT NULL,
  `bookId` int(10) unsigned DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `time` date DEFAULT NULL,
  PRIMARY KEY (`orderId`),
  UNIQUE KEY `ORDER_ID_UNIQUE` (`orderId`),
  KEY `fk_order_1_idx` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (3,4,5,'address','2015-05-11'),(4,4,4,'address','2015-05-11'),(5,2,1,'address','2015-05-10'),(9,2,3,'address','2015-05-11'),(10,2,4,'address','2015-05-10'),(11,2,4,'address','2015-05-11'),(13,2,10,'address','2015-05-11'),(14,3,2,'address','2015-05-11'),(15,3,3,'address','2015-05-05'),(16,3,4,'address','2015-05-11'),(17,3,5,'address','2015-05-11'),(18,3,10,'address','2015-05-06'),(19,3,10,'address','2015-05-11'),(20,3,8,'address','2015-05-11'),(21,5,3,'address','2015-05-11'),(22,5,4,'address','2015-05-11'),(23,5,5,'address','2015-05-10'),(24,5,6,'address','2015-05-11'),(25,5,7,'address','2015-05-11'),(26,5,7,'address','2015-05-10'),(27,5,8,'address','2015-05-09'),(28,5,10,'address','2015-05-09'),(29,5,19,'address','2015-05-11'),(30,5,3,'address','2015-05-11'),(31,5,3,'address','2015-05-11'),(32,5,5,'address','2015-05-08'),(33,5,5,'address','2015-05-06'),(34,5,6,'address','2015-05-11'),(35,5,6,'address','2015-05-11'),(36,5,7,'address','2015-05-07'),(37,5,7,'address','2015-05-11'),(38,5,10,'address','2015-05-11'),(39,5,10,'address','2015-05-11'),(40,2,3,'address','2015-05-11'),(41,2,3,'address','2015-05-11'),(42,2,4,'address','2015-05-11'),(44,2,5,'address','2015-05-11'),(45,2,6,'address','2015-05-11'),(46,2,6,'address','2015-05-11'),(47,4,2,'address','2015-05-11'),(48,4,6,'address','2015-05-22'),(49,4,1,'address','2015-05-22'),(50,4,10,'address','2015-05-22'),(51,2,1,'address','2015-06-26'),(52,2,4,'address','2015-06-26'),(53,2,3,'address','2015-06-27'),(54,2,4,'address','2015-06-27'),(55,2,6,'address','2015-06-27'),(57,6,10,'address','2015-06-28'),(58,6,2,'address','2015-06-28'),(59,6,4,'address','2015-06-28'),(61,2,2,'address','2016-04-30'),(62,2,3,'address','2016-04-30'),(65,2,1,'address','2016-04-30'),(66,2,2,'address','2016-04-30'),(67,2,1,'address','2016-04-30'),(68,2,1,'address','2016-04-30'),(69,2,1,'address','2016-05-01'),(71,2,1,'address','2016-05-01');
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `USER_ID_UNIQUE` (`userId`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','admin'),(2,'user1','user1'),(3,'user2','user2'),(4,'user3','user3'),(5,'user4','user4'),(6,'user5','user5'),(8,'usermogo','usermogo'),(9,'user10','user11'),(10,'guest','guest');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-05-01 23:15:34
