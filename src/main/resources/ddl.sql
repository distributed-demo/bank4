CREATE TABLE `bank` (
  `id` int(11) NOT NULL,
  `money` decimal(10,0) DEFAULT NULL,
  `frozen` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `bytejta` (
  `xid` varchar(32) NOT NULL,
  `gxid` varchar(40) DEFAULT NULL,
  `bxid` varchar(40) DEFAULT NULL,
  `ctime` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`xid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `bank` (`id`, `money`, `frozen`) VALUES ('1', '10000', '0');

# 涉及分布式事务的数据库必须创建bytejta表
