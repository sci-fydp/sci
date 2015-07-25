# --- !Ups

DROP TABLE IF EXISTS `address`;

DROP TABLE IF EXISTS `city`;

DROP TABLE IF EXISTS `country`;

DROP TABLE IF EXISTS `holiday`;

DROP TABLE IF EXISTS `inventory_category`;

DROP TABLE IF EXISTS `item`;

DROP TABLE IF EXISTS `location_holiday`;

DROP TABLE IF EXISTS `location_item`;

DROP TABLE IF EXISTS `state_province`;

DROP TABLE IF EXISTS `store`;

DROP TABLE IF EXISTS `store_location`;

DROP TABLE IF EXISTS `store_location_distance`;

DROP TABLE IF EXISTS `user`;

DROP TABLE IF EXISTS `user_shopping_list`;

DROP TABLE IF EXISTS `user_shopping_list_item`;



# Dump of table address
# ------------------------------------------------------------

CREATE TABLE `address` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `city_id` int(11) unsigned NOT NULL,
  `postal_code` varchar(11) DEFAULT NULL,
  `state_province_id` int(11) unsigned NOT NULL,
  `country_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table city
# ------------------------------------------------------------

CREATE TABLE `city` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table country
# ------------------------------------------------------------

CREATE TABLE `country` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table holiday
# ------------------------------------------------------------

CREATE TABLE `holiday` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(11) DEFAULT NULL,
  `month_of_year` int(11) DEFAULT NULL,
  `day_of_month` int(11) DEFAULT NULL,
  `day_of_week` int(11) DEFAULT NULL,
  `iteration_of_week` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table inventory_category
# ------------------------------------------------------------

CREATE TABLE `inventory_category` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table item
# ------------------------------------------------------------

CREATE TABLE `item` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  `description` varchar(255) DEFAULT NULL,
  `category_id` int(11) unsigned NOT NULL,
  `creation_date` datetime DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `item_category` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table location_holiday
# ------------------------------------------------------------

CREATE TABLE `location_holiday` (
  `location_id` int(11) unsigned NOT NULL,
  `holiday_id` int(11) unsigned NOT NULL,
  KEY `location` (`location_id`),
  KEY `holiday` (`holiday_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table location_item
# ------------------------------------------------------------

CREATE TABLE `location_item` (
  `location_id` int(11) unsigned NOT NULL,
  `item_id` int(11) unsigned NOT NULL,
  `price` float DEFAULT NULL,
  `stock_count` int(11) DEFAULT NULL,
  KEY `item` (`item_id`),
  KEY `location` (`location_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table state_province
# ------------------------------------------------------------

CREATE TABLE `state_province` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table store
# ------------------------------------------------------------

CREATE TABLE `store` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  `inventory_category_id` int(11) unsigned NOT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `creation_date` datetime DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `store_category` (`inventory_category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table store_location
# ------------------------------------------------------------

CREATE TABLE `store_location` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `store_id` int(11) unsigned NOT NULL,
  `address_id` int(11) unsigned NOT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `sunday_begin_time` datetime DEFAULT NULL,
  `sunday_end_time` datetime DEFAULT NULL,
  `monday_begin_time` datetime DEFAULT NULL,
  `monday_end_time` datetime DEFAULT NULL,
  `tuesday_begin_time` datetime DEFAULT NULL,
  `tuesday_end_time` datetime DEFAULT NULL,
  `wednesday_begin_time` datetime DEFAULT NULL,
  `wednesday_end_time` datetime DEFAULT NULL,
  `thursday_begin_time` datetime DEFAULT NULL,
  `thursday_end_time` datetime DEFAULT NULL,
  `friday_begin_time` datetime DEFAULT NULL,
  `friday_end_time` datetime DEFAULT NULL,
  `saturday_begin_time` datetime DEFAULT NULL,
  `saturday_end_time` datetime DEFAULT NULL,
  `creation_date` datetime DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `store_location_store` (`store_id`),
  KEY `store_location_address` (`address_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table store_location_distance
# ------------------------------------------------------------

CREATE TABLE `store_location_distance` (
  `store_location_a` int(11) unsigned NOT NULL,
  `store_location_b` int(11) unsigned NOT NULL,
  `distance_m` float NOT NULL,
  KEY `store_location_b` (`store_location_b`),
  KEY `store_location_a` (`store_location_a`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table user
# ------------------------------------------------------------

CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `email` varchar(11) NOT NULL DEFAULT '',
  `hashed_password` varchar(255) NOT NULL DEFAULT '',
  `address_id` int(11) unsigned DEFAULT NULL,
  `store_id_avoid_list` varchar(255) DEFAULT NULL,
  `verified` tinyint(1) NOT NULL DEFAULT '0',
  `creation_date` datetime DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  `session_str` varchar(255) DEFAULT NULL,
  `udid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_address` (`address_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table user_shopping_list
# ------------------------------------------------------------

CREATE TABLE `user_shopping_list` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `creation_date` datetime DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table user_shopping_list_item
# ------------------------------------------------------------

CREATE TABLE `user_shopping_list_item` (
  `shopping_list_id` int(11) NOT NULL,
  `item_id` int(11) DEFAULT NULL,
  `location_id` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `price` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


# --- !Downs

