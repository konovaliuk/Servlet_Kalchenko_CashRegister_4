CREATE DATABASE  IF NOT EXISTS `cashreg` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `cashreg`;
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
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `creator` bigint(20) NOT NULL COMMENT 'Автор',
  `crtime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Время создания чека',
  `total` double NOT NULL DEFAULT '0' COMMENT 'Сумма чека',
  `discount` double NOT NULL DEFAULT '0' COMMENT 'Сумма скидки',
  `canceled` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'Признак аннулирования',
  `registration` int(11) DEFAULT NULL COMMENT 'Регистрация в Z-отчете',
  PRIMARY KEY (`id`),
  KEY `creator_idx` (`creator`),
  CONSTRAINT `creator` FOREIGN KEY (`creator`) REFERENCES `user` (`id`),
  CONSTRAINT `canceled` CHECK (((`canceled` = 0) or (`canceled` = 1)))
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Чек';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `check`
--

LOCK TABLES `check` WRITE;
/*!40000 ALTER TABLE `check` DISABLE KEYS */;
INSERT INTO `check` VALUES (21,2,'2019-09-02 00:37:00',903,0,1,NULL),(22,2,'2019-09-02 00:37:00',1089,0,0,NULL),(23,2,'2019-09-02 00:37:00',500,0,0,NULL),(25,2,'2019-09-02 00:37:00',190,0,0,NULL),(26,2,'2019-09-02 00:37:00',850,0,0,NULL),(27,2,'2019-09-02 00:37:00',1750,0,0,NULL),(28,2,'2019-09-06 00:58:13',1650,0,0,NULL),(29,2,'2019-09-14 23:12:31',168,0,0,NULL),(30,2,'2019-09-14 23:14:00',24,0,0,NULL),(31,2,'2019-09-21 22:34:39',24,0,0,NULL),(35,16,'2019-09-22 03:29:35',4,0,0,1),(36,16,'2019-09-22 03:39:33',17.5,0,0,1);
/*!40000 ALTER TABLE `check` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `checkspec`
--

DROP TABLE IF EXISTS `checkspec`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `checkspec` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_check` bigint(20) NOT NULL COMMENT 'Заголовок счета',
  `id_good` bigint(20) NOT NULL COMMENT 'Товар',
  `quant` double NOT NULL DEFAULT '0' COMMENT 'Количество',
  `price` double NOT NULL DEFAULT '0' COMMENT 'Цена',
  `total` double NOT NULL DEFAULT '0' COMMENT 'Сумма',
  `nds` int(3) DEFAULT '0' COMMENT '% НДС',
  `ndstotal` double NOT NULL DEFAULT '0' COMMENT 'Сумма НДС',
  `canceled` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'Признак аннулирования спецификации',
  PRIMARY KEY (`id`),
  KEY `id_check_idx` (`id_check`),
  KEY `id_good_idx` (`id_good`),
  CONSTRAINT `id_check` FOREIGN KEY (`id_check`) REFERENCES `check` (`id`) ON DELETE CASCADE,
  CONSTRAINT `id_good` FOREIGN KEY (`id_good`) REFERENCES `goods` (`id`),
  CONSTRAINT `canceled_spec` CHECK (((`canceled` = 0) or (`canceled` = 1)))
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Чек (спецификации)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `checkspec`
--

