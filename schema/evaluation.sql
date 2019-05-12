USE `elevator_air_conditioner_online_monitoring_system`;

-- 创建本系统的数据库时注意啦！
-- 首先，如果存在数据表 evaluation ，则先删除该数据表
DROP TABLE IF EXISTS `evaluation`;

-- 创建数据表 evaluation
CREATE TABLE `evaluation` (
    `id`                 bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `gmt_create`         datetime            NOT NULL COMMENT '数据创建(插入)时间',
    `gmt_modified`       datetime            NOT NULL COMMENT '数据最后更新时间',
    `content`            text                NOT NULL COMMENT '评价内容',
    `satisfaction`       tinyint(4) DEFAULT NULL COMMENT '满意度',
    `fault_id`           bigint(20) UNSIGNED NOT NULL COMMENT '故障id',
    `evaluation_user_id` bigint(20) UNSIGNED NOT NULL COMMENT '评价者id',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;