-- 创建本系统的数据库时注意啦！
-- 首先，如果存在数据表 address ，则先删除该数据表
DROP TABLE IF EXISTS `address`;

-- 创建数据表 address
CREATE TABLE `address`
(
    `id`           bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `gmt_create`   datetime            NOT NULL COMMENT '数据创建(插入)时间',
    `gmt_modified` datetime            NOT NULL COMMENT '数据最后更新时间',
    `province`     varchar(255)        NOT NULL DEFAULT '',
    `city`         varchar(255)        NOT NULL DEFAULT '',
    `district`     varchar(255)        NOT NULL DEFAULT '',
    `detail`       varchar(255)                 DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;