LOCK TABLES `checkspec` WRITE;
/*!40000 ALTER TABLE `checkspec` DISABLE KEYS */;
INSERT INTO `checkspec` VALUES (7,21,4,12,12,144,20,28.8,0),(8,21,2,23,33,759,20,151.8,0),(10,23,2,5,100,500,20,100,1),(11,23,4,5,50,250,20,50,1),(12,23,4,5,50,250,20,50,0),(13,23,4,5,50,250,20,50,0),(15,25,2,1,100,100,20,20,0),(16,25,4,5,18,90,20,18,1),(17,26,5,100,8.5,850,20,170,0),(18,27,5,200,8.75,1750,7,122.5,0),(19,28,2,0,33,0,20,0,0),(20,28,2,0,33,0,20,0,0),(21,28,2,50,33,1650,20,330,0),(24,30,2,2,12,24,20,4.8,0),(25,31,4,2,12,24,20,4.8,0),(26,35,7,2,2,4,20,0.8,0),(27,36,7,2.5,2.5,6.25,20,1.25,0),(28,36,2,3,3.75,11.25,20,2.25,0);
/*!40000 ALTER TABLE `checkspec` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fiscal`
--

DROP TABLE IF EXISTS `fiscal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fiscal` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '№ регистрации(Z-отчета)',
  `total` double DEFAULT NULL COMMENT 'Сумма',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='фискальная память';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fiscal`
--

LOCK TABLES `fiscal` WRITE;
/*!40000 ALTER TABLE `fiscal` DISABLE KEYS */;
INSERT INTO `fiscal` VALUES (1,5803),(2,5803),(3,5803),(4,5803),(5,5803),(6,5803),(7,4714),(8,4714),(9,4714),(10,4714),(11,0),(12,0),(13,0),(14,0),(15,0),(16,0),(17,0),(18,0),(19,0),(20,0),(21,0),(22,0),(23,0),(24,0),(25,0),(26,0),(27,0),(28,0),(29,0),(30,0),(31,0),(32,0),(33,0),(34,0),(35,0),(36,4738),(37,4738),(38,4738),(39,4738),(40,4738),(41,4738),(42,4902),(43,4915.5),(44,4898);
/*!40000 ALTER TABLE `fiscal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `goods`
--

DROP TABLE IF EXISTS `goods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` int(4) unsigned NOT NULL,
  `name` varchar(255) NOT NULL,
  `quant` double unsigned zerofill NOT NULL,
  `measure` varchar(45) NOT NULL,
  `comments` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_UNIQUE` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `goods`
--

LOCK TABLES `goods` WRITE;
/*!40000 ALTER TABLE `goods` DISABLE KEYS */;
INSERT INTO `goods` VALUES (2,33,'Лук репчатый',0000000000000000000007,'кг','прим'),(3,20,'Петрушка',0000000000000000000020,'кг',''),(4,12,'Сливы',0000000000000000000050,'кг',''),(5,34,'Редис',0000000000000000000100,'кг',''),(6,1,'Буряк',0000000000000000000050,'кг',''),(7,2,'Морковь',0000000000000000000200,'кг',''),(9,3,'Огурцы',0000000000000000000200,'кг',NULL),(10,4,'Картошка',0000000000000000000100,'кг',NULL),(11,5,'Лук репчатый',0000000000000000000100,'кг',NULL),(12,6,'Чеснок',0000000000000000000100,'кг',NULL),(13,7,'Бананы',0000000000000000000070,'кг',NULL),(14,8,'Капуста цветная',0000000000000000000240,'кг',NULL),(15,9,'Капуста ранняя',0000000000000000000240,'кг',NULL),(16,10,'Капуста пекинская',0000000000000000000080,'кг',NULL),(17,11,'Дыня',0000000000000000000135,'кг',NULL),(18,13,'Арбуз',0000000000000000000135,'кг',NULL),(19,14,'Персик',0000000000000000000135,'кг',NULL),(20,15,'Киви',0000000000000000000135,'кг',NULL),(21,16,'Авокадо',0000000000000000000075,'кг',NULL),(22,17,'Ананас',0000000000000000000090,'кг',NULL),(23,19,'Укроп',0000000000000000000010,'кг',NULL),(25,18,'Горох',0000000000000000000010,'кг',NULL),(26,21,'Фасоль',0000000000000000000010,'кг',NULL),(27,22,'Персики',0000000000000000000010,'кг',NULL),(28,23,'Лук зеленый',0000000000000000000015,'кг',NULL),(29,24,'Апельсины',0000000000000000000015,'кг',NULL),(30,25,'Мандарины',0000000000000000000015,'кг',NULL),(31,26,'Кабачок',0000000000000000000015,'кг',NULL),(33,27,'Тыква',0000000000000000000015,'кг',NULL),(34,35,'Груши',0000000000000000000015,'кг',NULL),(35,36,'Яблоки',0000000000000000000015,'кг',NULL),(36,37,'Инжир',0000000000000000000015,'кг',NULL);
/*!40000 ALTER TABLE `goods` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_user_type` bigint(20) NOT NULL,
  `login` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_user_type_idx` (`id_user_type`),
  CONSTRAINT `id_user_type` FOREIGN KEY (`id_user_type`) REFERENCES `user_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,4,'a1@gmail.com','1','Сергей'),(2,3,'a2@gmail.com','2','Александр'),(16,2,'a3@gmail.com','3','Николай'),(17,3,'a@gmail.com','1','a@gmail.com'),(18,3,'a@gmail.com','1','a@gmail.com'),(19,3,'a@gmail.com','1','a@gmail.com'),(20,3,'a@gmail.com','1','a@gmail.com'),(21,3,'a@gmail.com','1','a@gmail.com'),(22,3,'f2@gmail.com','f2','Sergeo'),(23,3,'f3@gmail.com','f3','f3'),(24,3,'aa@gmail.com','aa@gmail.com','aa@gmail.com');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_type`
--

DROP TABLE IF EXISTS `user_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL,
  `description` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
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

-- Dump completed on 2019-09-22 23:16:46
