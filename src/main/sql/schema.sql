-- 创建主机信息表，并创建测试数据
-- id  ip  user_name password  gmt_create  gmt_modify operator is_deleted
drop DATABASE middleware_operation;
CREATE DATABASE middleware_operation  DEFAULT CHARACTER SET utf8;
use middleware_operation;
CREATE TABLE `server` (`id`  BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                       `ip` VARCHAR(256),
                       `user_name` VARCHAR(256),
                       `password` VARCHAR(256),
                       `description` VARCHAR(256),
                       `gmt_create` DATETIME,
                       `gmt_modify` DATETIME,
                       `operator` VARCHAR(256),
                       `is_deleted` INT UNSIGNED,
  PRIMARY KEY(`id`,`ip`)
);
INSERT INTO `server` (`ip`,`user_name`,`password`,`description`,`gmt_create`,`operator`,`is_deleted`) VALUES
  ('192.168.0.1','root','hit123456','张旭测试账号01',NOW(),'张旭',0);

INSERT INTO `server` (`ip`,`user_name`,`password`,`description`,`gmt_create`,`operator`,`is_deleted`) VALUES
  ('192.168.0.2','root','hit123456','张旭测试账号02',NOW(),'张旭',0);

