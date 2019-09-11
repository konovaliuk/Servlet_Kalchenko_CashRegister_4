-- MySQL dump 10.13  Distrib 8.0.17, for Win64 (x86_64)
--
-- Host: localhost    Database: cashreg
-- ------------------------------------------------------
-- Server version	8.0.17

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
-- Table structure for table `check`
--

DROP TABLE IF EXISTS `check`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `check` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creator` int(11) NOT NULL COMMENT 'Автор',
  `crtime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Время создания чека',
  `total` double NOT NULL DEFAULT '0' COMMENT 'Сумма чека',
  `discount` double NOT NULL DEFAULT '0' COMMENT 'Сумма скидки',
  `canceled` int(1) NOT NULL DEFAULT '0' COMMENT 'Признак аннулирования',
  PRIMARY KEY (`id`),
  CONSTRAINT `canceled` CHECK (((`canceled` = 0) or (`canceled` = 1)))
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Чек';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `check`
--

LOCK TABLES `check` WRITE;
/*!40000 ALTER TABLE `check` DISABLE KEYS */;
INSERT INTO `check` VALUES (5,2,'2019-09-02 00:37:00',0,0,0),(6,2,'2019-09-02 00:37:00',0,0,0),(7,2,'2019-09-02 00:37:00',0,0,0),(8,2,'2019-09-02 00:37:00',194,0,0),(9,2,'2019-09-02 00:37:00',0,0,0),(10,2,'2019-09-02 00:37:00',144,0,0),(11,2,'2019-09-02 00:37:00',24,0,0),(12,2,'2019-09-02 00:37:00',144,0,0),(13,2,'2019-09-02 00:37:00',24,0,0),(14,2,'2019-09-02 00:37:00',144,0,0),(15,2,'2019-09-02 00:37:00',2464,0,0),(16,2,'2019-09-02 00:37:00',3410,0,0),(17,2,'2019-09-02 00:37:00',1837,0,0),(18,2,'2019-09-02 00:37:00',0,0,0),(19,2,'2019-09-02 00:37:00',10132,0,0),(20,2,'2019-09-02 00:37:00',0,0,0),(21,2,'2019-09-02 00:37:00',903,0,1),(22,2,'2019-09-02 00:37:00',1089,0,0),(23,2,'2019-09-02 00:37:00',250,0,0),(25,2,'2019-09-02 00:37:00',190,0,0),(26,2,'2019-09-02 00:37:00',850,0,0),(27,2,'2019-09-02 00:37:00',1750,0,0),(28,2,'2019-09-06 00:58:13',1650,0,0);
/*!40000 ALTER TABLE `check` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `checkspec`
--

DROP TABLE IF EXISTS `checkspec`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `checkspec` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_check` int(11) NOT NULL COMMENT 'Заголовок счета',
  `id_good` int(11) NOT NULL COMMENT 'Товар',
  `quant` double NOT NULL DEFAULT '0' COMMENT 'Количество',
  `price` double NOT NULL DEFAULT '0' COMMENT 'Цена',
  `total` double NOT NULL DEFAULT '0' COMMENT 'Сумма',
  `nds` int(3) DEFAULT '0' COMMENT '% НДС',
  `ndstotal` double NOT NULL DEFAULT '0' COMMENT 'Сумма НДС',
  `canceled` int(1) NOT NULL DEFAULT '0' COMMENT 'Признак аннулирования спецификации',
  PRIMARY KEY (`id`),
  KEY `id_check_idx` (`id_check`),
  KEY `id_good_idx` (`id_good`),
  CONSTRAINT `id_check` FOREIGN KEY (`id_check`) REFERENCES `check` (`id`),
  CONSTRAINT `id_good` FOREIGN KEY (`id_good`) REFERENCES `goods` (`id`),
  CONSTRAINT `canceled_spec` CHECK (((`canceled` = 0) or (`canceled` = 1)))
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Чек (спецификации)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `checkspec`
--

LOCK TABLES `checkspec` WRITE;
/*!40000 ALTER TABLE `checkspec` DISABLE KEYS */;
INSERT INTO `checkspec` VALUES (7,21,4,12,12,144,20,28.8,0),(8,21,2,23,33,759,20,151.8,0),(9,22,2,33,33,1089,20,217.8,0),(10,23,2,5,100,500,20,100,1),(11,23,4,5,50,250,20,50,1),(12,23,4,5,50,250,20,50,1),(13,23,4,5,50,250,20,50,0),(15,25,2,1,100,100,20,20,0),(16,25,4,5,18,90,20,18,0),(17,26,5,100,8.5,850,20,170,0),(18,27,5,200,8.75,1750,7,122.5,0),(19,28,2,0,33,0,20,0,0),(20,28,2,0,33,0,20,0,0),(21,28,2,50,33,1650,20,330,0);
/*!40000 ALTER TABLE `checkspec` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `goods`
--

DROP TABLE IF EXISTS `goods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` int(4) unsigned NOT NULL,
  `name` varchar(255) NOT NULL,
  `quant` double unsigned zerofill NOT NULL,
  `measure` varchar(45) NOT NULL,
  `comments` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_UNIQUE` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `goods`
--

LOCK TABLES `goods` WRITE;
/*!40000 ALTER TABLE `goods` DISABLE KEYS */;
INSERT INTO `goods` VALUES (2,33,'путрушка',0000000000000000000007,'кг','прим'),(3,20,'помидор',0000000000000000000020,'кг',''),(4,12,'огурцы',0000000000000000000050,'кг',''),(5,34,'арбуз',0000000000000000000100,'кг',''),(6,1,'буряк',0000000000000000000050,'кг',''),(7,2,'морковь',0000000000000000000200,'кг',''),(8,200,'Конфеты Ромашка',0000000000000000000100,'кг','');
/*!40000 ALTER TABLE `goods` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_user_type` int(11) NOT NULL,
  `login` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_user_type_idx` (`id_user_type`),
  CONSTRAINT `id_user_type` FOREIGN KEY (`id_user_type`) REFERENCES `user_type` (`id_user_type`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,4,'a1@gmail.com','1','Сергей'),(2,3,'a2@gmail.com','2','Александр'),(16,2,'a3@gmail.com','3','Николай'),(17,3,'a@gmail.com','1','a@gmail.com'),(18,3,'a@gmail.com','1','a@gmail.com'),(19,3,'a@gmail.com','1','a@gmail.com'),(20,3,'a@gmail.com','1','a@gmail.com'),(21,3,'a@gmail.com','1','a@gmail.com');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_type`
--

DROP TABLE IF EXISTS `user_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_type` (
  `id_user_type` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL,
  `description` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_user_type`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_type`
--

LOCK TABLES `user_type` WRITE;
/*!40000 ALTER TABLE `user_type` DISABLE KEYS */;
INSERT INTO `user_type` VALUES (1,'admin','Администратор'),(2,'senior_cashier','Старший кассир'),(3,'cashier','Кассир'),(4,'goods_spec','Товаровед');
/*!40000 ALTER TABLE `user_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `vcheckspec`
--

DROP TABLE IF EXISTS `vcheckspec`;
/*!50001 DROP VIEW IF EXISTS `vcheckspec`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vcheckspec` AS SELECT 
 1 AS `id`,
 1 AS `id_check`,
 1 AS `id_good`,
 1 AS `quant`,
 1 AS `price`,
 1 AS `total`,
 1 AS `nds`,
 1 AS `ndstotal`,
 1 AS `canceled`,
 1 AS `xname`,
 1 AS `xcode`*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `vcheckspec`
--

/*!50001 DROP VIEW IF EXISTS `vcheckspec`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vcheckspec` AS select `s`.`id` AS `id`,`s`.`id_check` AS `id_check`,`s`.`id_good` AS `id_good`,`s`.`quant` AS `quant`,`s`.`price` AS `price`,`s`.`total` AS `total`,`s`.`nds` AS `nds`,`s`.`ndstotal` AS `ndstotal`,`s`.`canceled` AS `canceled`,`g`.`name` AS `xname`,`g`.`code` AS `xcode` from (`checkspec` `s` join `goods` `g` on((`g`.`id` = `s`.`id_good`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-09-11  2:55:14
