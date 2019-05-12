USE `elevator_air_conditioner_online_monitoring_system`;

-- 创建本系统的数据库时注意啦！
-- 首先，如果存在数据表 repair ，则先删除该数据表
DROP TABLE IF EXISTS `repair`;

-- 创建数据表 repair
CREATE TABLE `repair` (
    `id`             bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `gmt_create`     datetime            NOT NULL COMMENT '数据创建(插入)时间',
    `gmt_modified`   datetime            NOT NULL COMMENT '数据最后更新时间',
    `result`         varchar(255)        NOT NULL COMMENT '维修结果',
    `material`       text COMMENT '维修材料',
    `record`         text                NOT NULL COMMENT '维修记录',
    `fault_id`       bigint(20) UNSIGNED NOT NULL COMMENT '故障id',
    `repair_user_id` bigint(20) UNSIGNED NOT NULL COMMENT '维修者id',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;