-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema reviewitdb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `reviewitdb` ;

-- -----------------------------------------------------
-- Schema reviewitdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `reviewitdb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci ;
USE `reviewitdb` ;

-- -----------------------------------------------------
-- Table `product`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `product` ;

CREATE TABLE IF NOT EXISTS `product` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(120) NOT NULL,
  `description` VARCHAR(500) NULL,
  `created_on` DATETIME NULL,
  `last_updated` DATETIME NULL,
  `verified` TINYINT NOT NULL DEFAULT 0,
  `enabled` TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user` ;

CREATE TABLE IF NOT EXISTS `user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(120) NOT NULL,
  `email` VARCHAR(500) NOT NULL,
  `password` VARCHAR(100) NULL,
  `verified` TINYINT NOT NULL DEFAULT 0,
  `enabled` TINYINT NOT NULL DEFAULT 1,
  `created_on` DATETIME NULL,
  `last_updated` VARCHAR(45) NULL,
  `role` VARCHAR(60) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `product_review`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `product_review` ;

CREATE TABLE IF NOT EXISTS `product_review` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `content` TEXT NULL,
  `rating` INT NOT NULL,
  `created_on` DATETIME NULL,
  `last_updated` DATETIME NULL,
  `product_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `enabled` TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`, `enabled`),
  INDEX `fk_product_review_product_idx` (`product_id` ASC),
  INDEX `fk_product_review_user1_idx` (`user_id` ASC),
  CONSTRAINT `fk_product_review_product`
    FOREIGN KEY (`product_id`)
    REFERENCES `product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_review_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `review_reaction`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `review_reaction` ;

