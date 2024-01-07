DROP DATABASE IF EXISTS reviewitdb;
CREATE DATABASE IF NOT EXISTS reviewitdb;

USE reviewitdb;

CREATE TABLE product (
    `id` INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `title` VARCHAR(120) NOT NULL,
);

-- CREATE TABLE `product_source` (
--     `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
--     `product_id` INTEGER NOT NULL,
--     `name` VARCHAR(120) NOT NULL,
--     `url` VARCHAR(2500) NOT NULL,
--     `company_name` VARCHAR(120),
--     FOREIGN KEY (`product_id`) REFERENCES `product` (`id`);
-- );

-- CREATE TABLE `category` (
--     `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
--     `name` VARCHAR(120) NOT NULL,
-- );

-- CREATE TABLE `product_category` (
--     `product_id` INTEGER PRIMARY KEY,
--     `category_id` INTEGER PRIMARY KEY,
--     FOREIGN KEY (`product_id`) REFERENCES `product`(`id`);   
--     FOREIGN KEY (`category_id`) REFERENCES `category`(`id`);   
-- );

-- DROP USER IF EXISTS `reviewitdev@localhost`;
-- CREATE USER IF NOT EXISTS `reviewitdev@localhost` IDENTIFIED BY `reviewitdev`;
-- GRANT
-- SELECT
-- ,
-- INSERT
-- ,
-- UPDATE
-- ,
--     DELETE ON `reviewitdb`.* TO `reviewitdev@localhost`;