-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: wheretogo
-- ------------------------------------------------------
-- Server version	5.7.13-log

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
-- Table structure for table `categories`
--
CREATE DATABASE IF NOT EXISTS `wheretogo` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `wheretogo`;

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categories` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'Concert'),(2,'Movie'),(3,'Nature'),(4,'Pub'),(5,'Sport'),(6,'Theatre'),(7,'Trip'),(8,'Other'),(9,'Concert'),(10,'Movie'),(11,'Nature'),(12,'Pub'),(13,'Sport'),(14,'Theatre'),(15,'Trip'),(16,'Other'),(17,'Concert'),(18,'Movie'),(19,'Nature'),(20,'Pub'),(21,'Sport'),(22,'Theatre'),(23,'Trip'),(24,'Other');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `currencies`
--

DROP TABLE IF EXISTS `currencies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `currencies` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `currencies`
--

LOCK TABLES `currencies` WRITE;
/*!40000 ALTER TABLE `currencies` DISABLE KEYS */;
INSERT INTO `currencies` VALUES (1,'UAH'),(2,'USD'),(3,'EUR'),(4,'UAH'),(5,'USD'),(6,'EUR'),(7,'UAH'),(8,'USD'),(9,'EUR');
/*!40000 ALTER TABLE `currencies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `events`
--

DROP TABLE IF EXISTS `events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `events` (
  `id` bigint(20) NOT NULL,
  `cost` int(11) DEFAULT NULL,
  `deleted` int(1) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `endDateTime` datetime DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `name` varchar(30) DEFAULT NULL,
  `picture` longblob,
  `startDateTime` datetime DEFAULT NULL,
  `currencyId` bigint(20) DEFAULT NULL,
  `owner` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_7it2uhguu43kdlt6c3gw5snpl` (`currencyId`),
  KEY `FK_ae4spempo860i7yo3uh2nuec9` (`owner`),
  CONSTRAINT `FK_7it2uhguu43kdlt6c3gw5snpl` FOREIGN KEY (`currencyId`) REFERENCES `currencies` (`id`),
  CONSTRAINT `FK_ae4spempo860i7yo3uh2nuec9` FOREIGN KEY (`owner`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `events`
--

LOCK TABLES `events` WRITE;
/*!40000 ALTER TABLE `events` DISABLE KEYS */;
INSERT INTO `events` VALUES (1,100,0,'Description of the event','2016-07-10 23:38:00','Lepse Street, 6z, Kiev','Concert....',NULL,'2016-07-10 22:00:00',1,1),(2,200,0,'Description of the event','2016-07-11 23:38:00','Lepse Street, 6z, Kiev','Movie......',NULL,'2016-07-11 22:00:00',2,1),(3,300,0,'Description of the event','2016-07-12 23:38:00','Lepse Street, 6z, Kiev','Nature.....',NULL,'2016-07-12 22:00:00',3,1),(4,400,0,'Description of the event','2016-07-13 23:38:00','Lepse Street, 6z, Kiev','Pub........',NULL,'2016-07-13 22:00:00',1,2),(5,500,0,'Description of the event','2016-07-14 23:38:00','Lepse Street, 6z, Kiev','Sport......0',NULL,'2016-07-14 22:00:00',2,1),(6,600,0,'Description of the event','2016-04-22 23:38:00','Lepse Street, 6z, Kiev','Theatre....0',NULL,'2016-04-22 22:00:00',3,1),(7,700,0,'Description of the event','2016-04-24 23:38:00','Lepse Street, 6z, Kiev','Trip.......',NULL,'2016-04-24 13:38:00',1,1),(8,800,0,'Description of the event','2016-04-24 23:38:00','Lepse Street, 6z, Kiev','Other......',NULL,'2016-04-24 22:00:00',2,1),(9,500,0,'Description of the event1','2016-07-15 23:38:00','Lepse Street, 6z, Kiev','Sport......1',NULL,'2016-07-15 22:00:00',2,1),(10,500,0,'Description of the event2','2016-04-27 23:38:00','Lepse Street, 6z, Kiev','Sport......2',NULL,'2016-04-27 22:00:00',2,1),(11,500,0,'Description of the event3','2016-04-28 23:38:00','Lepse Street, 6z, Kiev','Sport......3',NULL,'2016-04-28 22:00:00',2,1),(12,600,0,'Description of the event','2016-04-23 23:38:00','Lepse Street, 6z, Kiev','Theatre....1',NULL,'2016-04-23 22:00:00',3,1),(13,600,0,'Description of the event','2016-04-24 23:38:00','Lepse Street, 6z, Kiev','Theatre....2',NULL,'2016-04-24 22:00:00',3,1),(14,600,0,'Description of the event','2016-04-24 23:38:00','Lepse Street, 6z, Kiev','Theatre....3',NULL,'2016-04-24 22:00:00',3,1),(15,600,0,'Description of the event','2016-04-24 23:38:00','Lepse Street, 6z, Kiev','Theatre....4',NULL,'2016-04-24 22:00:00',3,1);
/*!40000 ALTER TABLE `events` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `events_categories`
--

DROP TABLE IF EXISTS `events_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `events_categories` (
  `event_id` bigint(20) NOT NULL,
  `category_id` bigint(20) NOT NULL,
  KEY `FK_65ecwptoj3noo3k6yxskg8doc` (`category_id`),
  KEY `FK_1lbgpsw8xxdvv99hx7cveeor8` (`event_id`),
  CONSTRAINT `FK_1lbgpsw8xxdvv99hx7cveeor8` FOREIGN KEY (`event_id`) REFERENCES `events` (`id`),
  CONSTRAINT `FK_65ecwptoj3noo3k6yxskg8doc` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `events_categories`
--

LOCK TABLES `events_categories` WRITE;
/*!40000 ALTER TABLE `events_categories` DISABLE KEYS */;
INSERT INTO `events_categories` VALUES (1,1),(2,2),(3,3),(4,4),(5,5),(9,5),(10,5),(11,5),(6,6),(12,6),(13,6),(14,6),(15,6),(7,7),(8,8);
/*!40000 ALTER TABLE `events_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `events_id`
--

DROP TABLE IF EXISTS `events_id`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `events_id` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `events_id`
--

LOCK TABLES `events_id` WRITE;
/*!40000 ALTER TABLE `events_id` DISABLE KEYS */;
/*!40000 ALTER TABLE `events_id` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `events_users`
--

DROP TABLE IF EXISTS `events_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `events_users` (
  `event_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`event_id`,`user_id`),
  KEY `FK_q3fcox8d0oyb6aaw7nl5icwqu` (`user_id`),
  CONSTRAINT `FK_8uxp9c38flhb5v85eta7w5nji` FOREIGN KEY (`event_id`) REFERENCES `events` (`id`),
  CONSTRAINT `FK_q3fcox8d0oyb6aaw7nl5icwqu` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `events_users`
--

LOCK TABLES `events_users` WRITE;
/*!40000 ALTER TABLE `events_users` DISABLE KEYS */;
/*!40000 ALTER TABLE `events_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL DEFAULT b'1',
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(30) DEFAULT NULL,
  `last_name` varchar(30) DEFAULT NULL,
  `password` varchar(60) DEFAULT NULL,
  `role` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'','root@gmail.com','Root','Root','$2a$10$HbZiH.4zZp4CLY7krwzt9udnO36T23LnqgqE.WXhpArZ4nvys7T0a','admin'),(2,'','test@gmail.com','Test','Test','$2a$10$xBVIkkIXVTc.K.frxLyOM.cQSZ2GABMphVbFEfCFZTh.reKWutt6u','user'),(3,'','serg.tanchenko@gmail.com','Maria ','Anders','$2a$10$HbZiH.4zZp4CLY7krwzt9udnO36T23LnqgqE.WXhpArZ4nvys7T0a','user'),(4,'','xvxsergxvx@gmail.com','Ana ','Trujillo','$2a$10$HbZiH.4zZp4CLY7krwzt9udnO36T23LnqgqE.WXhpArZ4nvys7T0a','user');
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

-- Dump completed on 2016-07-10 13:15:12
