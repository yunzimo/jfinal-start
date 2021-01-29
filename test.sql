/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50557
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50557
File Encoding         : 65001

Date: 2021-01-29 11:23:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `hide`
-- ----------------------------
DROP TABLE IF EXISTS `hide`;
CREATE TABLE `hide` (
  `id` int(15) NOT NULL AUTO_INCREMENT,
  `field` varchar(20) DEFAULT NULL,
  `desc` varchar(20) DEFAULT NULL,
  `view_name` varchar(20) DEFAULT NULL,
  `checked_col` int(10) DEFAULT NULL,
  `order_num` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hide
-- ----------------------------
INSERT INTO `hide` VALUES ('1', 'id', 'ID', 'user', '1', '1');
INSERT INTO `hide` VALUES ('2', 'name', '姓名', 'user', '1', '2');
INSERT INTO `hide` VALUES ('3', 'password', '密码', 'user', '0', '3');
INSERT INTO `hide` VALUES ('4', 'email', '邮箱', 'user', '1', '4');
INSERT INTO `hide` VALUES ('5', 'age', '年龄', 'user', '0', '5');
INSERT INTO `hide` VALUES ('6', 'sex', '性别', 'user', '0', '6');
INSERT INTO `hide` VALUES ('7', 'birthday', '生日', 'user', '1', '7');
INSERT INTO `hide` VALUES ('8', 'remark', '备注', 'user', '0', '8');
INSERT INTO `hide` VALUES ('9', 'orgId', '部门', 'user', '1', '9');
INSERT INTO `hide` VALUES ('10', 'addtime', '添加时间', 'user', '0', '10');
INSERT INTO `hide` VALUES ('12', 'operate', '操作', 'user', '1', '12');

-- ----------------------------
-- Table structure for `org`
-- ----------------------------
DROP TABLE IF EXISTS `org`;
CREATE TABLE `org` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `url` varchar(15) DEFAULT NULL,
  `remark` varchar(20) DEFAULT NULL,
  `pid` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of org
-- ----------------------------
INSERT INTO `org` VALUES ('1', '中孚信息', null, null, '0');
INSERT INTO `org` VALUES ('2', '研发能力中心', null, null, '1');
INSERT INTO `org` VALUES ('3', '系统设计部', null, null, '2');
INSERT INTO `org` VALUES ('4', '数据分析能力中心', null, null, '2');
INSERT INTO `org` VALUES ('5', '数据采集能力中心', null, null, '2');
INSERT INTO `org` VALUES ('6', '基础平台组', null, null, '5');
INSERT INTO `org` VALUES ('7', '应用分析组', null, null, '5');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `pwd` varchar(20) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `age` int(10) DEFAULT NULL,
  `sex` int(10) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `remark` longtext,
  `org_id` int(10) DEFAULT NULL,
  `addtime` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '李小六', '123128', '1233@qq.com', '12', '1', '1974-06-13', '<img src=\"http://localhost:8080/plugins/kindeditor/plugins/emoticons/images/9.gif\" border=\"0\" alt=\"\" />', '6', null);
INSERT INTO `user` VALUES ('2', '王六', '23333', null, '15', '1', '2021-01-17', null, '6', null);
INSERT INTO `user` VALUES ('3', '赵四', '2233', null, '14', '1', null, null, '6', null);
INSERT INTO `user` VALUES ('32', '王晓晓', '22333', null, '22', '0', null, null, '7', null);
INSERT INTO `user` VALUES ('33', '刘晓晓', '123123', '1223@qq.com', '22', '0', '2021-01-14', '我喜欢天天看书', '7', null);
INSERT INTO `user` VALUES ('58', '李甜甜', '123123', '12312351@qq.com', '12', '0', '2021-01-12', null, '6', '2021-01-28');
INSERT INTO `user` VALUES ('59', '天天', '123123', 'vase@qq.com', '12', '0', '2021-01-14', null, '7', '2021-01-28');

-- ----------------------------
-- Function structure for `getChildLst`
-- ----------------------------
DROP FUNCTION IF EXISTS `getChildLst`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `getChildLst`(rootId INT,tableName VARCHAR(255)) RETURNS varchar(1000) CHARSET utf8
BEGIN
   DECLARE sTemp VARCHAR(1000);
   DECLARE sTempChd VARCHAR(1000);
 
  SET sTemp = '$';
   SET sTempChd =cast(rootId as CHAR);
 
 
  WHILE sTempChd is not null DO
    SET sTemp = concat(sTemp,',',sTempChd);
    IF tableName = 'sys_org' THEN
       SELECT group_concat(id) INTO sTempChd FROM sys_org where FIND_IN_SET(pid,sTempChd)>0;
    ELSEIF tableName = 'sys_menu' THEN
       SELECT group_concat(id) INTO sTempChd FROM sys_menu where FIND_IN_SET(pid,sTempChd)>0;
    END IF;
  END WHILE;
  RETURN REPLACE(sTemp,'$,','');
 END
;;
DELIMITER ;
