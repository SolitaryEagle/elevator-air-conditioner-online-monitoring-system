-- 创建本系统的数据库时注意啦！
-- 首先，如果存在数据表 fault ，则先删除该数据表
DROP TABLE IF EXISTS `fault`;

-- 创建数据表 fault
CREATE TABLE `fault` (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `gmt_create` datetime NOT NULL COMMENT '数据创建(插入)时间',
  `gmt_modified` datetime NOT NULL COMMENT '数据最后更新时间',
  `is_handled` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '处理状态，表示该记录是否已被处理',
  `description` text NOT NULL COMMENT '故障描述',
  `fault_type` varchar(255) NOT NULL COMMENT '故障类型',
  `air_conditioner_id` bigint(20) UNSIGNED NOT NULL COMMENT '故障设备id',
  `report_user_id` bigint(20) UNSIGNED NOT NULL COMMENT '报修者id',
  `repair_user_id` bigint(20) UNSIGNED NOT NULL COMMENT '维修者id',
  `repair_result` text DEFAULT NULL COMMENT '维修结果',
  `repair_record` text DEFAULT NULL COMMENT '维修记录',
  `gmt_repair` datetime DEFAULT NULL COMMENT '维修时间',
  `repair_material` text DEFAULT NULL COMMENT '维修材料',
  `revisit_user_id` bigint(20) UNSIGNED NOT NULL COMMENT '回访者id',
  `revisit_result` text DEFAULT NULL COMMENT '回访结果',
  `gmt_revisit` datetime DEFAULT NULL COMMENT '回访时间',
  `evaluation_user_id` bigint(20) UNSIGNED NOT NULL COMMENT '评价者id',
  `user_evaluation` varchar(255) DEFAULT NULL COMMENT '用户评价',
  `user_satisfaction` tinyint(4) DEFAULT NULL COMMENT '用户满意度',
  `gmt_evaluation` datetime DEFAULT NULL COMMENT '评价时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;