DROP TABLE IF EXISTS account_pay;
CREATE TABLE `account_pay` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '֧���˺�ID',
  `account_no` varchar(30) NOT NULL COMMENT '�û��˺�',
  `amount` decimal(20,2) DEFAULT 0 NOT NULL COMMENT '�˻����',
  `version` int(11) DEFAULT 0 NOT NULL COMMENT '�汾��',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '����ʱ��',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='�˻��ʽ��';

INSERT INTO `account_pay`(account_no, amount) VALUE (20100001,600);
INSERT INTO `account_pay`(account_no, amount) VALUE (20100002,2000);

DROP TABLE IF EXISTS account_pay_tccfinal;
CREATE TABLE `account_pay_tccfinal` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'tcc�˻��ʽ�ҵ���¼��Id',
  `account_no` varchar(30) NOT NULL COMMENT '֧���˺�',
  `pay_amount` decimal(20,2) DEFAULT 0 NOT NULL COMMENT '֧�����',
  `tcc_id` VARCHAR(30) NOT NULL COMMENT 'tcc����ID',
  `version` int(11) DEFAULT 0 NOT NULL COMMENT '�汾��',
  `status` CHAR(1) NOT NULL COMMENT 'ҵ��״̬��0-try��1-confirm��2-cancel',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '����ʱ��',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='�˻��ʽ��';