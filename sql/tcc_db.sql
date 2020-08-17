DROP TABLE IF EXISTS tcc_ucc;
CREATE TABLE `tcc_ucc` (
  `ucc_sign` varchar(100) NOT NULL COMMENT 'ucc����Ψһʶ��',
  `tcc_id` VARCHAR(30) NOT NULL COMMENT 'tcc����ID',
  `tcc_ver` int(11) DEFAULT 0 NOT NULL COMMENT 'ucc�汾��',
  `status` CHAR(1) NOT NULL COMMENT 'ҵ��״̬��0-try��1-confirm��2-cancel',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '����ʱ��',
  PRIMARY KEY (`tcc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='tcc����ucc��';

DROP TABLE IF EXISTS tcc_bs;
CREATE TABLE `tcc_bs` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `ucc_sign` varchar(100) NOT NULL COMMENT 'ucc����Ψһʶ��',
  `bs_actors` varbinary(1000) DEFAULT NULL COMMENT 'ҵ������߼���',
  `ver` int(11) DEFAULT 0 NOT NULL COMMENT 'ucc�汾��',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '����ʱ��',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='tcc����bs��Ϣ��';