/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : localhost:3306
 Source Schema         : hs_bus

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 15/08/2018 16:05:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for auth_token
-- ----------------------------
DROP TABLE IF EXISTS `auth_token`;
CREATE TABLE `auth_token` (
  `id` varchar(255) NOT NULL,
  `update_time` bigint(20) NOT NULL,
  `create_time` bigint(20) NOT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for city
-- ----------------------------
DROP TABLE IF EXISTS `city`;
CREATE TABLE `city` (
  `city_id` varchar(50) NOT NULL,
  `city_name` varchar(50) NOT NULL,
  `update_time` bigint(20) NOT NULL,
  `create_time` bigint(20) NOT NULL,
  PRIMARY KEY (`city_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for county
-- ----------------------------
DROP TABLE IF EXISTS `county`;
CREATE TABLE `county` (
  `county_id` varchar(50) NOT NULL,
  `county_name` varchar(50) NOT NULL,
  `update_time` bigint(20) NOT NULL,
  `create_time` bigint(20) NOT NULL,
  PRIMARY KEY (`county_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for dictionary
-- ----------------------------
DROP TABLE IF EXISTS `dictionary`;
CREATE TABLE `dictionary` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `label` varchar(255) NOT NULL,
  `value` varchar(255) NOT NULL,
  `category_id` int(10) unsigned NOT NULL,
  `update_time` bigint(20) NOT NULL,
  `create_time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_dictionary_dictionary_category` (`category_id`),
  CONSTRAINT `fk_dictionary_dictionary_category` FOREIGN KEY (`category_id`) REFERENCES `dictionary_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dictionary
-- ----------------------------
BEGIN;
INSERT INTO `dictionary` VALUES (1, '订单', 'order', 1, 1526540519000, 1526540519000);
INSERT INTO `dictionary` VALUES (2, 'order', 'order', 2, 1526540519000, 1526540519000);
COMMIT;

-- ----------------------------
-- Table structure for dictionary_category
-- ----------------------------
DROP TABLE IF EXISTS `dictionary_category`;
CREATE TABLE `dictionary_category` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `label` varchar(255) NOT NULL,
  `update_time` bigint(20) NOT NULL,
  `create_time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `index_dictionary_category_label` (`label`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dictionary_category
-- ----------------------------
BEGIN;
INSERT INTO `dictionary_category` VALUES (1, '中文', 1526540519000, 1526540519000);
INSERT INTO `dictionary_category` VALUES (2, '英文', 1526540519000, 1526540519000);
COMMIT;

-- ----------------------------
-- Table structure for doctor
-- ----------------------------
DROP TABLE IF EXISTS `doctor`;
CREATE TABLE `doctor` (
  `doctor_id` varchar(255) NOT NULL,
  `doctor_name` varchar(50) NOT NULL,
  `hospital_name` varchar(50) DEFAULT NULL,
  `hospital_address` varchar(100) DEFAULT NULL,
  `update_time` bigint(20) unsigned NOT NULL,
  `create_time` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`doctor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for error_log
-- ----------------------------
DROP TABLE IF EXISTS `error_log`;
CREATE TABLE `error_log` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `type` varchar(50) NOT NULL,
  `error` varchar(255) NOT NULL,
  `detail` text,
  `status` tinyint(1) DEFAULT '0',
  `update_time` bigint(20) NOT NULL,
  `create_time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for gene_daily_detail
-- ----------------------------
DROP TABLE IF EXISTS `gene_daily_detail`;
CREATE TABLE `gene_daily_detail` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `tag` varchar(50) NOT NULL,
  `message_id` varchar(100) NOT NULL,
  `record_id` int(10) NOT NULL,
  `day_str` varchar(50) DEFAULT NULL,
  `month_str` varchar(50) DEFAULT NULL,
  `year_str` varchar(50) DEFAULT NULL,
  `quarter_str` varchar(50) DEFAULT NULL,
  `half_year_str` varchar(50) DEFAULT NULL,
  `event` varchar(50) NOT NULL,
  `event_time` bigint(20) NOT NULL,
  `change_count` int(5) DEFAULT '1',
  `order_income` bigint(20) DEFAULT '0',
  `cash_income` bigint(20) DEFAULT '0',
  `finance_confirm_income` bigint(20) DEFAULT '0',
  `testing_item_cost` bigint(20) DEFAULT '0',
  `testing_item_confirm_cost` bigint(20) DEFAULT '0',
  `integral_cost` bigint(20) DEFAULT '0',
  `integral` bigint(20) DEFAULT '0',
  `dcw_integral` bigint(20) DEFAULT '0',
  `dcw_integral_cost` bigint(20) DEFAULT '0',
  `dcw_integral_raw` bigint(20) DEFAULT '0',
  `dcw_integral_raw_cost` bigint(20) DEFAULT '0',
  `empiri_value` bigint(20) DEFAULT '0',
  `order_id` varchar(50) DEFAULT NULL,
  `order_number` varchar(50) DEFAULT NULL,
  `order_price` bigint(20) DEFAULT '0',
  `order_creator_id` varchar(50) DEFAULT NULL,
  `order_creator_name` varchar(50) DEFAULT NULL,
  `order_create_time` bigint(20) DEFAULT '0',
  `order_pay_time` bigint(20) DEFAULT '0',
  `sample_confirm_time` bigint(20) DEFAULT '0',
  `testing_report_upload_time` bigint(20) DEFAULT '0',
  `integral_grant_time` bigint(20) DEFAULT '0',
  `order_invoice_time` bigint(20) DEFAULT '0',
  `order_cancel_time` bigint(20) DEFAULT '0',
  `order_refund_time` bigint(20) DEFAULT '0',
  `order_update_time` bigint(20) DEFAULT '0',
  `patient_name` varchar(50) DEFAULT NULL,
  `patient_gender` varchar(20) DEFAULT NULL,
  `patient_id_card` varchar(50) DEFAULT NULL,
  `patient_age` int(4) DEFAULT '0',
  `patient_address` varchar(50) DEFAULT NULL,
  `patient_clinical_diagnosis` varchar(300) DEFAULT NULL,
  `local_staff_id` varchar(50) DEFAULT NULL,
  `local_staff_job_number` varchar(50) DEFAULT NULL,
  `local_staff_name` varchar(50) DEFAULT NULL,
  `local_staff_region_id` varchar(50) DEFAULT NULL,
  `local_staff_region_name` varchar(50) DEFAULT NULL,
  `local_staff_province_id` varchar(50) DEFAULT NULL,
  `local_staff_province_name` varchar(50) DEFAULT NULL,
  `local_staff_city_id` varchar(50) DEFAULT NULL,
  `local_staff_city_name` varchar(50) DEFAULT NULL,
  `local_staff_county_id` varchar(50) DEFAULT NULL,
  `local_staff_county_name` varchar(50) DEFAULT NULL,
  `doctor_id` varchar(50) DEFAULT NULL,
  `doctor_name` varchar(50) DEFAULT NULL,
  `hospital_name` varchar(50) DEFAULT NULL,
  `hospital_address` varchar(255) DEFAULT NULL,
  `dcw_id` varchar(50) DEFAULT NULL,
  `order_place_cost` bigint(20) DEFAULT '0',
  `order_cancel_count` bigint(20) DEFAULT '0',
  `order_place_count` bigint(20) DEFAULT '0',
  `order_cancel_cost` bigint(20) DEFAULT '0',
  `update_time` bigint(20) unsigned NOT NULL,
  `create_time` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_gene_daily_detail_local_staff` (`local_staff_id`),
  KEY `fk_gene_daily_detail_region` (`local_staff_region_id`),
  KEY `fk_gene_daily_detail_province` (`local_staff_province_id`),
  KEY `fk_gene_daily_detail_city` (`local_staff_city_id`),
  KEY `fk_gene_daily_detail_doctor` (`doctor_id`),
  KEY `index_gene_daily_detail_order_id` (`order_id`),
  KEY `index_gene_daily_detail_day_str` (`day_str`),
  KEY `index_gene_daily_detail_month_str` (`month_str`),
  KEY `index_gene_daily_detail_year_str` (`year_str`),
  KEY `index_gene_daily_detail_local_staff_region_id` (`local_staff_region_id`),
  KEY `index_gene_daily_detail_local_staff_province_id` (`local_staff_province_id`),
  KEY `index_gene_daily_detail_event_time` (`event_time`),
  KEY `index_gene_daily_detail_local_staff_name` (`local_staff_name`),
  KEY `index_gene_daily_detail_order_create_time` (`order_create_time`),
  CONSTRAINT `fk_gene_daily_detail_city` FOREIGN KEY (`local_staff_city_id`) REFERENCES `city` (`city_id`),
  CONSTRAINT `fk_gene_daily_detail_doctor` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`doctor_id`),
  CONSTRAINT `fk_gene_daily_detail_local_staff` FOREIGN KEY (`local_staff_id`) REFERENCES `local_staff` (`local_staff_id`),
  CONSTRAINT `fk_gene_daily_detail_province` FOREIGN KEY (`local_staff_province_id`) REFERENCES `province` (`province_id`),
  CONSTRAINT `fk_gene_daily_detail_region` FOREIGN KEY (`local_staff_region_id`) REFERENCES `region` (`region_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for gene_detail
-- ----------------------------
DROP TABLE IF EXISTS `gene_detail`;
CREATE TABLE `gene_detail` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `tag` varchar(50) NOT NULL,
  `message_id` varchar(100) NOT NULL,
  `record_id` int(10) NOT NULL,
  `day_str` varchar(50) DEFAULT NULL,
  `month_str` varchar(50) DEFAULT NULL,
  `year_str` varchar(50) DEFAULT NULL,
  `quarter_str` varchar(50) DEFAULT NULL,
  `half_year_str` varchar(50) DEFAULT NULL,
  `event` varchar(50) NOT NULL,
  `event_time` bigint(20) NOT NULL,
  `change_count` int(5) DEFAULT '1',
  `order_income` bigint(20) DEFAULT '0',
  `cash_income` bigint(20) DEFAULT '0',
  `finance_confirm_income` bigint(20) DEFAULT '0',
  `testing_item_cost` bigint(20) DEFAULT '0',
  `testing_item_confirm_cost` bigint(20) DEFAULT '0',
  `integral_cost` bigint(20) DEFAULT '0',
  `integral` bigint(20) DEFAULT '0',
  `dcw_integral` bigint(20) DEFAULT '0',
  `dcw_integral_cost` bigint(20) DEFAULT '0',
  `dcw_integral_raw` bigint(20) DEFAULT '0',
  `dcw_integral_raw_cost` bigint(20) DEFAULT '0',
  `empiri_value` bigint(20) DEFAULT '0',
  `order_id` varchar(50) DEFAULT NULL,
  `order_number` varchar(50) DEFAULT NULL,
  `order_price` bigint(20) DEFAULT '0',
  `order_creator_id` varchar(50) DEFAULT NULL,
  `order_creator_name` varchar(50) DEFAULT NULL,
  `order_create_time` bigint(20) DEFAULT '0',
  `order_pay_time` bigint(20) DEFAULT '0',
  `sample_confirm_time` bigint(20) DEFAULT '0',
  `testing_report_upload_time` bigint(20) DEFAULT '0',
  `integral_grant_time` bigint(20) DEFAULT '0',
  `order_invoice_time` bigint(20) DEFAULT '0',
  `order_cancel_time` bigint(20) DEFAULT '0',
  `order_refund_time` bigint(20) DEFAULT '0',
  `order_update_time` bigint(20) DEFAULT '0',
  `patient_name` varchar(50) DEFAULT NULL,
  `patient_gender` varchar(20) DEFAULT NULL,
  `patient_id_card` varchar(50) DEFAULT NULL,
  `patient_age` int(4) DEFAULT '0',
  `patient_address` varchar(50) DEFAULT NULL,
  `patient_clinical_diagnosis` varchar(300) DEFAULT NULL,
  `local_staff_id` varchar(50) DEFAULT NULL,
  `local_staff_job_number` varchar(50) DEFAULT NULL,
  `local_staff_name` varchar(50) DEFAULT NULL,
  `local_staff_region_id` varchar(50) DEFAULT NULL,
  `local_staff_region_name` varchar(50) DEFAULT NULL,
  `local_staff_province_id` varchar(50) DEFAULT NULL,
  `local_staff_province_name` varchar(50) DEFAULT NULL,
  `local_staff_city_id` varchar(50) DEFAULT NULL,
  `local_staff_city_name` varchar(50) DEFAULT NULL,
  `local_staff_county_id` varchar(50) DEFAULT NULL,
  `local_staff_county_name` varchar(50) DEFAULT NULL,
  `doctor_id` varchar(50) DEFAULT NULL,
  `doctor_name` varchar(50) DEFAULT NULL,
  `hospital_name` varchar(50) DEFAULT NULL,
  `hospital_address` varchar(255) DEFAULT NULL,
  `dcw_id` varchar(50) DEFAULT NULL,
  `order_place_cost` bigint(20) DEFAULT '0',
  `order_cancel_count` bigint(20) DEFAULT '0',
  `order_place_count` bigint(20) DEFAULT '0',
  `order_cancel_cost` bigint(20) DEFAULT '0',
  `update_time` bigint(20) unsigned NOT NULL,
  `create_time` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_gene_detail_local_staff` (`local_staff_id`),
  KEY `fk_gene_detail_region` (`local_staff_region_id`),
  KEY `fk_gene_detail_city` (`local_staff_city_id`),
  KEY `fk_gene_detail_province` (`local_staff_province_id`),
  KEY `fk_gene_detail_doctor` (`doctor_id`),
  KEY `index_gene_detail_order_id` (`order_id`),
  KEY `index_gene_detail_order_create_time` (`order_create_time`),
  KEY `index_gene_detail_integral_cost` (`integral_cost`),
  KEY `index_gene_detail_month_str` (`month_str`),
  CONSTRAINT `fk_gene_detail_city` FOREIGN KEY (`local_staff_city_id`) REFERENCES `city` (`city_id`),
  CONSTRAINT `fk_gene_detail_doctor` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`doctor_id`),
  CONSTRAINT `fk_gene_detail_local_staff` FOREIGN KEY (`local_staff_id`) REFERENCES `local_staff` (`local_staff_id`),
  CONSTRAINT `fk_gene_detail_province` FOREIGN KEY (`local_staff_province_id`) REFERENCES `province` (`province_id`),
  CONSTRAINT `fk_gene_detail_region` FOREIGN KEY (`local_staff_region_id`) REFERENCES `region` (`region_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for gene_half_yearly_detail
-- ----------------------------
DROP TABLE IF EXISTS `gene_half_yearly_detail`;
CREATE TABLE `gene_half_yearly_detail` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `tag` varchar(50) NOT NULL,
  `message_id` varchar(100) NOT NULL,
  `record_id` int(10) NOT NULL,
  `day_str` varchar(50) DEFAULT NULL,
  `month_str` varchar(50) DEFAULT NULL,
  `year_str` varchar(50) DEFAULT NULL,
  `quarter_str` varchar(50) DEFAULT NULL,
  `half_year_str` varchar(50) DEFAULT NULL,
  `event` varchar(50) NOT NULL,
  `event_time` bigint(20) NOT NULL,
  `change_count` int(5) DEFAULT '1',
  `order_income` bigint(20) DEFAULT '0',
  `cash_income` bigint(20) DEFAULT '0',
  `finance_confirm_income` bigint(20) DEFAULT '0',
  `testing_item_cost` bigint(20) DEFAULT '0',
  `testing_item_confirm_cost` bigint(20) DEFAULT '0',
  `integral_cost` bigint(20) DEFAULT '0',
  `integral` bigint(20) DEFAULT '0',
  `dcw_integral` bigint(20) DEFAULT '0',
  `dcw_integral_cost` bigint(20) DEFAULT '0',
  `dcw_integral_raw` bigint(20) DEFAULT '0',
  `dcw_integral_raw_cost` bigint(20) DEFAULT '0',
  `empiri_value` bigint(20) DEFAULT '0',
  `order_id` varchar(50) DEFAULT NULL,
  `order_number` varchar(50) DEFAULT NULL,
  `order_price` bigint(20) DEFAULT '0',
  `order_creator_id` varchar(50) DEFAULT NULL,
  `order_creator_name` varchar(50) DEFAULT NULL,
  `order_create_time` bigint(20) DEFAULT '0',
  `order_pay_time` bigint(20) DEFAULT '0',
  `sample_confirm_time` bigint(20) DEFAULT '0',
  `testing_report_upload_time` bigint(20) DEFAULT '0',
  `integral_grant_time` bigint(20) DEFAULT '0',
  `order_invoice_time` bigint(20) DEFAULT '0',
  `order_cancel_time` bigint(20) DEFAULT '0',
  `order_refund_time` bigint(20) DEFAULT '0',
  `order_update_time` bigint(20) DEFAULT '0',
  `patient_name` varchar(50) DEFAULT NULL,
  `patient_gender` varchar(20) DEFAULT NULL,
  `patient_id_card` varchar(50) DEFAULT NULL,
  `patient_age` int(4) DEFAULT '0',
  `patient_address` varchar(50) DEFAULT NULL,
  `patient_clinical_diagnosis` varchar(300) DEFAULT NULL,
  `local_staff_id` varchar(50) DEFAULT NULL,
  `local_staff_job_number` varchar(50) DEFAULT NULL,
  `local_staff_name` varchar(50) DEFAULT NULL,
  `local_staff_region_id` varchar(50) DEFAULT NULL,
  `local_staff_region_name` varchar(50) DEFAULT NULL,
  `local_staff_province_id` varchar(50) DEFAULT NULL,
  `local_staff_province_name` varchar(50) DEFAULT NULL,
  `local_staff_city_id` varchar(50) DEFAULT NULL,
  `local_staff_city_name` varchar(50) DEFAULT NULL,
  `local_staff_county_id` varchar(50) DEFAULT NULL,
  `local_staff_county_name` varchar(50) DEFAULT NULL,
  `doctor_id` varchar(50) DEFAULT NULL,
  `doctor_name` varchar(50) DEFAULT NULL,
  `hospital_name` varchar(50) DEFAULT NULL,
  `hospital_address` varchar(255) DEFAULT NULL,
  `dcw_id` varchar(50) DEFAULT NULL,
  `order_place_cost` bigint(20) DEFAULT '0',
  `order_cancel_count` bigint(20) DEFAULT '0',
  `order_place_count` bigint(20) DEFAULT '0',
  `order_cancel_cost` bigint(20) DEFAULT '0',
  `update_time` bigint(20) unsigned NOT NULL,
  `create_time` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_gene_half_yearly_detail_local_staff` (`local_staff_id`),
  KEY `fk_gene_half_yearly_detail_region` (`local_staff_region_id`),
  KEY `fk_gene_half_yearly_detail_city` (`local_staff_city_id`),
  KEY `fk_gene_half_yearly_detail_province` (`local_staff_province_id`),
  KEY `fk_gene_half_yearly_detail_doctor` (`doctor_id`),
  KEY `index_gene_half_yearly_detail_order_id` (`order_id`),
  KEY `index_gene_half_yearly_detail_half_year_str` (`half_year_str`),
  KEY `index_gene_half_yearly_detail_local_staff_region_id` (`local_staff_region_id`),
  CONSTRAINT `fk_gene_half_yearly_detail_city` FOREIGN KEY (`local_staff_city_id`) REFERENCES `city` (`city_id`),
  CONSTRAINT `fk_gene_half_yearly_detail_doctor` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`doctor_id`),
  CONSTRAINT `fk_gene_half_yearly_detail_local_staff` FOREIGN KEY (`local_staff_id`) REFERENCES `local_staff` (`local_staff_id`),
  CONSTRAINT `fk_gene_half_yearly_detail_province` FOREIGN KEY (`local_staff_province_id`) REFERENCES `province` (`province_id`),
  CONSTRAINT `fk_gene_half_yearly_detail_region` FOREIGN KEY (`local_staff_region_id`) REFERENCES `region` (`region_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for gene_log
-- ----------------------------
DROP TABLE IF EXISTS `gene_log`;
CREATE TABLE `gene_log` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `tag` varchar(255) NOT NULL,
  `event` varchar(255) NOT NULL,
  `event_time` mediumtext NOT NULL,
  `order_id` varchar(255) DEFAULT NULL,
  `message_id` varchar(255) NOT NULL,
  `record_id` int(10) NOT NULL,
  `day_str` varchar(255) NOT NULL,
  `month_str` varchar(255) NOT NULL,
  `year_str` varchar(255) NOT NULL,
  `quarter_str` varchar(255) NOT NULL,
  `half_year_str` varchar(255) NOT NULL,
  `record` longtext NOT NULL,
  `update_time` bigint(20) unsigned NOT NULL,
  `create_time` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `index_gene_log_record_id` (`record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for gene_monthly_detail
-- ----------------------------
DROP TABLE IF EXISTS `gene_monthly_detail`;
CREATE TABLE `gene_monthly_detail` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `tag` varchar(50) NOT NULL,
  `message_id` varchar(100) NOT NULL,
  `record_id` int(10) NOT NULL,
  `day_str` varchar(50) DEFAULT NULL,
  `month_str` varchar(50) DEFAULT NULL,
  `year_str` varchar(50) DEFAULT NULL,
  `quarter_str` varchar(50) DEFAULT NULL,
  `half_year_str` varchar(50) DEFAULT NULL,
  `event` varchar(50) NOT NULL,
  `event_time` bigint(20) NOT NULL,
  `change_count` int(5) DEFAULT '1',
  `order_income` bigint(20) DEFAULT '0',
  `cash_income` bigint(20) DEFAULT '0',
  `finance_confirm_income` bigint(20) DEFAULT '0',
  `testing_item_cost` bigint(20) DEFAULT '0',
  `testing_item_confirm_cost` bigint(20) DEFAULT '0',
  `integral_cost` bigint(20) DEFAULT '0',
  `integral` bigint(20) DEFAULT '0',
  `dcw_integral` bigint(20) DEFAULT '0',
  `dcw_integral_cost` bigint(20) DEFAULT '0',
  `dcw_integral_raw` bigint(20) DEFAULT '0',
  `dcw_integral_raw_cost` bigint(20) DEFAULT '0',
  `empiri_value` bigint(20) DEFAULT '0',
  `order_id` varchar(50) DEFAULT NULL,
  `order_number` varchar(50) DEFAULT NULL,
  `order_price` bigint(20) DEFAULT '0',
  `order_creator_id` varchar(50) DEFAULT NULL,
  `order_creator_name` varchar(50) DEFAULT NULL,
  `order_create_time` bigint(20) DEFAULT '0',
  `order_pay_time` bigint(20) DEFAULT '0',
  `sample_confirm_time` bigint(20) DEFAULT '0',
  `testing_report_upload_time` bigint(20) DEFAULT '0',
  `integral_grant_time` bigint(20) DEFAULT '0',
  `order_invoice_time` bigint(20) DEFAULT '0',
  `order_cancel_time` bigint(20) DEFAULT '0',
  `order_refund_time` bigint(20) DEFAULT '0',
  `order_update_time` bigint(20) DEFAULT '0',
  `patient_name` varchar(50) DEFAULT NULL,
  `patient_gender` varchar(20) DEFAULT NULL,
  `patient_id_card` varchar(50) DEFAULT NULL,
  `patient_age` int(4) DEFAULT '0',
  `patient_address` varchar(50) DEFAULT NULL,
  `patient_clinical_diagnosis` varchar(300) DEFAULT NULL,
  `local_staff_id` varchar(50) DEFAULT NULL,
  `local_staff_job_number` varchar(50) DEFAULT NULL,
  `local_staff_name` varchar(50) DEFAULT NULL,
  `local_staff_region_id` varchar(50) DEFAULT NULL,
  `local_staff_region_name` varchar(50) DEFAULT NULL,
  `local_staff_province_id` varchar(50) DEFAULT NULL,
  `local_staff_province_name` varchar(50) DEFAULT NULL,
  `local_staff_city_id` varchar(50) DEFAULT NULL,
  `local_staff_city_name` varchar(50) DEFAULT NULL,
  `local_staff_county_id` varchar(50) DEFAULT NULL,
  `local_staff_county_name` varchar(50) DEFAULT NULL,
  `doctor_id` varchar(50) DEFAULT NULL,
  `doctor_name` varchar(50) DEFAULT NULL,
  `hospital_name` varchar(50) DEFAULT NULL,
  `hospital_address` varchar(255) DEFAULT NULL,
  `dcw_id` varchar(50) DEFAULT NULL,
  `order_place_cost` bigint(20) DEFAULT '0',
  `order_cancel_count` bigint(20) DEFAULT '0',
  `order_place_count` bigint(20) DEFAULT '0',
  `order_cancel_cost` bigint(20) DEFAULT '0',
  `update_time` bigint(20) unsigned NOT NULL,
  `create_time` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_gene_monthly_detail_local_staff` (`local_staff_id`),
  KEY `fk_gene_monthly_detail_region` (`local_staff_region_id`),
  KEY `fk_gene_monthly_detail_city` (`local_staff_city_id`),
  KEY `fk_gene_monthly_detail_province` (`local_staff_province_id`),
  KEY `fk_gene_monthly_detail_doctor` (`doctor_id`),
  KEY `index_gene_monthly_detail_order_id` (`order_id`),
  KEY `index_gene_monthly_detail_month_str` (`month_str`),
  KEY `index_gene_monthly_detail_half_year_str` (`half_year_str`),
  KEY `index_gene_monthly_detail_year_str` (`year_str`),
  KEY `index_gene_monthly_detail_local_staff_region_id` (`local_staff_region_id`),
  KEY `index_gene_monthly_detail_local_staff_province_id` (`local_staff_province_id`),
  CONSTRAINT `fk_gene_monthly_detail_city` FOREIGN KEY (`local_staff_city_id`) REFERENCES `city` (`city_id`),
  CONSTRAINT `fk_gene_monthly_detail_doctor` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`doctor_id`),
  CONSTRAINT `fk_gene_monthly_detail_local_staff` FOREIGN KEY (`local_staff_id`) REFERENCES `local_staff` (`local_staff_id`),
  CONSTRAINT `fk_gene_monthly_detail_province` FOREIGN KEY (`local_staff_province_id`) REFERENCES `province` (`province_id`),
  CONSTRAINT `fk_gene_monthly_detail_region` FOREIGN KEY (`local_staff_region_id`) REFERENCES `region` (`region_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for gene_quarterly_detail
-- ----------------------------
DROP TABLE IF EXISTS `gene_quarterly_detail`;
CREATE TABLE `gene_quarterly_detail` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `tag` varchar(50) NOT NULL,
  `message_id` varchar(100) NOT NULL,
  `record_id` int(10) NOT NULL,
  `day_str` varchar(50) DEFAULT NULL,
  `month_str` varchar(50) DEFAULT NULL,
  `year_str` varchar(50) DEFAULT NULL,
  `quarter_str` varchar(50) DEFAULT NULL,
  `half_year_str` varchar(50) DEFAULT NULL,
  `event` varchar(50) NOT NULL,
  `event_time` bigint(20) NOT NULL,
  `change_count` int(5) DEFAULT '1',
  `order_income` bigint(20) DEFAULT '0',
  `cash_income` bigint(20) DEFAULT '0',
  `finance_confirm_income` bigint(20) DEFAULT '0',
  `testing_item_cost` bigint(20) DEFAULT '0',
  `testing_item_confirm_cost` bigint(20) DEFAULT '0',
  `integral_cost` bigint(20) DEFAULT '0',
  `integral` bigint(20) DEFAULT '0',
  `dcw_integral` bigint(20) DEFAULT '0',
  `dcw_integral_cost` bigint(20) DEFAULT '0',
  `dcw_integral_raw` bigint(20) DEFAULT '0',
  `dcw_integral_raw_cost` bigint(20) DEFAULT '0',
  `empiri_value` bigint(20) DEFAULT '0',
  `order_id` varchar(50) DEFAULT NULL,
  `order_number` varchar(50) DEFAULT NULL,
  `order_price` bigint(20) DEFAULT '0',
  `order_creator_id` varchar(50) DEFAULT NULL,
  `order_creator_name` varchar(50) DEFAULT NULL,
  `order_create_time` bigint(20) DEFAULT '0',
  `order_pay_time` bigint(20) DEFAULT '0',
  `sample_confirm_time` bigint(20) DEFAULT '0',
  `testing_report_upload_time` bigint(20) DEFAULT '0',
  `integral_grant_time` bigint(20) DEFAULT '0',
  `order_invoice_time` bigint(20) DEFAULT '0',
  `order_cancel_time` bigint(20) DEFAULT '0',
  `order_refund_time` bigint(20) DEFAULT '0',
  `order_update_time` bigint(20) DEFAULT '0',
  `patient_name` varchar(50) DEFAULT NULL,
  `patient_gender` varchar(20) DEFAULT NULL,
  `patient_id_card` varchar(50) DEFAULT NULL,
  `patient_age` int(4) DEFAULT '0',
  `patient_address` varchar(50) DEFAULT NULL,
  `patient_clinical_diagnosis` varchar(300) DEFAULT NULL,
  `local_staff_id` varchar(50) DEFAULT NULL,
  `local_staff_job_number` varchar(50) DEFAULT NULL,
  `local_staff_name` varchar(50) DEFAULT NULL,
  `local_staff_region_id` varchar(50) DEFAULT NULL,
  `local_staff_region_name` varchar(50) DEFAULT NULL,
  `local_staff_province_id` varchar(50) DEFAULT NULL,
  `local_staff_province_name` varchar(50) DEFAULT NULL,
  `local_staff_city_id` varchar(50) DEFAULT NULL,
  `local_staff_city_name` varchar(50) DEFAULT NULL,
  `local_staff_county_id` varchar(50) DEFAULT NULL,
  `local_staff_county_name` varchar(50) DEFAULT NULL,
  `doctor_id` varchar(50) DEFAULT NULL,
  `doctor_name` varchar(50) DEFAULT NULL,
  `hospital_name` varchar(50) DEFAULT NULL,
  `hospital_address` varchar(255) DEFAULT NULL,
  `dcw_id` varchar(50) DEFAULT NULL,
  `order_place_cost` bigint(20) DEFAULT '0',
  `order_cancel_count` bigint(20) DEFAULT '0',
  `order_place_count` bigint(20) DEFAULT '0',
  `order_cancel_cost` bigint(20) DEFAULT '0',
  `update_time` bigint(20) unsigned NOT NULL,
  `create_time` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_gene_quarterly_detail_local_staff` (`local_staff_id`),
  KEY `fk_gene_quarterly_detail_region` (`local_staff_region_id`),
  KEY `fk_gene_quarterly_detail_city` (`local_staff_city_id`),
  KEY `fk_gene_quarterly_detail_province` (`local_staff_province_id`),
  KEY `fk_gene_quarterly_detail_doctor` (`doctor_id`),
  KEY `index_gene_quarterly_detail_order_id` (`order_id`),
  KEY `index_gene_quarterly_detail_quarter_str` (`quarter_str`),
  CONSTRAINT `fk_gene_quarterly_detail_city` FOREIGN KEY (`local_staff_city_id`) REFERENCES `city` (`city_id`),
  CONSTRAINT `fk_gene_quarterly_detail_doctor` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`doctor_id`),
  CONSTRAINT `fk_gene_quarterly_detail_local_staff` FOREIGN KEY (`local_staff_id`) REFERENCES `local_staff` (`local_staff_id`),
  CONSTRAINT `fk_gene_quarterly_detail_province` FOREIGN KEY (`local_staff_province_id`) REFERENCES `province` (`province_id`),
  CONSTRAINT `fk_gene_quarterly_detail_region` FOREIGN KEY (`local_staff_region_id`) REFERENCES `region` (`region_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for gene_yearly_detail
-- ----------------------------
DROP TABLE IF EXISTS `gene_yearly_detail`;
CREATE TABLE `gene_yearly_detail` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `tag` varchar(50) NOT NULL,
  `message_id` varchar(100) NOT NULL,
  `record_id` int(10) NOT NULL,
  `day_str` varchar(50) DEFAULT NULL,
  `month_str` varchar(50) DEFAULT NULL,
  `year_str` varchar(50) DEFAULT NULL,
  `quarter_str` varchar(50) DEFAULT NULL,
  `half_year_str` varchar(50) DEFAULT NULL,
  `event` varchar(50) NOT NULL,
  `event_time` bigint(20) NOT NULL,
  `change_count` int(5) DEFAULT '1',
  `order_income` bigint(20) DEFAULT '0',
  `cash_income` bigint(20) DEFAULT '0',
  `finance_confirm_income` bigint(20) DEFAULT '0',
  `testing_item_cost` bigint(20) DEFAULT '0',
  `testing_item_confirm_cost` bigint(20) DEFAULT '0',
  `integral_cost` bigint(20) DEFAULT '0',
  `integral` bigint(20) DEFAULT '0',
  `dcw_integral` bigint(20) DEFAULT '0',
  `dcw_integral_cost` bigint(20) DEFAULT '0',
  `dcw_integral_raw` bigint(20) DEFAULT '0',
  `dcw_integral_raw_cost` bigint(20) DEFAULT '0',
  `empiri_value` bigint(20) DEFAULT '0',
  `order_id` varchar(50) DEFAULT NULL,
  `order_number` varchar(50) DEFAULT NULL,
  `order_price` bigint(20) DEFAULT '0',
  `order_creator_id` varchar(50) DEFAULT NULL,
  `order_creator_name` varchar(50) DEFAULT NULL,
  `order_create_time` bigint(20) DEFAULT '0',
  `order_pay_time` bigint(20) DEFAULT '0',
  `sample_confirm_time` bigint(20) DEFAULT '0',
  `testing_report_upload_time` bigint(20) DEFAULT '0',
  `integral_grant_time` bigint(20) DEFAULT '0',
  `order_invoice_time` bigint(20) DEFAULT '0',
  `order_cancel_time` bigint(20) DEFAULT '0',
  `order_refund_time` bigint(20) DEFAULT '0',
  `order_update_time` bigint(20) DEFAULT '0',
  `patient_name` varchar(50) DEFAULT NULL,
  `patient_gender` varchar(20) DEFAULT NULL,
  `patient_id_card` varchar(50) DEFAULT NULL,
  `patient_age` int(4) DEFAULT '0',
  `patient_address` varchar(50) DEFAULT NULL,
  `patient_clinical_diagnosis` varchar(300) DEFAULT NULL,
  `local_staff_id` varchar(50) DEFAULT NULL,
  `local_staff_job_number` varchar(50) DEFAULT NULL,
  `local_staff_name` varchar(50) DEFAULT NULL,
  `local_staff_region_id` varchar(50) DEFAULT NULL,
  `local_staff_region_name` varchar(50) DEFAULT NULL,
  `local_staff_province_id` varchar(50) DEFAULT NULL,
  `local_staff_province_name` varchar(50) DEFAULT NULL,
  `local_staff_city_id` varchar(50) DEFAULT NULL,
  `local_staff_city_name` varchar(50) DEFAULT NULL,
  `local_staff_county_id` varchar(50) DEFAULT NULL,
  `local_staff_county_name` varchar(50) DEFAULT NULL,
  `doctor_id` varchar(50) DEFAULT NULL,
  `doctor_name` varchar(50) DEFAULT NULL,
  `hospital_name` varchar(50) DEFAULT NULL,
  `hospital_address` varchar(255) DEFAULT NULL,
  `dcw_id` varchar(50) DEFAULT NULL,
  `order_place_cost` bigint(20) DEFAULT '0',
  `order_cancel_count` bigint(20) DEFAULT '0',
  `order_place_count` bigint(20) DEFAULT '0',
  `order_cancel_cost` bigint(20) DEFAULT '0',
  `update_time` bigint(20) unsigned NOT NULL,
  `create_time` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_gene_yearly_detail_local_staff` (`local_staff_id`),
  KEY `fk_gene_yearly_detail_region` (`local_staff_region_id`),
  KEY `fk_gene_yearly_detail_city` (`local_staff_city_id`),
  KEY `fk_gene_yearly_detail_province` (`local_staff_province_id`),
  KEY `fk_gene_yearly_detail_doctor` (`doctor_id`),
  KEY `index_gene_yearly_detail_order_id` (`order_id`),
  KEY `index_gene_yearly_detail_year_str` (`year_str`),
  KEY `index_gene_yearly_detail_local_staff_region_id` (`local_staff_region_id`),
  KEY `index_gene_yearly_detail_local_staff_province_id` (`local_staff_province_id`),
  CONSTRAINT `fk_gene_yearly_detail_city` FOREIGN KEY (`local_staff_city_id`) REFERENCES `city` (`city_id`),
  CONSTRAINT `fk_gene_yearly_detail_doctor` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`doctor_id`),
  CONSTRAINT `fk_gene_yearly_detail_local_staff` FOREIGN KEY (`local_staff_id`) REFERENCES `local_staff` (`local_staff_id`),
  CONSTRAINT `fk_gene_yearly_detail_province` FOREIGN KEY (`local_staff_province_id`) REFERENCES `province` (`province_id`),
  CONSTRAINT `fk_gene_yearly_detail_region` FOREIGN KEY (`local_staff_region_id`) REFERENCES `region` (`region_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for holiday_day
-- ----------------------------
DROP TABLE IF EXISTS `holiday_day`;
CREATE TABLE `holiday_day` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `year_str` varchar(255) NOT NULL,
  `month_str` varchar(255) NOT NULL,
  `day_str` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for integral_coefficient
-- ----------------------------
DROP TABLE IF EXISTS `integral_coefficient`;
CREATE TABLE `integral_coefficient` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `start_date` bigint(20) NOT NULL,
  `end_date` bigint(20) NOT NULL,
  `integral_ration` double NOT NULL DEFAULT '1',
  `dcw_ration` double NOT NULL DEFAULT '1',
  `create_time` bigint(20) NOT NULL,
  `update_time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for invoice
-- ----------------------------
DROP TABLE IF EXISTS `invoice`;
CREATE TABLE `invoice` (
  `invoice_code` varchar(255) NOT NULL,
  `invoice_number` varchar(255) NOT NULL,
  `invoice_goods_code_version` varchar(255) DEFAULT NULL,
  `invoice_tax_classification_code` varchar(255) DEFAULT NULL,
  `invoice_time` bigint(20) DEFAULT NULL,
  `invoice_date` varchar(255) DEFAULT NULL,
  `invoice_buyer_name` varchar(255) DEFAULT NULL,
  `invoice_buyer_tax_payer_number` varchar(255) DEFAULT NULL,
  `invoice_buyer_address` varchar(255) DEFAULT NULL,
  `invoice_buyer_bank` varchar(255) DEFAULT NULL,
  `invoice_item_name` varchar(255) DEFAULT NULL,
  `invoice_item_count` int(10) DEFAULT NULL,
  `invoice_item_unit_price` bigint(20) DEFAULT NULL,
  `invoice_post_tax_amount` bigint(20) DEFAULT NULL,
  `invoice_tax_rate` int(10) DEFAULT NULL,
  `invoice_tax_amount` bigint(20) DEFAULT NULL,
  `invoice_amount` bigint(20) DEFAULT NULL,
  `invoice_seller_name` varchar(255) DEFAULT NULL,
  `invoice_seller_tax_payer_number` varchar(255) DEFAULT NULL,
  `invoice_seller_address` varchar(255) DEFAULT NULL,
  `invoice_seller_bank` varchar(255) DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `update_time` bigint(20) unsigned NOT NULL,
  `create_time` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`invoice_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for kpi_export
-- ----------------------------
DROP TABLE IF EXISTS `kpi_export`;
CREATE TABLE `kpi_export` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `original_file_name` varchar(255) NOT NULL,
  `storage_file_name` varchar(255) NOT NULL,
  `kpi_date` bigint(20) NOT NULL DEFAULT '0',
  `export_time` bigint(20) NOT NULL DEFAULT '0',
  `kpi_module` int(10) unsigned NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_kpi_export_kpi_upload` (`kpi_module`),
  CONSTRAINT `fk_kpi_export_kpi_upload` FOREIGN KEY (`kpi_module`) REFERENCES `kpi_upload` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for kpi_upload
-- ----------------------------
DROP TABLE IF EXISTS `kpi_upload`;
CREATE TABLE `kpi_upload` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `original_file_name` varchar(255) NOT NULL,
  `storage_file_name` varchar(255) NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  `upload_time` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for local_staff
-- ----------------------------
DROP TABLE IF EXISTS `local_staff`;
CREATE TABLE `local_staff` (
  `local_staff_id` varchar(255) NOT NULL,
  `local_staff_job_number` varchar(50) DEFAULT NULL,
  `local_staff_name` varchar(50) NOT NULL,
  `local_staff_region_id` varchar(255) NOT NULL,
  `local_staff_region_name` varchar(50) DEFAULT NULL,
  `local_staff_province_id` varchar(255) NOT NULL,
  `local_staff_province_name` varchar(50) DEFAULT NULL,
  `local_staff_city_id` varchar(255) NOT NULL,
  `local_staff_city_name` varchar(50) DEFAULT NULL,
  `local_staff_county_id` varchar(255) DEFAULT NULL,
  `local_staff_county_name` varchar(50) DEFAULT NULL,
  `update_time` bigint(20) unsigned NOT NULL,
  `create_time` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`local_staff_id`),
  KEY `fk_local_staff_region` (`local_staff_region_id`),
  KEY `fk_local_staff_province` (`local_staff_province_id`),
  KEY `fk_local_staff_city` (`local_staff_city_id`),
  CONSTRAINT `fk_local_staff_city` FOREIGN KEY (`local_staff_city_id`) REFERENCES `city` (`city_id`),
  CONSTRAINT `fk_local_staff_province` FOREIGN KEY (`local_staff_province_id`) REFERENCES `province` (`province_id`),
  CONSTRAINT `fk_local_staff_region` FOREIGN KEY (`local_staff_region_id`) REFERENCES `region` (`region_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for logistics_payout
-- ----------------------------
DROP TABLE IF EXISTS `logistics_payout`;
CREATE TABLE `logistics_payout` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `tag` varchar(255) NOT NULL,
  `message_id` varchar(255) NOT NULL,
  `record_id` int(10) NOT NULL,
  `day_str` varchar(50) DEFAULT NULL,
  `month_str` varchar(50) DEFAULT NULL,
  `year_str` varchar(50) DEFAULT NULL,
  `quarter_str` varchar(50) DEFAULT NULL,
  `half_year_str` varchar(50) DEFAULT NULL,
  `event` varchar(50) NOT NULL,
  `event_time` bigint(20) NOT NULL,
  `total` bigint(20) NOT NULL DEFAULT '0',
  `local_staff_id` varchar(255) NOT NULL,
  `local_staff_job_number` varchar(50) DEFAULT NULL,
  `local_staff_name` varchar(50) DEFAULT NULL,
  `local_staff_region_id` varchar(255) NOT NULL,
  `local_staff_region_name` varchar(50) DEFAULT NULL,
  `local_staff_province_id` varchar(255) NOT NULL,
  `local_staff_province_name` varchar(50) DEFAULT NULL,
  `local_staff_city_id` varchar(255) NOT NULL,
  `local_staff_city_name` varchar(50) DEFAULT NULL,
  `local_staff_county_id` varchar(255) DEFAULT NULL,
  `local_staff_county_name` varchar(50) DEFAULT NULL,
  `create_time` bigint(20) unsigned NOT NULL,
  `update_time` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_logistics_payout_local_staff` (`local_staff_id`),
  KEY `fk_logistics_payout_region` (`local_staff_region_id`),
  KEY `fk_logistics_payout_province` (`local_staff_province_id`),
  KEY `fk_logistics_payout_city` (`local_staff_city_id`),
  CONSTRAINT `fk_logistics_payout_city` FOREIGN KEY (`local_staff_city_id`) REFERENCES `city` (`city_id`),
  CONSTRAINT `fk_logistics_payout_local_staff` FOREIGN KEY (`local_staff_id`) REFERENCES `local_staff` (`local_staff_id`),
  CONSTRAINT `fk_logistics_payout_province` FOREIGN KEY (`local_staff_province_id`) REFERENCES `province` (`province_id`),
  CONSTRAINT `fk_logistics_payout_region` FOREIGN KEY (`local_staff_region_id`) REFERENCES `region` (`region_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for order_invoice
-- ----------------------------
DROP TABLE IF EXISTS `order_invoice`;
CREATE TABLE `order_invoice` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `order_id` varchar(255) NOT NULL,
  `invoice_code` varchar(255) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `update_time` bigint(20) unsigned NOT NULL,
  `create_time` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_order_invoice_invoice` (`invoice_code`),
  KEY `index_order_invoice_order_id` (`order_id`),
  CONSTRAINT `fk_order_invoice_invoice` FOREIGN KEY (`invoice_code`) REFERENCES `invoice` (`invoice_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for order_logistics
-- ----------------------------
DROP TABLE IF EXISTS `order_logistics`;
CREATE TABLE `order_logistics` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `tag` varchar(50) NOT NULL,
  `message_id` varchar(100) NOT NULL,
  `record_id` int(10) NOT NULL,
  `event` varchar(50) NOT NULL,
  `event_time` bigint(20) NOT NULL,
  `order_id` varchar(50) NOT NULL,
  `logistics_type` varchar(50) DEFAULT NULL,
  `express_number` varchar(50) DEFAULT NULL,
  `express_company_name` varchar(50) DEFAULT NULL,
  `express_company_id` varchar(50) DEFAULT NULL,
  `update_time` bigint(20) unsigned NOT NULL,
  `create_time` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for page
-- ----------------------------
DROP TABLE IF EXISTS `page`;
CREATE TABLE `page` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `label` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `update_time` bigint(20) NOT NULL,
  `create_time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `index_page_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of page
-- ----------------------------
BEGIN;
INSERT INTO `page` VALUES (1, 'dashboard', '首页', '包含今日各种维度报表及走势图', 1530002859831, 1530002859831);
INSERT INTO `page` VALUES (2, 'query:business-detail', '业务明细表', '以订单为维度按时间查询订单数据', 1530002859831, 1530002859831);
INSERT INTO `page` VALUES (3, 'query:daily-detail', '日统计查询', '以天为周期的统计查询', 1530002859831, 1530002859831);
INSERT INTO `page` VALUES (4, 'query:region-contrast', '大区对比', '按大区分组进行对比', 1530002859831, 1530002859831);
INSERT INTO `page` VALUES (5, 'query:province-contrast', '省份对比', '按省份分组进行对比', 1530002859831, 1530002859831);
INSERT INTO `page` VALUES (6, 'query:testing-item-contrast', '检测项目对比', '按检测项目分组进行对比', 1530002859831, 1530002859831);
INSERT INTO `page` VALUES (7, 'query:income-detail', '收入明细表', '财务所需的发票收入明细汇总', 1530002859831, 1530002859831);
INSERT INTO `page` VALUES (8, 'report:business-detail', '业务明细表', '以订单为维度按时间查询订单数据', 1530002859831, 1530002859831);
INSERT INTO `page` VALUES (9, 'report:region-contrast', '大区对比', '按大区分组进行对比', 1530002859831, 1530002859831);
INSERT INTO `page` VALUES (10, 'report:province-contrast', '省份对比', '按省份分组进行对比', 1530002859831, 1530002859831);
INSERT INTO `page` VALUES (11, 'report:testing-item-contrast', '检测项目对比', '按检测项目分组进行对比', 1530002859831, 1530002859831);
INSERT INTO `page` VALUES (12, 'report:profits', '利润表', '按大区分组展示业务利润', 1530002859831, 1530002859831);
INSERT INTO `page` VALUES (13, 'report:income-detail', '收入明细表', '财务所需的发票收入明细汇总', 1530002859831, 1530002859831);
INSERT INTO `page` VALUES (14, 'page', '模块管理', '模块管理', 1530002859831, 1530002859831);
INSERT INTO `page` VALUES (15, 'user', '用户管理', '用户管理', 1530002859831, 1530002859831);
INSERT INTO `page` VALUES (16, 'role', '角色管理', '角色管理', 1530002859831, 1530002859831);
INSERT INTO `page` VALUES (17, 'permission', '权限管理', '权限管理', 1530002859831, 1530002859831);
INSERT INTO `page` VALUES (18, 'query:order-without-invoice', '未开票明细表', '未开票明细表', 1530002859831, 1530002859831);
INSERT INTO `page` VALUES (19, 'query:order-trend-month-avg', '月平均销量表', '月平均销量表', 1530002859831, 1530002859831);
INSERT INTO `page` VALUES (20, 'kpi', '业绩考核表', '业绩考核表', 1530002859831, 1530002859831);
COMMIT;

-- ----------------------------
-- Table structure for payment_info
-- ----------------------------
DROP TABLE IF EXISTS `payment_info`;
CREATE TABLE `payment_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `pay_time` bigint(20) unsigned NOT NULL,
  `order_id` varchar(50) NOT NULL,
  `type` varchar(50) DEFAULT NULL,
  `total` bigint(20) DEFAULT '0',
  `account` varchar(255) DEFAULT NULL,
  `user_name` varchar(50) DEFAULT NULL,
  `local_staff_payroll` tinyint(1) DEFAULT '0',
  `payment_type` varchar(50) DEFAULT NULL,
  `trade_number` varchar(255) DEFAULT NULL,
  `order_pay_attorney_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_payment_info_order_id` (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `page_id` int(10) unsigned NOT NULL,
  `name` varchar(255) NOT NULL,
  `label` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  `parent` int(10) DEFAULT NULL,
  `sort` int(255) NOT NULL DEFAULT '0',
  `update_time` bigint(20) NOT NULL,
  `create_time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_permission_page` (`page_id`),
  KEY `index_permission_type` (`type`),
  CONSTRAINT `fk_permission_page` FOREIGN KEY (`page_id`) REFERENCES `page` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of permission
-- ----------------------------
BEGIN;
INSERT INTO `permission` VALUES (1, 1, 'dashboard:dimension-count', '今日各维度数据', 'dashboard:dimension-count', NULL, NULL, 0, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (2, 1, 'dashboard:order-trend', '销售额趋势', 'dashboard:order-trend', NULL, NULL, 1, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (3, 1, 'dashboard:order-count-top', '大区订单量排行', 'dashboard:order-count-top', NULL, NULL, 2, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (4, 1, 'dashboard:order-count-top', '省份业务明细', 'dashboard:order-count-top', NULL, NULL, 3, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (5, 2, 'query:business-detail', '基因检测业务-业务明细查询', 'query', NULL, NULL, 0, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (6, 2, 'query:business-detail-export', '基因检测业务-业务明细查询-导出Excel', 'export', NULL, NULL, 1, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (7, 3, 'query:daily-detail', '基因检测业务-日统计查询', 'query', NULL, NULL, 0, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (8, 3, 'query:daily-detail-export', '基因检测业务-日统计查询-导出Excel', 'export', NULL, NULL, 1, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (9, 4, 'query:region-contrast', '基因检测业务-业务明细对比-大区对比查询', 'query', NULL, NULL, 0, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (10, 4, 'query:region-contrast-export', '基因检测业务-业务明细对比-大区对比查询-导出Excel', 'export', NULL, NULL, 1, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (11, 5, 'query:province-contrast', '基因检测业务-业务明细对比-省份对比查询', 'query', NULL, NULL, 0, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (12, 5, 'query:province-contrast-export', '基因检测业务-业务明细对比-省份对比查询-导出Excel', 'export', NULL, NULL, 1, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (13, 6, 'query:testing-item-contrast', '基因检测业务-业务明细对比-检测项目对比查询', 'query', NULL, NULL, 0, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (14, 6, 'query:testing-item-contrast-export', '基因检测业务-业务明细对比-检测项目对比查询-导出Excel', 'export', NULL, NULL, 1, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (15, 7, 'query:income-detail', '基因检测业务-收入明细查询', 'query', NULL, NULL, 0, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (16, 7, 'query:income-detail-export', '基因检测业务-收入明细查询-导出Excel', 'export', NULL, NULL, 1, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (17, 8, 'report:business-detail', '基因检测业务-业务明细查询', 'query', NULL, NULL, 0, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (18, 8, 'report:business-detail-export', '基因检测业务-业务明细查询-导出Excel', 'export', NULL, NULL, 1, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (19, 9, 'report:region-contrast', '基因检测业务-业务明细对比-大区对比查询', 'query', NULL, NULL, 0, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (20, 9, 'report:region-contrast-export', '基因检测业务-业务明细对比-大区对比查询-导出Excel', 'export', NULL, NULL, 1, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (21, 10, 'report:province-contrast', '基因检测业务-业务明细对比-省份对比查询', 'query', NULL, NULL, 0, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (22, 10, 'report:province-contrast-export', '基因检测业务-业务明细对比-省份对比查询-导出Excel', 'export', NULL, NULL, 1, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (23, 11, 'report:testing-item-contrast', '基因检测业务-业务明细对比-检测项目对比查询', 'query', NULL, NULL, 0, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (24, 11, 'report:testing-item-contrast-export', '基因检测业务-业务明细对比-检测项目对比查询-导出Excel', 'export', NULL, NULL, 1, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (25, 12, 'report:profits', '基因检测业务-利润查询', 'query', NULL, NULL, 0, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (26, 12, 'report:profits-export', '基因检测业务-利润查询-导出Excel', 'export', NULL, NULL, 1, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (27, 13, 'report:income-detail', '基因检测业务-收入明细', 'query', NULL, NULL, 0, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (28, 13, 'report:income-detail-export', '基因检测业务-收入明细-导出Excel', 'export', NULL, NULL, 1, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (29, 14, 'page:add', '新增页面', 'add', NULL, NULL, 0, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (30, 14, 'page:delete', '删除页面', 'delete', NULL, NULL, 1, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (31, 14, 'page:update', '更新页面', 'update', NULL, NULL, 2, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (32, 14, 'page:get', '获取页面详细信息', 'get', NULL, NULL, 3, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (33, 14, 'page:list', '获取页面列表', 'list', NULL, NULL, 4, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (34, 15, 'user:add', '用户管理-新增用户', 'add', NULL, NULL, 0, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (35, 15, 'user:delete', '用户管理-删除用户', 'delete', NULL, NULL, 1, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (36, 15, 'user:update', '用户管理-更新用户', 'update', NULL, NULL, 2, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (37, 15, 'user:get', '用户管理-获取用户详细信息', 'get', NULL, NULL, 3, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (38, 15, 'user:list', '用户管理-获取用户列表', 'list', NULL, NULL, 4, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (39, 16, 'role:add', '角色管理-新增角色', 'add', NULL, NULL, 0, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (40, 16, 'role:delete', '角色管理-删除角色', 'delete', NULL, NULL, 1, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (41, 16, 'role:update', '角色管理-更新角色', 'update', NULL, NULL, 2, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (42, 16, 'role:get', '角色管理-获取角色详细信息', 'get', NULL, NULL, 3, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (43, 16, 'role:list', '角色管理-获取角色列表', 'list', NULL, NULL, 4, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (44, 17, 'permission:add', '权限管理-新增权限', 'add', NULL, NULL, 0, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (45, 17, 'permission:delete', '权限管理-删除权限', 'delete', NULL, NULL, 1, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (46, 17, 'permission:update', '权限管理-更新权限', 'update', NULL, NULL, 2, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (47, 17, 'permission:get', '权限管理-获取权限详细信息', 'get', NULL, NULL, 3, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (48, 17, 'permission:list', '权限管理-获取权限列表', 'list', NULL, NULL, 4, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (49, 18, 'query:order-without-invoice', '基因检测业务-未开票明细表', 'query', NULL, NULL, 0, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (50, 18, 'query:order-without-invoice-export', '基因检测业务-未开票明细表-导出Excel', 'export', NULL, NULL, 1, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (51, 1, 'dashboard:order-trend-month-avg', '月均销量趋势图', 'dashboard:order-trend-month-avg', NULL, NULL, 4, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (52, 19, 'query:order-trend-month-avg', '基因检测业务-月平均销量表', 'query', NULL, NULL, 0, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (53, 19, 'query:order-trend-month-avg-export', '基因检测业务-月平均销量表-导出Excel', 'export', NULL, NULL, 1, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (54, 20, 'kpi:upload-export', '绩效考核-上传并导出', 'export', NULL, NULL, 0, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (55, 20, 'kpi:history', '绩效考核-历史记录', 'query', NULL, NULL, 0, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (56, 20, 'kpi:export-history', '绩效考核-导出历史绩效', 'export', NULL, NULL, 0, 1530003069707, 1530003069707);
INSERT INTO `permission` VALUES (57, 20, 'kpi:export-again', '绩效考核-重新导出绩效', 'export', NULL, NULL, 0, 1530003069707, 1530003069707);
COMMIT;

-- ----------------------------
-- Table structure for properties
-- ----------------------------
DROP TABLE IF EXISTS `properties`;
CREATE TABLE `properties` (
  `id` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `type_int` int(10) DEFAULT NULL,
  `type_varchar` varchar(255) DEFAULT NULL,
  `create_time` bigint(20) NOT NULL,
  `update_time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of properties
-- ----------------------------
BEGIN;
INSERT INTO `properties` VALUES ('hs_integral', 'type_int', 1, NULL, 1529377806000, 1529377806000);
COMMIT;

-- ----------------------------
-- Table structure for province
-- ----------------------------
DROP TABLE IF EXISTS `province`;
CREATE TABLE `province` (
  `province_id` varchar(255) NOT NULL,
  `province_name` varchar(50) NOT NULL,
  `region_id` varchar(255) DEFAULT NULL,
  `update_time` bigint(20) unsigned NOT NULL,
  `create_time` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`province_id`),
  KEY `fk_province_region` (`region_id`),
  CONSTRAINT `fk_province_region` FOREIGN KEY (`region_id`) REFERENCES `region` (`region_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for region
-- ----------------------------
DROP TABLE IF EXISTS `region`;
CREATE TABLE `region` (
  `region_id` varchar(50) NOT NULL,
  `region_name` varchar(50) NOT NULL,
  `update_time` bigint(20) NOT NULL,
  `create_time` bigint(20) NOT NULL,
  PRIMARY KEY (`region_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `label` varchar(255) NOT NULL,
  `update_time` bigint(20) NOT NULL,
  `create_time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `index_role_name` (`name`),
  KEY `index_role_label` (`label`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
BEGIN;
INSERT INTO `role` VALUES (1, 'boss', '领导', 1528269419000, 1528269419000);
INSERT INTO `role` VALUES (2, 'admin', '管理员', 1528269419000, 1528269419000);
INSERT INTO `role` VALUES (3, 'finance', '财务', 1528269419000, 1528269419000);
COMMIT;

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `role_id` int(10) unsigned NOT NULL,
  `permission_id` int(10) unsigned NOT NULL,
  `update_time` bigint(20) DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_role_permission_role` (`role_id`),
  KEY `fk_role_permission_permission` (`permission_id`),
  CONSTRAINT `fk_role_permission_permission` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`),
  CONSTRAINT `fk_role_permission_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
BEGIN;
INSERT INTO `role_permission` VALUES (1, 1, 1, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (2, 1, 2, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (3, 1, 3, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (4, 1, 4, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (5, 1, 5, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (6, 1, 6, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (7, 1, 7, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (8, 1, 8, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (9, 1, 9, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (10, 1, 10, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (11, 1, 11, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (12, 1, 12, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (13, 1, 13, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (14, 1, 14, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (15, 1, 15, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (16, 1, 16, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (17, 1, 17, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (18, 1, 18, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (19, 1, 19, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (20, 1, 20, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (21, 1, 21, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (22, 1, 22, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (23, 1, 23, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (24, 1, 24, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (25, 1, 25, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (26, 1, 26, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (27, 1, 27, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (28, 1, 28, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (29, 2, 1, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (30, 2, 2, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (31, 2, 3, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (32, 2, 4, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (33, 2, 5, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (34, 2, 6, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (35, 2, 7, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (36, 2, 8, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (37, 2, 9, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (38, 2, 10, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (39, 2, 11, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (40, 2, 12, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (41, 2, 13, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (42, 2, 14, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (43, 2, 15, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (44, 2, 16, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (45, 2, 17, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (46, 2, 18, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (47, 2, 19, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (48, 2, 20, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (49, 2, 21, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (50, 2, 22, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (51, 2, 23, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (52, 2, 24, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (53, 2, 25, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (54, 2, 26, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (55, 2, 27, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (56, 2, 28, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (57, 2, 29, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (58, 2, 30, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (59, 2, 31, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (60, 2, 32, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (61, 2, 33, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (62, 2, 34, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (63, 2, 35, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (64, 2, 36, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (65, 2, 37, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (66, 2, 38, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (67, 2, 39, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (68, 2, 40, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (69, 2, 41, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (70, 2, 42, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (71, 2, 43, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (72, 2, 44, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (73, 2, 45, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (74, 2, 46, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (75, 2, 47, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (76, 2, 48, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (77, 2, 49, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (78, 2, 50, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (79, 2, 51, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (80, 2, 52, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (81, 2, 53, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (82, 3, 15, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (83, 3, 16, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (84, 3, 27, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (85, 3, 28, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (86, 2, 54, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (87, 2, 55, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (88, 2, 56, 1530003183277, 1530003183277);
INSERT INTO `role_permission` VALUES (89, 2, 57, 1530003183277, 1530003183277);
COMMIT;

-- ----------------------------
-- Table structure for snapshoot
-- ----------------------------
DROP TABLE IF EXISTS `snapshoot`;
CREATE TABLE `snapshoot` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `type` varchar(50) NOT NULL,
  `time_range` varchar(50) NOT NULL,
  `day_str` varchar(20) DEFAULT NULL,
  `month_str` varchar(20) DEFAULT NULL,
  `year_str` varchar(20) DEFAULT NULL,
  `quarter_str` varchar(20) DEFAULT NULL,
  `half_year_str` varchar(20) DEFAULT NULL,
  `record` longtext NOT NULL,
  `recreate` tinyint(1) NOT NULL DEFAULT '0',
  `update_time` bigint(20) NOT NULL,
  `create_time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `index_snapshoot_type` (`type`),
  KEY `index_snapshoot_time_range` (`time_range`),
  KEY `index_snapshoot_day_str` (`day_str`),
  KEY `index_snapshoot_month_str` (`month_str`),
  KEY `index_snapshoot_year_str` (`year_str`),
  KEY `index_snapshoot_quarter_str` (`quarter_str`),
  KEY `index_snapshoot_half_year_str` (`half_year_str`),
  KEY `index_snapshoot_half_recreate` (`recreate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for testing_item
-- ----------------------------
DROP TABLE IF EXISTS `testing_item`;
CREATE TABLE `testing_item` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `order_id` varchar(255) NOT NULL,
  `testing_result` varchar(255) DEFAULT NULL,
  `testing_agency` varchar(50) DEFAULT NULL,
  `testing_item` varchar(255) DEFAULT NULL,
  `testing_item_id` int(10) NOT NULL,
  `testing_item_price` bigint(20) DEFAULT NULL,
  `testing_item_cost` bigint(20) unsigned DEFAULT NULL,
  `testing_agency_id` int(10) NOT NULL,
  `testing_agency_address` varchar(100) DEFAULT NULL,
  `testing_report_number` varchar(100) DEFAULT NULL,
  `testing_report_url` varchar(255) DEFAULT NULL,
  `testing_report_upload_time` bigint(20) unsigned DEFAULT NULL,
  `sampling_time` bigint(20) unsigned DEFAULT NULL,
  `sample_confirm_time` bigint(20) unsigned DEFAULT NULL,
  `update_time` bigint(20) unsigned NOT NULL,
  `create_time` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_testing_item_testing_item_table` (`testing_item_id`),
  KEY `index_testing_item_order_id` (`order_id`),
  CONSTRAINT `fk_testing_item_testing_item_table` FOREIGN KEY (`testing_item_id`) REFERENCES `testing_item_tab` (`testing_item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for testing_item_daily_detail
-- ----------------------------
DROP TABLE IF EXISTS `testing_item_daily_detail`;
CREATE TABLE `testing_item_daily_detail` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `tag` varchar(50) NOT NULL,
  `message_id` varchar(100) NOT NULL,
  `record_id` int(10) NOT NULL,
  `day_str` varchar(50) DEFAULT NULL,
  `month_str` varchar(50) DEFAULT NULL,
  `year_str` varchar(50) DEFAULT NULL,
  `quarter_str` varchar(50) DEFAULT NULL,
  `half_year_str` varchar(50) DEFAULT NULL,
  `event` varchar(50) NOT NULL,
  `event_time` bigint(20) NOT NULL,
  `change_count` int(5) DEFAULT '1',
  `testing_item_order_income` bigint(20) DEFAULT '0',
  `testing_item_cash_income` bigint(20) DEFAULT '0',
  `testing_item_finance_confirm_income` bigint(20) DEFAULT '0',
  `testing_item_record_cost` bigint(20) DEFAULT '0',
  `testing_item_confirm_record_cost` bigint(20) DEFAULT '0',
  `order_id` varchar(255) NOT NULL,
  `testing_result` varchar(255) DEFAULT NULL,
  `testing_agency` varchar(50) DEFAULT NULL,
  `testing_item` varchar(255) DEFAULT NULL,
  `testing_item_id` int(10) NOT NULL,
  `testing_item_price` bigint(20) DEFAULT NULL,
  `testing_item_cost` bigint(20) unsigned DEFAULT NULL,
  `testing_agency_id` int(10) NOT NULL,
  `testing_agency_address` varchar(100) DEFAULT NULL,
  `testing_report_number` varchar(100) DEFAULT NULL,
  `testing_report_url` varchar(255) DEFAULT NULL,
  `testing_report_upload_time` bigint(20) unsigned DEFAULT '0',
  `sampling_time` bigint(20) unsigned DEFAULT NULL,
  `order_create_time` bigint(20) DEFAULT '0',
  `order_pay_time` bigint(20) DEFAULT '0',
  `sample_confirm_time` bigint(20) unsigned DEFAULT '0',
  `order_invoice_time` bigint(20) DEFAULT '0',
  `order_cancel_time` bigint(20) DEFAULT '0',
  `order_update_time` bigint(20) DEFAULT '0',
  `testing_item_cancel_count` bigint(20) DEFAULT '0',
  `testing_item_place_count` bigint(20) DEFAULT '0',
  `update_time` bigint(20) unsigned NOT NULL,
  `create_time` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_testing_item_daily_detail_testing_item_table` (`testing_item_id`),
  KEY `index_testing_item_daily_detail_order_id` (`order_id`),
  KEY `index_testing_item_daily_detail_day_str` (`day_str`),
  KEY `index_testing_item_daily_detail_event_time` (`event_time`),
  KEY `index_testing_item_daily_detail_testing_item_place_count` (`testing_item_place_count`),
  KEY `index_testing_item_daily_detail_testing_item_order_income` (`testing_item_order_income`),
  CONSTRAINT `fk_testing_item_daily_detail_testing_item_table` FOREIGN KEY (`testing_item_id`) REFERENCES `testing_item_tab` (`testing_item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for testing_item_detail
-- ----------------------------
DROP TABLE IF EXISTS `testing_item_detail`;
CREATE TABLE `testing_item_detail` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `tag` varchar(50) NOT NULL,
  `message_id` varchar(100) NOT NULL,
  `record_id` int(10) NOT NULL,
  `day_str` varchar(50) DEFAULT NULL,
  `month_str` varchar(50) DEFAULT NULL,
  `year_str` varchar(50) DEFAULT NULL,
  `quarter_str` varchar(50) DEFAULT NULL,
  `half_year_str` varchar(50) DEFAULT NULL,
  `event` varchar(50) NOT NULL,
  `event_time` bigint(20) NOT NULL,
  `change_count` int(5) DEFAULT '1',
  `testing_item_order_income` bigint(20) DEFAULT '0',
  `testing_item_cash_income` bigint(20) DEFAULT '0',
  `testing_item_finance_confirm_income` bigint(20) DEFAULT '0',
  `testing_item_record_cost` bigint(20) DEFAULT '0',
  `testing_item_confirm_record_cost` bigint(20) DEFAULT '0',
  `order_id` varchar(255) NOT NULL,
  `testing_result` varchar(255) DEFAULT NULL,
  `testing_agency` varchar(50) DEFAULT NULL,
  `testing_item` varchar(255) DEFAULT NULL,
  `testing_item_id` int(10) NOT NULL,
  `testing_item_price` bigint(20) DEFAULT NULL,
  `testing_item_cost` bigint(20) unsigned DEFAULT NULL,
  `testing_agency_id` int(10) NOT NULL,
  `testing_agency_address` varchar(100) DEFAULT NULL,
  `testing_report_number` varchar(100) DEFAULT NULL,
  `testing_report_url` varchar(255) DEFAULT NULL,
  `testing_report_upload_time` bigint(20) unsigned DEFAULT '0',
  `sampling_time` bigint(20) unsigned DEFAULT NULL,
  `order_create_time` bigint(20) DEFAULT '0',
  `order_pay_time` bigint(20) DEFAULT '0',
  `sample_confirm_time` bigint(20) unsigned DEFAULT '0',
  `order_invoice_time` bigint(20) DEFAULT '0',
  `order_cancel_time` bigint(20) DEFAULT '0',
  `order_update_time` bigint(20) DEFAULT '0',
  `testing_item_cancel_count` bigint(20) DEFAULT '0',
  `testing_item_place_count` bigint(20) DEFAULT '0',
  `update_time` bigint(20) unsigned NOT NULL,
  `create_time` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_testing_item_detail_testing_item_table` (`testing_item_id`),
  KEY `index_testing_item_detail_order_id` (`order_id`),
  CONSTRAINT `fk_testing_item_detail_testing_item_table` FOREIGN KEY (`testing_item_id`) REFERENCES `testing_item_tab` (`testing_item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for testing_item_half_yearly_detail
-- ----------------------------
DROP TABLE IF EXISTS `testing_item_half_yearly_detail`;
CREATE TABLE `testing_item_half_yearly_detail` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `tag` varchar(50) NOT NULL,
  `message_id` varchar(100) NOT NULL,
  `record_id` int(10) NOT NULL,
  `day_str` varchar(50) DEFAULT NULL,
  `month_str` varchar(50) DEFAULT NULL,
  `year_str` varchar(50) DEFAULT NULL,
  `quarter_str` varchar(50) DEFAULT NULL,
  `half_year_str` varchar(50) DEFAULT NULL,
  `event` varchar(50) NOT NULL,
  `event_time` bigint(20) NOT NULL,
  `change_count` int(5) DEFAULT '1',
  `testing_item_order_income` bigint(20) DEFAULT '0',
  `testing_item_cash_income` bigint(20) DEFAULT '0',
  `testing_item_finance_confirm_income` bigint(20) DEFAULT '0',
  `testing_item_record_cost` bigint(20) DEFAULT '0',
  `testing_item_confirm_record_cost` bigint(20) DEFAULT '0',
  `order_id` varchar(255) NOT NULL,
  `testing_result` varchar(255) DEFAULT NULL,
  `testing_agency` varchar(50) DEFAULT NULL,
  `testing_item` varchar(255) DEFAULT NULL,
  `testing_item_id` int(10) NOT NULL,
  `testing_item_price` bigint(20) DEFAULT NULL,
  `testing_item_cost` bigint(20) unsigned DEFAULT NULL,
  `testing_agency_id` int(10) NOT NULL,
  `testing_agency_address` varchar(100) DEFAULT NULL,
  `testing_report_number` varchar(100) DEFAULT NULL,
  `testing_report_url` varchar(255) DEFAULT NULL,
  `testing_report_upload_time` bigint(20) unsigned DEFAULT '0',
  `sampling_time` bigint(20) unsigned DEFAULT NULL,
  `order_create_time` bigint(20) DEFAULT '0',
  `order_pay_time` bigint(20) DEFAULT '0',
  `sample_confirm_time` bigint(20) unsigned DEFAULT '0',
  `order_invoice_time` bigint(20) DEFAULT '0',
  `order_cancel_time` bigint(20) DEFAULT '0',
  `order_update_time` bigint(20) DEFAULT '0',
  `testing_item_cancel_count` bigint(20) DEFAULT '0',
  `testing_item_place_count` bigint(20) DEFAULT '0',
  `update_time` bigint(20) unsigned NOT NULL,
  `create_time` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_testing_item_half_yearly_detail_testing_item_table` (`testing_item_id`),
  KEY `index_testing_item_half_yearly_detail_order_id` (`order_id`),
  KEY `index_testing_item_half_yearly_detail_half_year_str` (`half_year_str`),
  CONSTRAINT `fk_testing_item_half_yearly_detail_testing_item_table` FOREIGN KEY (`testing_item_id`) REFERENCES `testing_item_tab` (`testing_item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for testing_item_monthly_detail
-- ----------------------------
DROP TABLE IF EXISTS `testing_item_monthly_detail`;
CREATE TABLE `testing_item_monthly_detail` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `tag` varchar(50) NOT NULL,
  `message_id` varchar(100) NOT NULL,
  `record_id` int(10) NOT NULL,
  `day_str` varchar(50) DEFAULT NULL,
  `month_str` varchar(50) DEFAULT NULL,
  `year_str` varchar(50) DEFAULT NULL,
  `quarter_str` varchar(50) DEFAULT NULL,
  `half_year_str` varchar(50) DEFAULT NULL,
  `event` varchar(50) NOT NULL,
  `event_time` bigint(20) NOT NULL,
  `change_count` int(5) DEFAULT '1',
  `testing_item_order_income` bigint(20) DEFAULT '0',
  `testing_item_cash_income` bigint(20) DEFAULT '0',
  `testing_item_finance_confirm_income` bigint(20) DEFAULT '0',
  `testing_item_record_cost` bigint(20) DEFAULT '0',
  `testing_item_confirm_record_cost` bigint(20) DEFAULT '0',
  `order_id` varchar(255) NOT NULL,
  `testing_result` varchar(255) DEFAULT NULL,
  `testing_agency` varchar(50) DEFAULT NULL,
  `testing_item` varchar(255) DEFAULT NULL,
  `testing_item_id` int(10) NOT NULL,
  `testing_item_price` bigint(20) DEFAULT NULL,
  `testing_item_cost` bigint(20) unsigned DEFAULT NULL,
  `testing_agency_id` int(10) NOT NULL,
  `testing_agency_address` varchar(100) DEFAULT NULL,
  `testing_report_number` varchar(100) DEFAULT NULL,
  `testing_report_url` varchar(255) DEFAULT NULL,
  `testing_report_upload_time` bigint(20) unsigned DEFAULT '0',
  `sampling_time` bigint(20) unsigned DEFAULT NULL,
  `order_create_time` bigint(20) DEFAULT '0',
  `order_pay_time` bigint(20) DEFAULT '0',
  `sample_confirm_time` bigint(20) unsigned DEFAULT '0',
  `order_invoice_time` bigint(20) DEFAULT '0',
  `order_cancel_time` bigint(20) DEFAULT '0',
  `order_update_time` bigint(20) DEFAULT '0',
  `testing_item_cancel_count` bigint(20) DEFAULT '0',
  `testing_item_place_count` bigint(20) DEFAULT '0',
  `update_time` bigint(20) unsigned NOT NULL,
  `create_time` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_testing_item_monthly_detail_testing_item_table` (`testing_item_id`),
  KEY `index_testing_item_monthly_detail_order_id` (`order_id`),
  KEY `index_testing_item_monthly_detail_month_str` (`month_str`),
  CONSTRAINT `fk_testing_item_monthly_detail_testing_item_table` FOREIGN KEY (`testing_item_id`) REFERENCES `testing_item_tab` (`testing_item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for testing_item_quarterly_detail
-- ----------------------------
DROP TABLE IF EXISTS `testing_item_quarterly_detail`;
CREATE TABLE `testing_item_quarterly_detail` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `tag` varchar(50) NOT NULL,
  `message_id` varchar(100) NOT NULL,
  `record_id` int(10) NOT NULL,
  `day_str` varchar(50) DEFAULT NULL,
  `month_str` varchar(50) DEFAULT NULL,
  `year_str` varchar(50) DEFAULT NULL,
  `quarter_str` varchar(50) DEFAULT NULL,
  `half_year_str` varchar(50) DEFAULT NULL,
  `event` varchar(50) NOT NULL,
  `event_time` bigint(20) NOT NULL,
  `change_count` int(5) DEFAULT '1',
  `testing_item_order_income` bigint(20) DEFAULT '0',
  `testing_item_cash_income` bigint(20) DEFAULT '0',
  `testing_item_finance_confirm_income` bigint(20) DEFAULT '0',
  `testing_item_record_cost` bigint(20) DEFAULT '0',
  `testing_item_confirm_record_cost` bigint(20) DEFAULT '0',
  `order_id` varchar(255) NOT NULL,
  `testing_result` varchar(255) DEFAULT NULL,
  `testing_agency` varchar(50) DEFAULT NULL,
  `testing_item` varchar(255) DEFAULT NULL,
  `testing_item_id` int(10) NOT NULL,
  `testing_item_price` bigint(20) DEFAULT NULL,
  `testing_item_cost` bigint(20) unsigned DEFAULT NULL,
  `testing_agency_id` int(10) NOT NULL,
  `testing_agency_address` varchar(100) DEFAULT NULL,
  `testing_report_number` varchar(100) DEFAULT NULL,
  `testing_report_url` varchar(255) DEFAULT NULL,
  `testing_report_upload_time` bigint(20) unsigned DEFAULT '0',
  `sampling_time` bigint(20) unsigned DEFAULT NULL,
  `order_create_time` bigint(20) DEFAULT '0',
  `order_pay_time` bigint(20) DEFAULT '0',
  `sample_confirm_time` bigint(20) unsigned DEFAULT '0',
  `order_invoice_time` bigint(20) DEFAULT '0',
  `order_cancel_time` bigint(20) DEFAULT '0',
  `order_update_time` bigint(20) DEFAULT '0',
  `testing_item_cancel_count` bigint(20) DEFAULT '0',
  `testing_item_place_count` bigint(20) DEFAULT '0',
  `update_time` bigint(20) unsigned NOT NULL,
  `create_time` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_testing_item_quarterly_detail_testing_item_table` (`testing_item_id`),
  KEY `index_testing_item_quarterly_detail_order_id` (`order_id`),
  KEY `index_testing_item_quarterly_detail_quarter_str` (`quarter_str`),
  CONSTRAINT `fk_testing_item_quarterly_detail_testing_item_table` FOREIGN KEY (`testing_item_id`) REFERENCES `testing_item_tab` (`testing_item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for testing_item_tab
-- ----------------------------
DROP TABLE IF EXISTS `testing_item_tab`;
CREATE TABLE `testing_item_tab` (
  `testing_item_id` int(10) NOT NULL,
  `testing_item` varchar(255) NOT NULL,
  `testing_agency` varchar(50) DEFAULT NULL,
  `testing_agency_id` int(10) NOT NULL,
  `testing_agency_address` varchar(100) DEFAULT NULL,
  `update_time` bigint(20) unsigned NOT NULL,
  `create_time` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`testing_item_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for testing_item_yearly_detail
-- ----------------------------
DROP TABLE IF EXISTS `testing_item_yearly_detail`;
CREATE TABLE `testing_item_yearly_detail` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `tag` varchar(50) NOT NULL,
  `message_id` varchar(100) NOT NULL,
  `record_id` int(10) NOT NULL,
  `day_str` varchar(50) DEFAULT NULL,
  `month_str` varchar(50) DEFAULT NULL,
  `year_str` varchar(50) DEFAULT NULL,
  `quarter_str` varchar(50) DEFAULT NULL,
  `half_year_str` varchar(50) DEFAULT NULL,
  `event` varchar(50) NOT NULL,
  `event_time` bigint(20) NOT NULL,
  `change_count` int(5) DEFAULT '1',
  `testing_item_order_income` bigint(20) DEFAULT '0',
  `testing_item_cash_income` bigint(20) DEFAULT '0',
  `testing_item_finance_confirm_income` bigint(20) DEFAULT '0',
  `testing_item_record_cost` bigint(20) DEFAULT '0',
  `testing_item_confirm_record_cost` bigint(20) DEFAULT '0',
  `order_id` varchar(255) NOT NULL,
  `testing_result` varchar(255) DEFAULT NULL,
  `testing_agency` varchar(50) DEFAULT NULL,
  `testing_item` varchar(255) DEFAULT NULL,
  `testing_item_id` int(10) NOT NULL,
  `testing_item_price` bigint(20) DEFAULT NULL,
  `testing_item_cost` bigint(20) unsigned DEFAULT NULL,
  `testing_agency_id` int(10) NOT NULL,
  `testing_agency_address` varchar(100) DEFAULT NULL,
  `testing_report_number` varchar(100) DEFAULT NULL,
  `testing_report_url` varchar(255) DEFAULT NULL,
  `testing_report_upload_time` bigint(20) unsigned DEFAULT '0',
  `sampling_time` bigint(20) unsigned DEFAULT NULL,
  `order_create_time` bigint(20) DEFAULT '0',
  `order_pay_time` bigint(20) DEFAULT '0',
  `sample_confirm_time` bigint(20) unsigned DEFAULT '0',
  `order_invoice_time` bigint(20) DEFAULT '0',
  `order_cancel_time` bigint(20) DEFAULT '0',
  `order_update_time` bigint(20) DEFAULT '0',
  `testing_item_cancel_count` bigint(20) DEFAULT '0',
  `testing_item_place_count` bigint(20) DEFAULT '0',
  `update_time` bigint(20) unsigned NOT NULL,
  `create_time` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_testing_item_yearly_detail_testing_item_table` (`testing_item_id`),
  KEY `index_testing_item_yearly_detail_order_id` (`order_id`),
  KEY `index_testing_item_yearly_detail_year_str` (`year_str`),
  CONSTRAINT `fk_testing_item_yearly_detail_testing_item_table` FOREIGN KEY (`testing_item_id`) REFERENCES `testing_item_tab` (`testing_item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `phone_number` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `nickname` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `salt` varchar(255) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `activate` tinyint(1) NOT NULL DEFAULT '0',
  `update_time` bigint(20) NOT NULL,
  `create_time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `index_user_deleted` (`deleted`),
  KEY `index_user_phone_number` (`phone_number`),
  KEY `index_user_email` (`email`),
  KEY `index_user_nickname` (`nickname`),
  KEY `index_user_activate` (`activate`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (1, '18653889398', 'admin@viathink.com', 'admin', '3bdc32724b7b8e0a0fdffada684139ea230c6260', '12345', 0, 1, 1528269149000, 1528269149000);
INSERT INTO `user` VALUES (2, '18653889398', 'boss@viathink.com', 'boss', '3bdc32724b7b8e0a0fdffada684139ea230c6260', '12345', 0, 1, 1528269149000, 1528269149000);
INSERT INTO `user` VALUES (3, '18653889398', 'finance@viathink.com', 'finance', '3bdc32724b7b8e0a0fdffada684139ea230c6260', '12345', 0, 1, 1528269149000, 1528269149000);
COMMIT;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL,
  `role_id` int(10) unsigned NOT NULL,
  `update_time` bigint(20) NOT NULL,
  `create_time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_role_user` (`user_id`),
  KEY `fk_user_role_role` (`role_id`),
  CONSTRAINT `fk_user_role_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `fk_user_role_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
BEGIN;
INSERT INTO `user_role` VALUES (1, 1, 2, 1528269149000, 1528269149000);
INSERT INTO `user_role` VALUES (2, 2, 1, 1528269149000, 1528269149000);
INSERT INTO `user_role` VALUES (3, 3, 3, 1528269149000, 1528269149000);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
