-- 创建本系统的数据库时注意啦！
-- 首先，如果存在数据库 elevator_air_conditioner_online_monitoring_system ，则先删除该数据库
DROP DATABASE IF EXISTS `elevator_air_conditioner_online_monitoring_system`;

-- 创建数据库 elevator_air_conditioner_online_monitoring_system ，并指定字符集为 'utf8mb4'
CREATE DATABASE `elevator_air_conditioner_online_monitoring_system` CHARACTER SET 'utf8mb4';

USE `elevator_air_conditioner_online_monitoring_system`;

-- 创建本系统的数据库时注意啦！
-- 首先，如果存在数据表 user ，则先删除该数据表
DROP TABLE IF EXISTS `user`;

-- 创建数据表 user
CREATE TABLE `user` (
    `id`           bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `gmt_create`   datetime            NOT NULL COMMENT '数据创建(插入)时间',
    `gmt_modified` datetime            NOT NULL COMMENT '数据最后更新时间',
    `username`     char(20)                     DEFAULT NULL,
    `password`     char(102)           NOT NULL,
    `email`        char(30)            NOT NULL,
    `phone_number` char(11)                     DEFAULT NULL,
    `is_activated` tinyint(1) UNSIGNED NOT NULL DEFAULT '0',
    `role`         varchar(20)         NOT NULL DEFAULT 'ORDINARY',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_email` (`email`),
    UNIQUE KEY `uk_username` (`username`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- 创建本系统的数据库时注意啦！
-- 首先，如果存在数据表 air_conditioner ，则先删除该数据表
DROP TABLE IF EXISTS `air_conditioner`;

-- 创建数据表 air_conditioner
CREATE TABLE `air_conditioner` (
    `id`                bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `gmt_create`        datetime            NOT NULL COMMENT '数据创建(插入)时间',
    `gmt_modified`      datetime            NOT NULL COMMENT '数据最后更新时间',
    `equipment_id`      char(12)            NOT NULL COMMENT '设备编号',
    `brand`             varchar(255)                 DEFAULT NULL COMMENT '品牌',
    `model`             varchar(255)                 DEFAULT NULL COMMENT '型号',
    `seller`            varchar(255)                 DEFAULT NULL COMMENT '销售方',
    `address_string`    varchar(255)        NOT NULL COMMENT '字符串地址',
    `region_code`       varchar(255)        NOT NULL COMMENT '区域编码',
    `longitude`         decimal(20, 14)     NOT NULL COMMENT '地址经度',
    `latitude`          decimal(20, 14)     NOT NULL COMMENT '地址纬度',
    `temperature`       int(11)             NOT NULL COMMENT '温度',
    `wind_speed`        varchar(255)        NOT NULL DEFAULT 'STOP' COMMENT '风速',
    `kwh`               decimal(20, 2)      NOT NULL COMMENT '用电量',
    `current_intensity` decimal(10, 3)      NOT NULL COMMENT '电流强度',
    `voltage`           decimal(5, 2)       NOT NULL COMMENT '电压',
    `power`             decimal(20, 3)      NOT NULL COMMENT '功率',
    `state`             varchar(255)        NOT NULL DEFAULT 'GOOD' COMMENT '设备状态 良好 or 故障',
    `fault_description` text COMMENT '故障描述，良好为空',
    `user_id`           bigint(20) UNSIGNED NOT NULL COMMENT '设备的所有者',
    `address_id`        bigint(20) UNSIGNED NOT NULL COMMENT '设备的地址',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_equipment_id` (`equipment_id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- 创建本系统的数据库时注意啦！
-- 首先，如果存在数据表 address ，则先删除该数据表
DROP TABLE IF EXISTS `address`;

-- 创建数据表 address
CREATE TABLE `address` (
    `id`           bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `gmt_create`   datetime            NOT NULL COMMENT '数据创建(插入)时间',
    `gmt_modified` datetime            NOT NULL COMMENT '数据最后更新时间',
    `province`     varchar(255)        NOT NULL,
    `city`         varchar(255)        NOT NULL,
    `district`     varchar(255)        NOT NULL,
    `detail`       varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- 创建本系统的数据库时注意啦！
-- 首先，如果存在数据表 fault ，则先删除该数据表
DROP TABLE IF EXISTS `fault`;

-- 创建数据表 fault
CREATE TABLE `fault` (
    `id`                 bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `gmt_create`         datetime            NOT NULL COMMENT '数据创建(插入)时间',
    `gmt_modified`       datetime            NOT NULL COMMENT '数据最后更新时间',
    `type`               varchar(255)        NOT NULL COMMENT '故障类型',
    `description`        text                NOT NULL COMMENT '故障描述',
    `real_name`          varchar(255)        NOT NULL COMMENT '报修者真实姓名',
    `contact_address`    varchar(255)        NOT NULL COMMENT '报修者联系地址',
    `phone_number`       varchar(255)        NOT NULL COMMENT '报修者电话号码',
    `handle_result`      varchar(255)        NOT NULL COMMENT '故障处理结果',
    `air_conditioner_id` bigint(20) UNSIGNED NOT NULL COMMENT '故障设备id',
    `report_user_id`     bigint(20) UNSIGNED NOT NULL COMMENT '报修者id',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;



-- 创建本系统的数据库时注意啦！
-- 首先，如果存在数据表 fault_allocation ，则先删除该数据表
DROP TABLE IF EXISTS `fault_allocation`;

-- 创建数据表 fault_allocation
CREATE TABLE `fault_allocation` (
    `id`                 bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `gmt_create`         datetime            NOT NULL COMMENT '数据创建(插入)时间',
    `gmt_modified`       datetime            NOT NULL COMMENT '数据最后更新时间',
    `fault_id`           bigint(20) UNSIGNED NOT NULL COMMENT '故障id',
    `repair_user_id`     bigint(20) UNSIGNED NOT NULL COMMENT '维修者id',
    `allocation_user_id` bigint(20) UNSIGNED NOT NULL COMMENT '分配者id',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- 创建本系统的数据库时注意啦！
-- 首先，如果存在数据表 repair ，则先删除该数据表
DROP TABLE IF EXISTS `repair`;

-- 创建数据表 repair
CREATE TABLE `repair` (
    `id`             bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `gmt_create`     datetime            NOT NULL COMMENT '数据创建(插入)时间',
    `gmt_modified`   datetime            NOT NULL COMMENT '数据最后更新时间',
    `result`         varchar(255)        NOT NULL COMMENT '维修结果',
    `material`       text                NOT NULL COMMENT '维修材料',
    `record`         text                NOT NULL COMMENT '维修记录',
    `fault_id`       bigint(20) UNSIGNED NOT NULL COMMENT '故障id',
    `repair_user_id` bigint(20) UNSIGNED NOT NULL COMMENT '维修者id',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;






