CREATE TABLE `mapi_user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自动编号',
  `username` varchar(100) NOT NULL DEFAULT '' COMMENT '用户名',
  `nickname` varchar(100) NOT NULL DEFAULT '' COMMENT '昵称',
  `password` varchar(50) NOT NULL DEFAULT '' COMMENT '密码',
   `email` varchar(50) NOT NULL DEFAULT '' COMMENT '邮箱',
  `last_login_time` datetime NOT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(30) NOT NULL DEFAULT '' COMMENT '最后登录IP',
  `is_lock` CHAR(1) NOT NULL DEFAULT 'N' COMMENT '是否锁定N：未锁定 Y：锁定',
  `head_image` varchar(100) NOT NULL DEFAULT '' COMMENT '用户头像',
  `create_time` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_mapi_user_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=1DEFAULT CHARSET=utf8 COMMENT='用户表';