-- 创建本系统的数据库时注意啦！
-- 首先，如果存在数据表 air_conditioner ，则先删除该数据表
DROP TABLE IF EXISTS `air_conditioner`;

-- 创建数据表 air_conditioner
CREATE TABLE `air_conditioner` (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `gmt_create` datetime NOT NULL COMMENT '数据创建(插入)时间',
  `gmt_modified` datetime NOT NULL COMMENT '数据最后更新时间',
  `equipment_number` char(12) NOT NULL COMMENT '设备编号',
  `brand` varchar(255) DEFAULT NULL COMMENT '品牌',
  `model` varchar(255) DEFAULT NULL COMMENT '型号',
  `seller` varchar(255) DEFAULT NULL COMMENT '销售方',
  `address` varchar(255) NOT NULL COMMENT '地址',
  `region_code` char(14) NOT NULL COMMENT '区域编码',
  `longitude` decimal(20, 14) NOT NULL COMMENT '地址经度',
  `latitude` decimal(20, 14) NOT NULL COMMENT '地址纬度',
  `temperature` int(11) NOT NULL COMMENT '温度',
  `wind_speed` varchar(255) NOT NULL DEFAULT 'STOP' COMMENT '风速',
  `kwh` decimal(20, 2) NOT NULL COMMENT '用电量',
  `current_intensity` decimal(10, 3) NOT NULL COMMENT '电流强度',
  `voltage` decimal(5, 2) NOT NULL COMMENT '电压',
  `power` decimal(20, 3) NOT NULL COMMENT '功率',
  `state` varchar(255) NOT NULL DEFAULT 'GOOD' COMMENT '设备状态 良好 or 故障',
  `fault_description` text NOT NULL COMMENT '故障描述，良好为空',
  `user_id` bigint(20) UNSIGNED NOT NULL COMMENT '设备的所有者',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_equipment_number` (`equipment_number`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;