CREATE TABLE IF NOT EXISTS `review_reaction` (
  `product_review_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `is_like` TINYINT NOT NULL,
  INDEX `fk_review_like_product_review1_idx` (`product_review_id` ASC),
  INDEX `fk_review_like_user1_idx` (`user_id` ASC),
  PRIMARY KEY (`product_review_id`, `user_id`),
  CONSTRAINT `fk_review_like_product_review1`
    FOREIGN KEY (`product_review_id`)
    REFERENCES `product_review` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_review_like_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `category` ;

CREATE TABLE IF NOT EXISTS `category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(120) NOT NULL,
  `created_on` DATETIME NULL,
  `enabled` TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `product_category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `product_category` ;

CREATE TABLE IF NOT EXISTS `product_category` (
  `category_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  PRIMARY KEY (`category_id`, `product_id`),
  INDEX `fk_category_has_product_product1_idx` (`product_id` ASC),
  INDEX `fk_category_has_product_category1_idx` (`category_id` ASC),
  CONSTRAINT `fk_category_has_product_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_category_has_product_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `auth_token`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `auth_token` ;

CREATE TABLE IF NOT EXISTS `auth_token` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `token` VARCHAR(512) NOT NULL,
  `user_id` INT NOT NULL,
  `created_on` DATETIME NULL,
  `expires_on` DATETIME NULL,
  `enabled` TINYINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_auth_token_user1_idx` (`user_id` ASC),
  UNIQUE INDEX `token_UNIQUE` (`token` ASC),
  CONSTRAINT `fk_auth_token_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SET SQL_MODE = '';
DROP USER IF EXISTS reviewitdev@localhost;
SET SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
CREATE USER 'reviewitdev'@'localhost' IDENTIFIED BY 'reviewitdev';

GRANT SELECT, INSERT, TRIGGER, UPDATE, DELETE ON TABLE * TO 'reviewitdev'@'localhost';

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `product`
-- -----------------------------------------------------
START TRANSACTION;
USE `reviewitdb`;
INSERT INTO `product` (`id`, `name`, `description`, `created_on`, `last_updated`, `verified`, `enabled`) VALUES (1, 'Monster Energy Drink', 'An energy drink that makes your heart hurt.', '2024-01-05 17:16:44', '2024-01-05 17:16:44', 1, 1);
INSERT INTO `product` (`id`, `name`, `description`, `created_on`, `last_updated`, `verified`, `enabled`) VALUES (2, 'Swedish Fish', 'Yummy fish.', '2024-01-05 17:16:44', '2024-01-05 17:16:44', 1, 1);
INSERT INTO `product` (`id`, `name`, `description`, `created_on`, `last_updated`, `verified`, `enabled`) VALUES (3, 'MacBook Pro', 'Big mac ', '2024-01-05 17:16:44', '2024-01-05 17:16:44', 1, 1);
INSERT INTO `product` (`id`, `name`, `description`, `created_on`, `last_updated`, `verified`, `enabled`) VALUES (4, 'Beats Pro', 'overpriced headphones', '2024-01-05 17:16:44', '2024-01-05 17:16:44', 0, 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `user`
-- -----------------------------------------------------
START TRANSACTION;
USE `reviewitdb`;
INSERT INTO `user` (`id`, `username`, `email`, `password`, `verified`, `enabled`, `created_on`, `last_updated`, `role`) VALUES (1, 'joeschmoe11', 'jsmoe69@gmail.com', 'password123', 1, 1, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 'user');
INSERT INTO `user` (`id`, `username`, `email`, `password`, `verified`, `enabled`, `created_on`, `last_updated`, `role`) VALUES (2, 'b0bbySchmurda39', 'bigbobby@aol.com', 'password123', 1, 1, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 'user');
INSERT INTO `user` (`id`, `username`, `email`, `password`, `verified`, `enabled`, `created_on`, `last_updated`, `role`) VALUES (3, 'bigMacMcGee120', 'mackin@x.com', 'password123', 1, 1, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 'user');
INSERT INTO `user` (`id`, `username`, `email`, `password`, `verified`, `enabled`, `created_on`, `last_updated`, `role`) VALUES (4, 'moePoppa1234', 'mo@gmail.com', 'password123', 1, 0, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 'user');
INSERT INTO `user` (`id`, `username`, `email`, `password`, `verified`, `enabled`, `created_on`, `last_updated`, `role`) VALUES (5, 'admin', 'admin@reviewit.com', 'password123', 1, 1, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 'admin');

COMMIT;


-- -----------------------------------------------------
-- Data for table `product_review`
-- -----------------------------------------------------
START TRANSACTION;
USE `reviewitdb`;
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`) VALUES (1, 'Monster Energy Killed my dog!!!', 0, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 1, 1, 1);
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`) VALUES (2, 'Monster Energy Ate My Goldfish!!!', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 1, 1, 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `review_reaction`
-- -----------------------------------------------------
START TRANSACTION;
USE `reviewitdb`;
INSERT INTO `review_reaction` (`product_review_id`, `user_id`, `is_like`) VALUES (1, 2, 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `category`
-- -----------------------------------------------------
START TRANSACTION;
USE `reviewitdb`;
INSERT INTO `category` (`id`, `name`, `created_on`, `enabled`) VALUES (1, 'Food', '2024-01-05 17:16:44', 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `product_category`
-- -----------------------------------------------------
START TRANSACTION;
USE `reviewitdb`;
INSERT INTO `product_category` (`category_id`, `product_id`) VALUES (1, 1);
INSERT INTO `product_category` (`category_id`, `product_id`) VALUES (1, 2);

COMMIT;


-- -----------------------------------------------------
-- Data for table `auth_token`
-- -----------------------------------------------------
START TRANSACTION;
USE `reviewitdb`;
INSERT INTO `auth_token` (`id`, `token`, `user_id`, `created_on`, `expires_on`, `enabled`) VALUES (1, 'a', 1, '2024-01-05 17:16:44', '2026-01-05 17:16:44', 1);
INSERT INTO `auth_token` (`id`, `token`, `user_id`, `created_on`, `expires_on`, `enabled`) VALUES (2, 'admin-auth-token', 5, '2024-01-05 17:16:44', '2026-01-05 17:16:44', 1);

COMMIT;

