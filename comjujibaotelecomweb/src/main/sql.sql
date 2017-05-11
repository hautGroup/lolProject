#用户基本信息表:
CREATE TABLE `user` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自动编号',
  `nickname` VARCHAR (256) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户昵称',
  `password` CHAR (100) COLLATE utf8mb4_bin DEFAULT NULL,
  `email` VARCHAR (64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户email地址',
  `status` VARCHAR (16) COLLATE utf8mb4_bin NOT NULL DEFAULT 'active' COMMENT '用户状态：active/locked',
  `head_image` VARCHAR (1024) COLLATE utf8mb4_bin DEFAULT NULL,
  `bind_phone` VARCHAR (16) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '绑定手机',
  `last_login_time` datetime NOT NULL COMMENT '最后登录时间',
  `last_login_ip` VARCHAR (15) COLLATE utf8mb4_bin NOT NULL COMMENT '最后登录IP地址',
  `is_notfiy_sign` CHAR (1) COLLATE utf8mb4_bin DEFAULT 'Y' COMMENT '是否开启签到提醒',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_bin;


#用户登录日志表:
CREATE TABLE `user_login_log` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自动编号',
  `user_id` int(11) DEFAULT NULL,
  `platform` varchar(50) DEFAULT NULL,
  `device_number` varchar(50) DEFAULT NULL,
  `login_ip` varchar(50) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


ALTER TABLE `user`
  ADD UNIQUE INDEX `uidx_user_email` (`email`) ;


#用户签到记录
CREATE TABLE `jjb_user_sign_in_record` (
 `id` int(11) NOT NULL AUTO_INCREMENT,
 `user_id` int(10) unsigned NOT NULL,
 `series_signed_times` int(10) DEFAULT '0' COMMENT '连续签到次数',
 `create_time` datetime NOT NULL COMMENT '创建时间',
 `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
 PRIMARY KEY (`id`),
 KEY `idx_jjb_user_sign_in_record_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户签到记录'