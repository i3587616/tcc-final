DROP TABLE IF EXISTS account_pay;
CREATE TABLE `account_pay` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '支付账号ID',
  `account_no` varchar(30) NOT NULL COMMENT '用户账号',
  `amount` decimal(20,2) DEFAULT 0 NOT NULL COMMENT '账户金额',
  `version` int(11) DEFAULT 0 NOT NULL COMMENT '版本号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账户资金表';

INSERT INTO `account_pay`(account_no, amount) VALUE (20100001,600);
INSERT INTO `account_pay`(account_no, amount) VALUE (20100002,2000);

DROP TABLE IF EXISTS account_pay_tccfinal;
CREATE TABLE `account_pay_tccfinal` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'tcc账户资金业务记录表Id',
  `account_no` varchar(30) NOT NULL COMMENT '支付账号',
  `pay_amount` decimal(20,2) DEFAULT 0 NOT NULL COMMENT '支付金额',
  `tcc_id` VARCHAR(30) NOT NULL COMMENT 'tcc事务ID',
  `version` int(11) DEFAULT 0 NOT NULL COMMENT '版本号',
  `status` CHAR(1) NOT NULL COMMENT '业务状态：0-try，1-confirm，2-cancel',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账户资金表';