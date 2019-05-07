-- 创建本系统的数据库时注意啦！
-- 首先，如果存在数据表 user ，则先删除该数据表
DROP TABLE IF EXISTS `user`;

-- 创建数据表 user
CREATE TABLE `user`
(
    `id`           bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `gmt_create`   datetime            NOT NULL COMMENT '数据创建(插入)时间',
    `gmt_modified` datetime            NOT NULL COMMENT '数据最后更新时间',
    `username`     char(20)                     DEFAULT NULL,
    `password`     char(102)           NOT NULL,
    `email`        char(30)            NOT NULL,
    `phone_number` char(11)                     DEFAULT NULL,
    `is_activated` tinyint(1) unsigned NOT NULL DEFAULT '0',
    `role`         varchar(20)         NOT NULL DEFAULT 'ORDINARY',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_email` (`email`),
    UNIQUE KEY `uk_username` (`username`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;




