# ************************************************************
# Sequel Pro SQL dump
# Version 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 127.0.0.1 (MySQL 5.6.25)
# Database: store_route_db
# Generation Time: 2015-06-02 17:21:40 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table address
# ------------------------------------------------------------

DROP TABLE IF EXISTS `address`;

CREATE TABLE `address` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `city_id` int(11) unsigned NOT NULL,
  `postal_code` int(11) DEFAULT NULL,
  `state_province_id` int(11) unsigned NOT NULL,
  `country_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `address_city` (`city_id`),
  KEY `address_state_province` (`state_province_id`),
  KEY `address_country` (`country_id`),
  CONSTRAINT `address_city` FOREIGN KEY (`city_id`) REFERENCES `country` (`id`),
  CONSTRAINT `address_country` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`),
  CONSTRAINT `address_state_province` FOREIGN KEY (`state_province_id`) REFERENCES `state_province` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table city
# ------------------------------------------------------------

DROP TABLE IF EXISTS `city`;

CREATE TABLE `city` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table country
# ------------------------------------------------------------

DROP TABLE IF EXISTS `country`;

CREATE TABLE `country` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table holiday
# ------------------------------------------------------------

DROP TABLE IF EXISTS `holiday`;

CREATE TABLE `holiday` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` int(11) DEFAULT NULL,
  `month_of_year` int(11) DEFAULT NULL,
  `day_of_month` int(11) DEFAULT NULL,
  `day_of_week` int(11) DEFAULT NULL,
  `iteration_of_week` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table inventory_category
# ------------------------------------------------------------

DROP TABLE IF EXISTS `inventory_category`;

CREATE TABLE `inventory_category` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table item
# ------------------------------------------------------------

DROP TABLE IF EXISTS `item`;

CREATE TABLE `item` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  `category_id` int(11) unsigned NOT NULL,
  `creation_date` timestamp NULL DEFAULT NULL,
  `modify_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `item_category` (`category_id`),
  CONSTRAINT `item_category` FOREIGN KEY (`category_id`) REFERENCES `inventory_category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table location_closed_list
# ------------------------------------------------------------

DROP TABLE IF EXISTS `location_closed_list`;

CREATE TABLE `location_closed_list` (
  `location_id` int(11) unsigned NOT NULL,
  `holiday_id` int(11) unsigned NOT NULL,
  KEY `location` (`location_id`),
  KEY `holiday` (`holiday_id`),
  CONSTRAINT `holiday` FOREIGN KEY (`holiday_id`) REFERENCES `holiday` (`id`),
  CONSTRAINT `location` FOREIGN KEY (`location_id`) REFERENCES `store_location` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table location_item
# ------------------------------------------------------------

DROP TABLE IF EXISTS `location_item`;

CREATE TABLE `location_item` (
  `location_id` int(11) unsigned NOT NULL,
  `item_id` int(11) unsigned NOT NULL,
  `price` float DEFAULT NULL,
  `stock_count` int(11) DEFAULT NULL,
  KEY `item` (`item_id`),
  KEY `location` (`location_id`),
  CONSTRAINT `item` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table state_province
# ------------------------------------------------------------

DROP TABLE IF EXISTS `state_province`;

CREATE TABLE `state_province` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table store
# ------------------------------------------------------------

DROP TABLE IF EXISTS `store`;

CREATE TABLE `store` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  `inventory_category_id` int(11) unsigned NOT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `creation_date` timestamp NULL DEFAULT NULL,
  `modify_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `store_category` (`inventory_category_id`),
  CONSTRAINT `store_category` FOREIGN KEY (`inventory_category_id`) REFERENCES `inventory_category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table store_location
# ------------------------------------------------------------

DROP TABLE IF EXISTS `store_location`;

CREATE TABLE `store_location` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `store_id` int(11) unsigned NOT NULL,
  `address_id` int(11) unsigned NOT NULL,
  `sunday_begin_time` int(11) DEFAULT NULL,
  `sunday_end_time` int(11) DEFAULT NULL,
  `monday_begin_time` int(11) DEFAULT NULL,
  `monday_end_time` int(11) DEFAULT NULL,
  `tuesday_begin_time` int(11) DEFAULT NULL,
  `tuesday_end_time` int(11) DEFAULT NULL,
  `wednesday_begin_time` int(11) DEFAULT NULL,
  `wednesday_end_time` int(11) DEFAULT NULL,
  `thursday_begin_time` int(11) DEFAULT NULL,
  `thursday_end_time` int(11) DEFAULT NULL,
  `friday_begin_time` int(11) DEFAULT NULL,
  `friday_end_time` int(11) DEFAULT NULL,
  `saturday_begin_time` int(11) DEFAULT NULL,
  `saturday_end_time` int(11) DEFAULT NULL,
  `creation_date` timestamp NULL DEFAULT NULL,
  `modify_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `store_location_store` (`store_id`),
  KEY `store_location_address` (`address_id`),
  CONSTRAINT `store_location_address` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`),
  CONSTRAINT `store_location_store` FOREIGN KEY (`store_id`) REFERENCES `store` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `email` varchar(11) NOT NULL DEFAULT '',
  `hashed_password` varchar(11) NOT NULL DEFAULT '',
  `address_id` int(11) unsigned NOT NULL,
  `store_id_avoid_list` varchar(255) DEFAULT NULL,
  `verified` tinyint(1) NOT NULL DEFAULT '0',
  `creation_date` timestamp NULL DEFAULT NULL,
  `modify_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_address` (`address_id`),
  CONSTRAINT `user_address` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
