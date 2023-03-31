/*
SQLyog Ultimate v11.22 (64 bit)
MySQL - 8.0.20 : Database - awakeningera
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`awakeningera` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `awakeningera`;

/*Table structure for table `appfile` */

DROP TABLE IF EXISTS `appfile`;

CREATE TABLE `appfile` (
  `fileid` bigint NOT NULL AUTO_INCREMENT COMMENT '文件id',
  `status` varchar(16) DEFAULT NULL COMMENT '状态',
  `userid` bigint NOT NULL COMMENT '上传人',
  `createtime` datetime DEFAULT NULL COMMENT '上传时间',
  `filename` varchar(128) DEFAULT NULL COMMENT '文件名',
  `extendname` varchar(16) DEFAULT NULL COMMENT '拓展名',
  `relationtype` varchar(16) DEFAULT NULL COMMENT '私信、头像、封面、插图',
  `relationid` bigint DEFAULT NULL COMMENT '文章id',
  PRIMARY KEY (`fileid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `appfile` */

insert  into `appfile`(`fileid`,`status`,`userid`,`createtime`,`filename`,`extendname`,`relationtype`,`relationid`) values (1,'1',1,'2022-02-02 02:19:03','58634672-42e7-4c14-9b9d-44cc5ecfbc9a.jpg','jpg','headshot',1),(2,'1',1,'2022-02-27 16:13:24','94c00be5-0a8e-4357-a6a7-086c8a41a2bc.png','png','headshot',1);

/*Table structure for table `article` */

DROP TABLE IF EXISTS `article`;

CREATE TABLE `article` (
  `articleid` bigint NOT NULL AUTO_INCREMENT,
  `status` varchar(16) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `userid` bigint DEFAULT NULL,
  `title` varchar(16) DEFAULT NULL,
  `body` varchar(8192) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`articleid`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `article` */

insert  into `article`(`articleid`,`status`,`createtime`,`userid`,`title`,`body`) values (1,'1','2022-02-04 18:56:52',1,'test','tesest'),(2,'1','2022-02-04 18:57:23',2,'test','test'),(3,'1','2022-02-04 18:57:31',3,'test','test'),(4,'1','2022-02-04 16:01:00',1,'觉醒年代之陈独秀《研究室与监狱》','<!DOCTYPE html>\n<html>\n<head>\n</head>\n<body>\n<p style=\"padding-left: 40px; text-align: left;\">世界文明的发源地有二：一是科学研究室。一是监狱。</p>\n<p style=\"padding-left: 40px; text-align: left;\">我们青年立志出了研究室就入监狱，出了监狱就入研究室，这才是人生最高尚优美的生活。</p>\n<p style=\"padding-left: 40px; text-align: left;\">从这两处发生的文明，才是真文明，才是有生命有价值的文明。</p>\n</body>\n</html>'),(5,'1','2022-02-22 08:16:34',1,'测试张三与hys私信','<!DOCTYPE html>\n<html>\n<head>\n</head>\n<body>\n<p>11111111</p>\n</body>\n</html>'),(6,'1','2022-02-24 08:08:39',5,'幸运咖的第一篇文章','<!DOCTYPE html>\n<html>\n<head>\n</head>\n<body>\n<p>测试111111</p>\n</body>\n</html>'),(7,'1','2022-02-24 08:10:05',5,'幸运咖的第二篇文章','<!DOCTYPE html>\n<html>\n<head>\n</head>\n<body>\n<p>测试自动跳转</p>\n</body>\n</html>'),(8,'1','2022-02-24 09:08:35',5,'幸运咖的第三篇文章','<!DOCTYPE html>\n<html>\n<head>\n</head>\n<body>\n<p>id</p>\n<p>bug</p>\n</body>\n</html>'),(9,'1','2022-02-24 09:11:32',5,'幸运咖的第四篇文章','<!DOCTYPE html>\n<html>\n<head>\n</head>\n<body>\n<p>测试修复id bug</p>\n</body>\n</html>'),(11,'1','2022-02-27 09:24:16',1,'第二次ES插入测试','<!DOCTYPE html>\n<html>\n<head>\n</head>\n<body>\n<p>id应为11</p>\n</body>\n</html>');

/*Table structure for table `branch_table` */

DROP TABLE IF EXISTS `branch_table`;

CREATE TABLE `branch_table` (
  `branch_id` bigint NOT NULL,
  `xid` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `transaction_id` bigint DEFAULT NULL,
  `resource_group_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `resource_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `branch_type` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  `client_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `application_data` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `gmt_create` datetime(6) DEFAULT NULL,
  `gmt_modified` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`branch_id`) USING BTREE,
  KEY `idx_xid` (`xid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

/*Data for the table `branch_table` */

/*Table structure for table `comment` */

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `commentid` bigint NOT NULL AUTO_INCREMENT,
  `status` varchar(16) DEFAULT NULL,
  `userid` bigint DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `type` varchar(16) DEFAULT NULL COMMENT '图片、文字',
  `articleid` bigint DEFAULT NULL COMMENT '文章id',
  `tocommentid` bigint DEFAULT NULL COMMENT '评论id',
  `body` varchar(128) DEFAULT NULL COMMENT '评论内容',
  PRIMARY KEY (`commentid`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `comment` */

insert  into `comment`(`commentid`,`status`,`userid`,`createtime`,`type`,`articleid`,`tocommentid`,`body`) values (1,'1',1,'2022-02-21 17:27:51','ordinary',1,NULL,'666'),(2,NULL,1,'2022-02-21 10:05:21','text',1,NULL,'666'),(3,NULL,1,'2022-02-21 10:07:58','text',1,NULL,'777'),(4,NULL,1,'2022-02-21 10:08:39','text',1,NULL,'888'),(5,NULL,1,'2022-02-21 10:09:19','text',1,NULL,'999'),(6,NULL,1,'2022-02-21 10:13:56','text',4,NULL,'good'),(7,NULL,1,'2022-02-21 10:30:27','text',1,NULL,'great'),(8,NULL,1,'2022-02-21 10:32:12','text',1,NULL,'good'),(9,NULL,1,'2022-02-21 10:33:24','text',1,NULL,'gre'),(10,NULL,1,'2022-02-21 10:34:48','text',4,NULL,'great'),(11,NULL,1,'2022-02-24 16:49:58','text',9,NULL,'ok'),(12,NULL,1,'2022-02-24 16:51:37','text',9,NULL,'ok');

/*Table structure for table `follow` */

DROP TABLE IF EXISTS `follow`;

CREATE TABLE `follow` (
  `followid` bigint NOT NULL AUTO_INCREMENT,
  `status` varchar(16) DEFAULT NULL,
  `userid` bigint DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `touserid` bigint DEFAULT NULL,
  PRIMARY KEY (`followid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `follow` */

insert  into `follow`(`followid`,`status`,`userid`,`createtime`,`touserid`) values (1,'1',1,'2022-02-19 15:02:21',2),(2,'2',1,'2022-02-21 15:54:39',1),(3,'1',4,'2022-02-22 08:27:12',1),(4,'1',5,'2022-02-24 09:02:58',1),(5,'1',1,'2022-02-24 10:19:14',5),(6,'1',3,'2022-02-28 06:48:04',1);

/*Table structure for table `global_table` */

DROP TABLE IF EXISTS `global_table`;

CREATE TABLE `global_table` (
  `xid` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `transaction_id` bigint DEFAULT NULL,
  `status` tinyint NOT NULL,
  `application_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `transaction_service_group` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `transaction_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `timeout` int DEFAULT NULL,
  `begin_time` bigint DEFAULT NULL,
  `application_data` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`xid`) USING BTREE,
  KEY `idx_gmt_modified_status` (`gmt_modified`,`status`) USING BTREE,
  KEY `idx_transaction_id` (`transaction_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

/*Data for the table `global_table` */

/*Table structure for table `hearten` */

DROP TABLE IF EXISTS `hearten`;

CREATE TABLE `hearten` (
  `heartenid` bigint NOT NULL AUTO_INCREMENT,
  `status` varchar(16) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `userid` bigint DEFAULT NULL,
  `relationtype` varchar(16) DEFAULT NULL COMMENT '文章或评论',
  `relationid` bigint DEFAULT NULL COMMENT '的id',
  PRIMARY KEY (`heartenid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `hearten` */

insert  into `hearten`(`heartenid`,`status`,`createtime`,`userid`,`relationtype`,`relationid`) values (1,'1','2022-02-21 14:53:29',1,'article',1),(3,'1','2022-02-22 08:26:32',1,'article',5),(4,'1','2022-02-22 08:27:02',4,'article',5),(5,'1','2022-02-24 16:43:41',1,'article',9);

/*Table structure for table `lock_table` */

DROP TABLE IF EXISTS `lock_table`;

CREATE TABLE `lock_table` (
  `row_key` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `xid` varchar(96) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `transaction_id` bigint DEFAULT NULL,
  `branch_id` bigint NOT NULL,
  `resource_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `table_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `pk` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`row_key`) USING BTREE,
  KEY `idx_branch_id` (`branch_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

/*Data for the table `lock_table` */

/*Table structure for table `message` */

DROP TABLE IF EXISTS `message`;

CREATE TABLE `message` (
  `messageid` bigint NOT NULL AUTO_INCREMENT,
  `userid` bigint DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `status` varchar(16) DEFAULT NULL,
  `messagetype` varchar(16) DEFAULT NULL COMMENT '文字、图片',
  `sessionid` bigint DEFAULT NULL,
  `messagebody` varchar(256) DEFAULT NULL COMMENT '消息内容',
  PRIMARY KEY (`messageid`)
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `message` */

insert  into `message`(`messageid`,`userid`,`createtime`,`status`,`messagetype`,`sessionid`,`messagebody`) values (1,1,'2022-02-19 15:15:24','1','text',1,'111'),(2,2,'2022-02-19 15:15:53','1','text',2,'222'),(3,1,'2022-02-19 09:19:22','1','text',1,'你好'),(4,1,'2022-02-19 09:22:30','1','text',1,'hi'),(5,1,'2022-02-19 09:29:47','1','text',1,'hei'),(6,1,'2022-02-19 09:31:53','1','text',1,''),(7,1,'2022-02-19 09:31:58','1','text',1,'hei'),(8,1,'2022-02-19 09:33:15','1','text',1,'hhh'),(9,2,'2022-02-19 09:36:15','1','text',1,'hhh'),(10,1,'2022-02-19 09:40:00','1','text',1,'111'),(11,1,'2022-02-19 09:40:45','1','text',1,'222'),(12,2,'2022-02-19 09:41:08','1','text',1,'777'),(13,2,'2022-02-19 09:43:14','1','text',1,'888'),(14,2,'2022-02-19 09:44:46','1','text',1,'999'),(15,2,'2022-02-19 09:45:21','1','text',1,'000'),(16,2,'2022-02-19 09:45:58','1','text',1,'789'),(17,2,'2022-02-19 09:46:19','1','text',1,'999'),(18,2,'2022-02-19 09:47:29','1','text',1,'ppp'),(19,1,'2022-02-19 09:48:01','1','text',1,'hello'),(20,1,'2022-02-19 09:49:12','1','text',1,'get'),(21,1,'2022-02-21 17:16:27','1','text',2,'333'),(22,1,'2022-02-21 17:18:41','1','text',2,'777'),(23,1,'2022-02-22 08:06:35','1','text',1,'你好'),(24,4,'2022-02-22 08:27:14','1','text',4,'你好'),(25,4,'2022-02-22 08:27:27','1','text',4,'111'),(26,1,'2022-02-22 08:37:39','1','text',4,'你好'),(27,1,'2022-02-22 08:37:54','1','text',4,'hello'),(28,1,'2022-02-22 08:38:37','1','text',4,'get'),(29,4,'2022-02-22 08:39:25','1','text',4,'.。。'),(30,4,'2022-02-22 08:49:50','1','text',4,'推送'),(31,4,'2022-02-22 08:50:48','1','text',4,'go'),(32,1,'2022-02-22 08:50:55','1','text',4,'good'),(33,4,'2022-02-22 09:00:51','1','text',4,'。'),(34,4,'2022-02-22 09:01:21','1','text',4,'。。'),(35,4,'2022-02-22 09:01:31','1','text',4,'1'),(36,4,'2022-02-22 09:02:56','1','text',4,'2'),(37,1,'2022-02-22 09:03:04','1','text',4,'1'),(38,1,'2022-02-22 09:03:59','1','text',4,'222'),(39,4,'2022-02-22 09:04:08','1','text',4,'333'),(40,4,'2022-02-22 09:04:15','1','text',4,'444'),(41,1,'2022-02-22 09:07:34','1','text',4,'999'),(42,4,'2022-02-22 09:07:42','1','text',4,'101010'),(43,4,'2022-02-22 09:08:38','1','text',4,'。。。'),(44,4,'2022-02-22 09:08:46','1','text',4,'999'),(45,4,'2022-02-22 09:13:39','1','text',4,'in'),(46,4,'2022-02-22 09:17:41','1','text',4,'g'),(47,4,'2022-02-22 09:17:46','1','text',4,'1'),(48,1,'2022-02-22 09:18:13','1','text',4,'1'),(49,4,'2022-02-22 09:18:48','1','text',4,'111'),(50,4,'2022-02-22 09:18:53','1','text',4,'333'),(51,5,'2022-02-24 08:11:27','1','text',5,'你好'),(52,1,'2022-02-24 08:11:36','1','text',5,'hello\n'),(53,5,'2022-02-24 08:11:59','1','text',5,'干嘛呢'),(54,1,'2022-02-24 08:12:12','1','text',5,'111'),(55,1,'2022-02-24 08:12:59','1','text',5,'111'),(56,5,'2022-02-24 08:13:04','1','text',5,'222'),(57,1,'2022-02-24 08:13:08','1','text',5,'333'),(58,1,'2022-02-24 08:18:34','1','text',5,'444'),(59,5,'2022-02-24 08:18:41','1','text',5,'555'),(60,1,'2022-02-24 08:18:47','1','text',5,'666'),(61,5,'2022-02-24 08:18:52','1','text',5,'777'),(62,1,'2022-02-24 08:18:56','1','text',5,'999'),(63,6,'2022-02-24 08:24:53','1','text',6,'你好'),(64,6,'2022-02-24 08:25:48','1','text',6,'你好'),(65,1,'2022-02-24 08:26:08','1','text',6,'hello'),(66,6,'2022-02-24 08:26:24','1','text',6,'嘿嘿嘿'),(67,1,'2022-02-24 08:27:47','1','text',5,'?'),(68,5,'2022-02-24 08:28:01','1','text',5,'.'),(69,1,'2022-02-24 08:30:21','1','text',5,'?'),(70,1,'2022-02-24 08:40:35','1','text',5,'好了吗'),(71,1,'2022-02-24 08:40:36','1','text',5,'好了吗'),(72,5,'2022-02-24 08:40:45','1','text',5,'好了'),(73,1,'2022-02-24 08:42:30','1','text',5,'good'),(76,1,'2022-02-24 09:12:03','1','text',5,'hello'),(77,1,'2022-02-24 09:12:34','1','text',5,''),(78,1,'2022-02-24 09:32:34','1','text',5,'??'),(79,1,'2022-02-24 09:35:11','1','text',5,'..'),(80,5,'2022-02-24 09:35:37','1','text',5,'..'),(81,1,'2022-02-24 10:14:27','1','text',5,'hello'),(82,5,'2022-02-24 10:14:57','1','text',5,'hello'),(83,5,'2022-03-01 10:02:57','1','text',1,'ooo'),(84,5,'2022-03-01 10:03:13','1','text',1,'aaa'),(85,5,'2022-03-01 10:09:49','1','text',1,'bbb'),(86,5,'2022-03-01 10:15:10','1','text',1,'LLL'),(87,1,'2022-03-01 10:38:17','1','text',9,'你好'),(88,1,'2022-03-01 10:39:05','1','text',9,'222'),(89,5,'2022-03-01 10:40:56','1','text',9,'大家好');

/*Table structure for table `notice` */

DROP TABLE IF EXISTS `notice`;

CREATE TABLE `notice` (
  `noticeid` bigint NOT NULL AUTO_INCREMENT,
  `status` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '已读、未读',
  `createtime` datetime DEFAULT NULL,
  `userid` bigint DEFAULT NULL,
  `sendtype` varchar(16) DEFAULT NULL COMMENT '系统/管理员',
  `touserid` bigint DEFAULT NULL,
  `bodytype` varchar(16) DEFAULT NULL COMMENT '点赞、评论、未读私信',
  `body` varchar(512) DEFAULT NULL COMMENT '内容',
  PRIMARY KEY (`noticeid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `notice` */

insert  into `notice`(`noticeid`,`status`,`createtime`,`userid`,`sendtype`,`touserid`,`bodytype`,`body`) values (1,'1','2022-02-21 14:24:31',1,'system',1,'hearten','xxx点赞了你的作品'),(2,'1','2022-02-24 10:19:16',1,'user',5,'follow','hys关注了你'),(3,'1','2022-02-24 16:43:41',1,'user',5,'hearten','hys点赞了你的文章'),(4,'1','2022-02-24 16:43:59',1,'user',5,'follow','hys关注了你'),(5,'1','2022-02-24 16:51:37',1,'user',5,'comment','hys评论了你'),(6,'1','2022-02-28 06:48:05',3,'user',1,'follow','小红关注了你');

/*Table structure for table `permission` */

DROP TABLE IF EXISTS `permission`;

CREATE TABLE `permission` (
  `permissionid` bigint NOT NULL AUTO_INCREMENT,
  `resource` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`permissionid`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `permission` */

insert  into `permission`(`permissionid`,`resource`) values (1,'/user/getusersbyids'),(2,'/session/getsessionsbyuserid'),(3,'/message/getmessagesbysessionid'),(4,'/follow/getfollowsbyuserid'),(5,'/message/sendmessage'),(6,'/notice/getnoticesbyuserid'),(7,'/user/update'),(8,'/actuator/**'),(9,'/auth/oauth/token'),(10,'/auth/rsa/publicKey'),(11,'/article/getarticle'),(12,'/comment/selectcomments'),(13,'/message/imserver/**'),(14,'/file/preview/**'),(15,'/user/register'),(16,'/comment/insertcomment'),(17,'/hearten/inserthearten'),(18,'/hearten/getheartensbyarticleid'),(19,'/follow/insertfollow'),(20,'/follow/getfollowbyuseridandtouserid'),(21,'/session/insertsession'),(22,'/article/insertarticle'),(23,'/file/updateheadshot'),(24,'/file/getheadshot'),(25,'/user/searchusersbyusername'),(26,'/session/searchsessionsbysessionname'),(27,'/session/joinsession'),(28,'/article/getarticleinelasticsearch');

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `roleid` bigint NOT NULL AUTO_INCREMENT,
  `rolename` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`roleid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `role` */

insert  into `role`(`roleid`,`rolename`) values (1,'PUBLIC'),(2,'ORDINARY'),(3,'ADMIN');

/*Table structure for table `role_permission` */

DROP TABLE IF EXISTS `role_permission`;

CREATE TABLE `role_permission` (
  `role_permissionid` bigint NOT NULL AUTO_INCREMENT,
  `roleid` bigint DEFAULT NULL,
  `permissionid` bigint DEFAULT NULL,
  PRIMARY KEY (`role_permissionid`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `role_permission` */

insert  into `role_permission`(`role_permissionid`,`roleid`,`permissionid`) values (1,2,1),(2,2,2),(3,2,3),(4,2,4),(5,2,5),(6,2,6),(7,1,8),(8,1,9),(9,1,10),(10,1,11),(11,1,12),(12,1,13),(13,1,14),(14,1,15),(16,2,7),(17,2,16),(18,2,17),(19,1,18),(20,2,18),(21,2,19),(22,2,20),(23,2,21),(24,2,22),(25,2,23),(26,1,23),(27,1,24),(28,1,25),(29,1,26),(30,2,27),(31,1,28);

/*Table structure for table `session` */

DROP TABLE IF EXISTS `session`;

CREATE TABLE `session` (
  `sessionid` bigint NOT NULL AUTO_INCREMENT,
  `status` varchar(16) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `userid` bigint DEFAULT NULL,
  `sessiontype` varchar(16) DEFAULT NULL COMMENT '私聊、群聊',
  `sessionname` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`sessionid`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `session` */

insert  into `session`(`sessionid`,`status`,`createtime`,`userid`,`sessiontype`,`sessionname`) values (1,'1','2022-02-18 23:27:30',1,'group','tmp'),(2,'1','2022-02-19 15:14:33',1,'group','tmp2'),(3,'1','2022-02-22 08:06:35',1,'private','private'),(4,'1','2022-02-22 08:27:14',4,'private','private'),(5,'1','2022-02-24 08:11:26',5,'private','private'),(6,'1','2022-02-24 08:24:53',6,'private','private'),(9,'1','2022-03-01 10:38:17',1,'group','创建一个新群聊');

/*Table structure for table `undo_log` */

DROP TABLE IF EXISTS `undo_log`;

CREATE TABLE `undo_log` (
  `branch_id` bigint NOT NULL COMMENT 'branch transaction id',
  `xid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'global transaction id',
  `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'undo_log context,such as serialization',
  `rollback_info` longblob NOT NULL COMMENT 'rollback info',
  `log_status` int NOT NULL COMMENT '0:normal status,1:defense status',
  `log_created` datetime(6) NOT NULL COMMENT 'create datetime',
  `log_modified` datetime(6) NOT NULL COMMENT 'modify datetime',
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='AT transaction mode undo table';

/*Data for the table `undo_log` */

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `userid` bigint NOT NULL AUTO_INCREMENT,
  `status` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '状态（未激活、正常、禁用）',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `username` varchar(32) NOT NULL COMMENT '唯一用户名',
  `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `phonenumber` varchar(16) DEFAULT NULL COMMENT '手机号',
  `headshotid` bigint DEFAULT NULL COMMENT '头像文件id',
  `description` varchar(1024) DEFAULT NULL COMMENT '简介',
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `user` */

insert  into `user`(`userid`,`status`,`createtime`,`username`,`password`,`email`,`phonenumber`,`headshotid`,`description`) values (1,'1','2022-01-28 22:53:56','hys','$2a$10$ORKihzinZBV708tP807vv.MgBCuRBcohusXUxQPMqtBeK4m262PPK','11@qq.com','123321',2,'我是hys'),(2,'1','2022-02-04 17:49:17','小明','$2a$10$ORKihzinZBV708tP807vv.MgBCuRBcohusXUxQPMqtBeK4m262PPK','22@qq.com','123321',1,'我是小明'),(3,'1','2022-02-04 17:50:18','小红','$2a$10$ORKihzinZBV708tP807vv.MgBCuRBcohusXUxQPMqtBeK4m262PPK','33@qq.com','123321',1,'我是小红'),(4,'1','2022-02-21 08:36:17','张三','$2a$10$mGDtTEmEmpG8Iz4b7.JkzedwXEfW5XQYQK.q9NBPK6S4kP37aZOVC','000@163.com','000',1,'我是张三'),(5,'1','2022-02-24 08:03:56','幸运咖','$2a$10$kT93H6oguz5NZ6JRLU6mIOiJeaszU6Yp.DqQmlsZI1CPK3bncMcCe','44@qq.com','123',1,NULL),(6,'1','2022-02-24 08:24:43','李赫宰','$2a$10$G4oUdLBHQeYAQlVCOohAPup27CCxBZ8dxSxDHuQ1lsM.TrcttDRzq','111','222',1,NULL),(7,'1','2022-03-02 09:27:37','test脏读2','$2a$10$3a4umRk4VQ2Q.d4o0hIH3.gUsBjq4Oqlb23sXdJC6/7iBJoYkikNa','1111','1111',1,NULL);

/*Table structure for table `user_role` */

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `user_roleid` bigint NOT NULL AUTO_INCREMENT,
  `userid` bigint DEFAULT NULL,
  `roleid` bigint DEFAULT NULL,
  PRIMARY KEY (`user_roleid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `user_role` */

insert  into `user_role`(`user_roleid`,`userid`,`roleid`) values (1,1,2),(2,2,2),(3,3,2),(4,4,2),(5,5,2),(6,6,2),(7,7,2);

/*Table structure for table `user_session` */

DROP TABLE IF EXISTS `user_session`;

CREATE TABLE `user_session` (
  `usid` bigint NOT NULL AUTO_INCREMENT,
  `userid` bigint DEFAULT NULL,
  `sessionid` bigint DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `status` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`usid`),
  UNIQUE KEY `UNIQUE` (`userid`,`sessionid`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `user_session` */

insert  into `user_session`(`usid`,`userid`,`sessionid`,`createtime`,`status`) values (1,1,1,'2022-02-18 23:29:25','1'),(2,2,1,'2022-02-18 23:29:29','1'),(3,3,1,'2022-02-18 23:29:31','1'),(4,1,2,'2022-02-19 15:16:22','1'),(7,4,4,'2022-02-22 08:27:14','1'),(8,1,4,'2022-02-22 08:27:14','1'),(9,5,5,'2022-02-24 08:11:26','1'),(10,1,5,'2022-02-24 08:11:26','1'),(11,6,6,'2022-02-24 08:24:53','1'),(12,1,6,'2022-02-24 08:24:53','1'),(17,5,1,'2022-03-01 10:02:40','1'),(18,1,9,'2022-03-01 10:38:17','1'),(19,5,9,'2022-03-01 10:40:44','1');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
