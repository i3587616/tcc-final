DROP TABLE IF EXISTS account_points;
CREATE TABLE `account_points` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '积分账号ID',
  `account_no` varchar(30) NOT NULL COMMENT '用户账号',
  `total` int(11) COMMENT '积分数量',
  `version` int(11) DEFAULT 0 NOT NULL COMMENT '版本号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账户积分表';

INSERT INTO `account_points`(account_no, total) VALUE (20100001,200);
INSERT INTO `account_points`(account_no, total) VALUE (20100002,1000);

DROP TABLE IF EXISTS account_points_tccfinal;
CREATE TABLE `account_points_tccfinal` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'tcc账户积分业务记录表Id',
  `account_no` varchar(30) NOT NULL COMMENT '用户账号',
  `points` int(11) COMMENT '本次业务获得积分',
  `tcc_id` VARCHAR(30) NOT NULL COMMENT 'tcc事务ID',
  `version` int(11) DEFAULT 0 NOT NULL COMMENT '版本号',
  `status` CHAR(1) NOT NULL COMMENT '业务状态：0-try，1-confirm，2-cancel',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账户积分表';