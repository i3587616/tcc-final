DROP TABLE IF EXISTS account_points;
CREATE TABLE `account_points` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '�����˺�ID',
  `account_no` varchar(30) NOT NULL COMMENT '�û��˺�',
  `total` int(11) COMMENT '��������',
  `version` int(11) DEFAULT 0 NOT NULL COMMENT '�汾��',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '����ʱ��',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='�˻����ֱ�';

INSERT INTO `account_points`(account_no, total) VALUE (20100001,200);
INSERT INTO `account_points`(account_no, total) VALUE (20100002,1000);

DROP TABLE IF EXISTS account_points_tccfinal;
CREATE TABLE `account_points_tccfinal` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'tcc�˻�����ҵ���¼��Id',
  `account_no` varchar(30) NOT NULL COMMENT '�û��˺�',
  `points` int(11) COMMENT '����ҵ���û���',
  `tcc_id` VARCHAR(30) NOT NULL COMMENT 'tcc����ID',
  `version` int(11) DEFAULT 0 NOT NULL COMMENT '�汾��',
  `status` CHAR(1) NOT NULL COMMENT 'ҵ��״̬��0-try��1-confirm��2-cancel',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '����ʱ��',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='�˻����ֱ�';