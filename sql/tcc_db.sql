DROP TABLE IF EXISTS tcc_ucc;
CREATE TABLE `tcc_ucc` (
  `ucc_sign` varchar(100) NOT NULL COMMENT 'ucc方法唯一识别',
  `tcc_id` VARCHAR(30) NOT NULL COMMENT 'tcc事务ID',
  `tcc_ver` int(11) DEFAULT 0 NOT NULL COMMENT 'ucc版本号',
  `status` CHAR(1) NOT NULL COMMENT '业务状态：0-try，1-confirm，2-cancel',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`tcc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='tcc事务ucc表';

DROP TABLE IF EXISTS tcc_bs;
CREATE TABLE `tcc_bs` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `ucc_sign` varchar(100) NOT NULL COMMENT 'ucc方法唯一识别',
  `bs_actors` varbinary(1000) DEFAULT NULL COMMENT '业务参与者集合',
  `ver` int(11) DEFAULT 0 NOT NULL COMMENT 'ucc版本号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='tcc事务bs信息表';