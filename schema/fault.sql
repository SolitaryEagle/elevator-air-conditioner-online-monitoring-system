USE `elevator_air_conditioner_online_monitoring_system`;

-- 创建本系统的数据库时注意啦！
-- 首先，如果存在数据表 fault ，则先删除该数据表
DROP TABLE IF EXISTS `fault`;

-- 创建数据表 fault
CREATE TABLE `fault` (
    `id`                 bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `gmt_create`         datetime            NOT NULL COMMENT '数据创建(插入)时间',
    `gmt_modified`       datetime            NOT NULL COMMENT '数据最后更新时间',
    `type`               varchar(255)        NOT NULL COMMENT '故障类型',
    `state`              varchar(255)        NOT NULL COMMENT '故障状态',
    `description`        text                NOT NULL COMMENT '故障描述',
    `real_name`          varchar(255)        NOT NULL COMMENT '报修者真实姓名',
    `contact_address`    varchar(255)        NOT NULL COMMENT '报修者联系地址',
    `phone_number`       varchar(255)        NOT NULL COMMENT '报修者电话号码',
    `repair_result`      varchar(255) COMMENT '故障处理结果',
    `air_conditioner_id` bigint(20) UNSIGNED NOT NULL COMMENT '故障设备id',
    `report_user_id`     bigint(20) UNSIGNED NOT NULL COMMENT '报修者id',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;