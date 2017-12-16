/*
Navicat MySQL Data Transfer

Source Server         : pretty
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : pretty_girl

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-12-16 20:19:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for folder_info
-- ----------------------------
DROP TABLE IF EXISTS `folder_info`;
CREATE TABLE `folder_info` (
  `folderId` int(64) NOT NULL AUTO_INCREMENT,
  `folderName` varchar(32) NOT NULL,
  PRIMARY KEY (`folderId`)
) ENGINE=InnoDB AUTO_INCREMENT=1268 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for girl_category_info
-- ----------------------------
DROP TABLE IF EXISTS `girl_category_info`;
CREATE TABLE `girl_category_info` (
  `categoryId` int(8) NOT NULL AUTO_INCREMENT,
  `title` varchar(64) NOT NULL,
  `icon` varchar(64) NOT NULL,
  `description` varchar(32) DEFAULT NULL,
  `girlType` int(8) NOT NULL,
  `viewNumber` int(32) DEFAULT NULL,
  `praiseNumber` int(32) DEFAULT NULL,
  `folderName` varchar(32) NOT NULL,
  PRIMARY KEY (`categoryId`)
) ENGINE=InnoDB AUTO_INCREMENT=561 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for girl_image_info
-- ----------------------------
DROP TABLE IF EXISTS `girl_image_info`;
CREATE TABLE `girl_image_info` (
  `id` int(64) NOT NULL AUTO_INCREMENT,
  `title` varchar(64) NOT NULL,
  `imageUrl` varchar(64) NOT NULL,
  `girlType` int(8) NOT NULL,
  `categoryId` int(64) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5385 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for girl_type
-- ----------------------------
DROP TABLE IF EXISTS `girl_type`;
CREATE TABLE `girl_type` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `girlType` int(8) NOT NULL,
  `categoryName` varchar(16) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
SET FOREIGN_KEY_CHECKS=1;
