/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 8.0.22 : Database - musicdb
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`musicdb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `musicdb`;

/*Table structure for table `album` */

DROP TABLE IF EXISTS `album`;

CREATE TABLE `album` (
  `id` bigint NOT NULL,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `duration` double NOT NULL,
  `numberOfSongs` int NOT NULL,
  `userId` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  CONSTRAINT `album_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `album` */

insert  into `album`(`id`,`name`,`duration`,`numberOfSongs`,`userId`) values 
(1,'The Eminem Show',13.97,3,2),
(2,'S A R S 1',10.82,3,1),
(3,'Appetite for destruction',12.99,3,2);

/*Table structure for table `country` */

DROP TABLE IF EXISTS `country`;

CREATE TABLE `country` (
  `id` bigint NOT NULL,
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `country` */

insert  into `country`(`id`,`name`) values 
(1,'Serbia'),
(2,'Russia'),
(3,'USA'),
(4,'UK'),
(5,'Germany'),
(6,'China');

/*Table structure for table `genre` */

DROP TABLE IF EXISTS `genre`;

CREATE TABLE `genre` (
  `id` bigint NOT NULL,
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `genre` */

insert  into `genre`(`id`,`name`) values 
(1,'Hiphop'),
(2,'Rock'),
(3,'House'),
(4,'Jazz'),
(5,'Classic');

/*Table structure for table `performer` */

DROP TABLE IF EXISTS `performer`;

CREATE TABLE `performer` (
  `id` bigint NOT NULL,
  `countryId` bigint NOT NULL,
  `userId` bigint NOT NULL,
  `firstName` varchar(30) DEFAULT NULL,
  `lastName` varchar(30) DEFAULT NULL,
  `artistName` varchar(30) DEFAULT NULL,
  `bandName` varchar(30) DEFAULT NULL,
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `countryId` (`countryId`),
  KEY `userId` (`userId`),
  CONSTRAINT `performer_ibfk_1` FOREIGN KEY (`countryId`) REFERENCES `country` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `performer_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `performer` */

insert  into `performer`(`id`,`countryId`,`userId`,`firstName`,`lastName`,`artistName`,`bandName`,`type`) values 
(1,3,1,NULL,NULL,NULL,'Guns and roses','TYPE_BAND'),
(2,1,2,NULL,NULL,NULL,'SARS','TYPE_BAND'),
(3,3,1,'Marshall','Mathers','Eminem',NULL,'TYPE_SINGER'),
(4,3,1,'Andre','Romelle','Dr Dre',NULL,'TYPE_SINGER'),
(5,3,2,'Nathaniel','Dwayne','Nate Dogg',NULL,'TYPE_SINGER'),
(6,1,1,'Đorđe','Miljenović','Wikluh Sky',NULL,'TYPE_SINGER');

/*Table structure for table `performer_song` */

DROP TABLE IF EXISTS `performer_song`;

CREATE TABLE `performer_song` (
  `PerformerID` bigint NOT NULL,
  `SongID` bigint NOT NULL,
  PRIMARY KEY (`PerformerID`,`SongID`),
  KEY `performer_song_ibfk_2` (`SongID`),
  CONSTRAINT `performer_song_ibfk_1` FOREIGN KEY (`PerformerID`) REFERENCES `performer` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `performer_song_ibfk_2` FOREIGN KEY (`SongID`) REFERENCES `song` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `performer_song` */

insert  into `performer_song`(`PerformerID`,`SongID`) values 
(3,1),
(3,2),
(4,2),
(3,3),
(5,3),
(2,4),
(6,4),
(2,5),
(2,6),
(1,7),
(1,8),
(1,9);

/*Table structure for table `song` */

DROP TABLE IF EXISTS `song`;

CREATE TABLE `song` (
  `id` bigint NOT NULL,
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `duration` double NOT NULL,
  `genreId` bigint NOT NULL,
  `userId` bigint NOT NULL,
  `albumId` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `genreId` (`genreId`),
  KEY `userId` (`userId`),
  KEY `song_ibfk_3` (`albumId`),
  CONSTRAINT `song_ibfk_1` FOREIGN KEY (`genreId`) REFERENCES `genre` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `song_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `song_ibfk_3` FOREIGN KEY (`albumId`) REFERENCES `album` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `song` */

insert  into `song`(`id`,`name`,`duration`,`genreId`,`userId`,`albumId`) values 
(1,'Without me',4.5,1,1,1),
(2,'Say What You Say',4.9,1,1,1),
(3,'Till I Collapse',4.57,1,2,1),
(4,'Budim se',3.39,2,1,2),
(5,'Buđav lebac',3.26,2,1,2),
(6,'Debeli lad',4.17,2,1,2),
(7,'Nightrain',4.33,2,2,3),
(8,'Sweet child of mine',4.9,2,2,3),
(9,'Paradise city',3.76,2,2,3);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint NOT NULL,
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `user` */

insert  into `user`(`id`,`username`,`password`) values 
(1,'luka','luka'),
(2,'aleksa','aleksa');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
