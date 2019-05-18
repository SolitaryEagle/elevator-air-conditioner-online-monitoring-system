USE `elevator_air_conditioner_online_monitoring_system`;

-- 创建本系统的数据库时注意啦！
-- 首先，如果存在数据表 allocation ，则先删除该数据表
DROP TABLE IF EXISTS `allocation`;

-- 创建数据表 allocation
CREATE TABLE `allocation` (
    `id`                 bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `gmt_create`         datetime            NOT NULL COMMENT '数据创建(插入)时间',
    `gmt_modified`       datetime            NOT NULL COMMENT '数据最后更新时间',
    `fault_id`           bigint(20) UNSIGNED NOT NULL COMMENT '故障id',
    `repair_user_id`     bigint(20) UNSIGNED NOT NULL COMMENT '维修者id',
    `allocation_user_id` bigint(20) UNSIGNED NOT NULL COMMENT '分配者id',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;