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
  `description` TEXT NOT NULL,
  `created_on` DATETIME NULL,
  `last_updated` DATETIME NULL,
  `verified` TINYINT NULL DEFAULT 0,
  `enabled` TINYINT NOT NULL DEFAULT 1,
  `image_url` TEXT NULL,
  `us_msrp` DECIMAL NULL,
  `brand_name` VARCHAR(200) NULL,
  `discontinued` TINYINT NULL,
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
  `title` VARCHAR(120) NOT NULL,
  `location` VARCHAR(45) NULL,
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


-- -----------------------------------------------------
-- Table `company`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `company` ;

CREATE TABLE IF NOT EXISTS `company` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(120) NOT NULL,
  `description` VARCHAR(350) NULL,
  `created_on` TIMESTAMP NULL,
  `last_updated` TIMESTAMP NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `product_link`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `product_link` ;

CREATE TABLE IF NOT EXISTS `product_link` (
  `id` INT NOT NULL,
  `url` VARCHAR(2000) NULL,
  `enabled` TINYINT NOT NULL,
  `product_id` INT NOT NULL,
  `company_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_product_link_product1_idx` (`product_id` ASC),
  INDEX `fk_product_link_company1_idx` (`company_id` ASC),
  CONSTRAINT `fk_product_link_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_link_company1`
    FOREIGN KEY (`company_id`)
    REFERENCES `company` (`id`)
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
INSERT INTO `product` (`id`, `name`, `description`, `created_on`, `last_updated`, `verified`, `enabled`, `image_url`, `us_msrp`, `brand_name`, `discontinued`) VALUES (1, 'Monster Energy Drink', 'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?', '2024-01-05 17:16:44', '2024-01-05 17:16:44', 1, 1, 'https://i5.walmartimages.com/seo/Monster-Energy-Original-Energy-Drink-16-fl-oz-12pk_174969a5-49e3-47d0-8f20-f78e98f1b52c.04dc7d34622c31874703eff0db3b82a4.jpeg?odnHeight=640&odnWidth=640&odnBg=FFFFFF', 2.28, 'Monster', 0);
INSERT INTO `product` (`id`, `name`, `description`, `created_on`, `last_updated`, `verified`, `enabled`, `image_url`, `us_msrp`, `brand_name`, `discontinued`) VALUES (2, 'Swedish Fish', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Turpis cursus in hac habitasse. Sed ullamcorper morbi tincidunt ornare massa eget egestas purus. A arcu cursus vitae congue mauris rhoncus. Tellus in metus vulputate eu scelerisque felis imperdiet.', '2024-01-05 17:16:44', '2024-01-05 17:16:44', 1, 1, 'https://target.scene7.com/is/image/Target/GUEST_8a53c0a1-0aa5-4bcf-878b-dc51bd0c317b?wid=488&hei=488&fmt=pjpeg', 3.24, 'Mondelez', 0);
INSERT INTO `product` (`id`, `name`, `description`, `created_on`, `last_updated`, `verified`, `enabled`, `image_url`, `us_msrp`, `brand_name`, `discontinued`) VALUES (3, 'MacBook Pro', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Sit amet mattis vulputate enim nulla aliquet. Arcu vitae elementum curabitur vitae nunc sed. Et leo duis ut diam quam nulla porttitor massa. Enim praesent elementum facilisis leo vel fringilla est.', '2024-01-05 17:16:44', '2024-01-05 17:16:44', 1, 1, 'data:image/jpeg;base64,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', 1299.00, 'Apple', 0);
INSERT INTO `product` (`id`, `name`, `description`, `created_on`, `last_updated`, `verified`, `enabled`, `image_url`, `us_msrp`, `brand_name`, `discontinued`) VALUES (4, 'Beats Pro', 'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?', '2024-01-05 17:16:44', '2024-01-05 17:16:44', 1, 1, 'https://pisces.bbystatic.com/image2/BestBuy_US/images/products/6341/6341988_rd.jpg;maxHeight=640;maxWidth=550', 199.99, 'Beats By Dre', 0);
INSERT INTO `product` (`id`, `name`, `description`, `created_on`, `last_updated`, `verified`, `enabled`, `image_url`, `us_msrp`, `brand_name`, `discontinued`) VALUES (5, 'Nintendo Switch', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Morbi leo urna molestie at elementum eu facilisis. Proin sed libero enim sed. Faucibus turpis in eu mi bibendum neque egestas. Iaculis eu non diam phasellus vestibulum lorem sed risus ultricies.', '2024-01-05 17:16:44', '2024-01-05 17:16:44', 1, 1, 'https://m.media-amazon.com/images/I/51Gz7IimgoL._AC_UF894,1000_QL80_.jpg', 199.99, 'Nintendo', 0);
INSERT INTO `product` (`id`, `name`, `description`, `created_on`, `last_updated`, `verified`, `enabled`, `image_url`, `us_msrp`, `brand_name`, `discontinued`) VALUES (6, 'Liquid Death Water', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Sit amet mattis vulputate enim nulla aliquet. Arcu vitae elementum curabitur vitae nunc sed. Et leo duis ut diam quam nulla porttitor massa. Enim praesent elementum facilisis leo vel fringilla est.', '2024-01-05 17:16:44', '2024-01-05 17:16:44', 1, 1, 'https://i5.walmartimages.com/asr/4e20b37b-8b55-4de3-afdf-5445c5a39243.8832d0abd57e0da8d907ca605719553b.jpeg?odnHeight=768&odnWidth=768&odnBg=FFFFFF', 6.99, 'Liquid Death', 0);
INSERT INTO `product` (`id`, `name`, `description`, `created_on`, `last_updated`, `verified`, `enabled`, `image_url`, `us_msrp`, `brand_name`, `discontinued`) VALUES (7, 'Gameboy Advance', 'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?', '2024-01-05 17:16:44', '2024-01-05 17:16:44', 1, 1, 'https://i.etsystatic.com/15746134/r/il/913526/1643427731/il_fullxfull.1643427731_mu24.jpg', 100.99, 'Nintendo', 1);
INSERT INTO `product` (`id`, `name`, `description`, `created_on`, `last_updated`, `verified`, `enabled`, `image_url`, `us_msrp`, `brand_name`, `discontinued`) VALUES (8, 'Sony Playstation 5', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Sit amet mattis vulputate enim nulla aliquet. Arcu vitae elementum curabitur vitae nunc sed. Et leo duis ut diam quam nulla porttitor massa. Enim praesent elementum facilisis leo vel fringilla est.', '2024-01-05 17:16:44', '2024-01-05 17:16:44', 1, 1, 'https://media.direct.playstation.com/is/image/sierialto/PS5-Hero-Packshot-us', 499.99, 'Sony', 0);
INSERT INTO `product` (`id`, `name`, `description`, `created_on`, `last_updated`, `verified`, `enabled`, `image_url`, `us_msrp`, `brand_name`, `discontinued`) VALUES (9, 'Xbox Series X 1TB Console - Black', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Morbi leo urna molestie at elementum eu facilisis. Proin sed libero enim sed. Faucibus turpis in eu mi bibendum neque egestas. Iaculis eu non diam phasellus vestibulum lorem sed risus ultricies.', '2024-01-05 17:16:44', '2024-01-05 17:16:44', 1, 1, 'https://pisces.bbystatic.com/image2/BestBuy_US/images/products/6428/6428324_sd.jpg;maxHeight=640;maxWidth=550', 499.99, 'Microsoft', 0);
INSERT INTO `product` (`id`, `name`, `description`, `created_on`, `last_updated`, `verified`, `enabled`, `image_url`, `us_msrp`, `brand_name`, `discontinued`) VALUES (10, 'Loose Fit Midweight Tapered Sweatpants ', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Morbi leo urna molestie at elementum eu facilisis. Proin sed libero enim sed. Faucibus turpis in eu mi bibendum neque egestas. Iaculis eu non diam phasellus vestibulum lorem sed risus ultricies.', '2024-01-05 17:16:44', '2024-01-05 17:16:44', 1, 1, 'data:image/jpeg;base64,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', 59.99, 'Carhartt', 0);
INSERT INTO `product` (`id`, `name`, `description`, `created_on`, `last_updated`, `verified`, `enabled`, `image_url`, `us_msrp`, `brand_name`, `discontinued`) VALUES (11, 'Women\'s Carhartt Force® Relaxed Fit Midweight Pocket T-Shirt ', 'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?', '2024-01-05 17:16:44', '2024-01-05 17:16:44', 1, 1, 'https://www.googleadservices.com/pagead/aclk?sa=L&ai=DChcSEwjY4L6uk-iDAxU4YZEFHdPwBjcYABAMGgJscg&ase=2&gclid=Cj0KCQiAtaOtBhCwARIsAN_x-3J8qy6I9tRGtdvupZ78pM32qb_cCbPdlhpuy4Fxw5U7e7VnU8oSBJ0aApRnEALw_wcB&ei=UrypZbKwA5KHqtsP4-ey8Ag&ohost=www.google.com&cid=CAESVuD2LyaHYWkI28-OOqs-HvK-XcdLJ-5EReaoqLMyW_8x4CuFF9W33x408WYr0fJFSZcbfnFyFCWgHh5ZAM4G-F5lM_9BEgqqju86h94fyJiD7_P9vAmT&sig=AOD64_2ZJ11vSBXZW6ZRkXDD9ZchN0gRPg&ctype=5&q=&nis=4&sqi=2&ved=2ahUKEwiyoayuk-iDAxWSg2oFHeOzDI4Qwg8oAXoECAgQCg&adurl=', 24.99, 'Carhartt', 0);
INSERT INTO `product` (`id`, `name`, `description`, `created_on`, `last_updated`, `verified`, `enabled`, `image_url`, `us_msrp`, `brand_name`, `discontinued`) VALUES (12, 'Hot Head Deep Conditioning Heat Cap', 'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?', '2024-01-05 17:16:44', '2024-01-05 17:16:44', 1, 1, 'https://cdn10.bigcommerce.com/s-70og2zva/products/187/images/1044/stellar-xl-hot-head__23015.1652121132.600.600.jpg?c=2', 34.99, 'Hot Head by Thermal Hair Case', 0);

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
INSERT INTO `user` (`id`, `username`, `email`, `password`, `verified`, `enabled`, `created_on`, `last_updated`, `role`) VALUES (6, 'bigboytommy334', 'biggi@example.com', 'password123', 1, 1, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 'user');

COMMIT;


-- -----------------------------------------------------
-- Data for table `product_review`
-- -----------------------------------------------------
START TRANSACTION;
USE `reviewitdb`;
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (1, 'I love the energy boost from this drink. It keeps me going throughout the day. Highly recommended!', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 1, 1, 1, 'Energizing \nBoost', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (2, 'Not a fan of the aftertaste. The initial burst of energy is good, but the lingering flavor is off-putting.', 3, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 1, 2, 1, 'Mixed \nFeelings', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (3, 'Monster Energy is my go-to drink for late-night gaming sessions. It keeps me alert and focused.', 4, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 1, 3, 1, 'Late-Night \nFuel', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (4, 'This drink is a lifesaver during long drives. It helps me stay awake and alert on the road.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 1, 4, 1, 'Road Trip \nEssential', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (5, 'Great taste and the perfect pick-me-up. Monster Energy keeps me going during hectic workdays.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 1, 5, 1, 'Workday \nSaviour', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (6, 'The energy boost is incredible, but I wish the drink came in a larger size. More, please!', 4, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 1, 6, 1, 'Size Matters', 'United \nStates');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (7, 'Monster Energy is a staple in my pre-workout routine. It gives me the extra push I need at the gym.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 1, 1, 1, 'Gym \nBuddy', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (8, 'Refreshing and effective. I enjoy the taste, and it keeps me energized during long study sessions.', 4, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 1, 2, 1, 'Study \nFuel', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (9, 'Monster Energy is my secret weapon during intense gaming marathons. It helps me stay focused.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 1, 3, 1, 'Gaming \nEndurance', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (10, 'The packaging is convenient, and the drink provides a quick energy boost when I need it.', 4, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 1, 4, 1, 'Convenient \nPick-Me-Up', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (11, 'These Swedish Fish candies are my guilty pleasure. Can\'t resist the chewy goodness!', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 2, 1, 1, 'Sweet Indulgence', 'United \nStates');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (12, 'Deliciously addictive! I always have a bag of Swedish Fish on my desk. Perfect for a quick treat.', 4, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 2, 2, 1, 'Desk \nDelight', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (13, 'Swedish Fish are a hit at every movie night. The fruity flavor is a crowd-pleaser.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 2, 3, 1, 'Movie Companion', 'United \nStates');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (14, 'My kids love these candies! The vibrant colors and tasty flavor make them a family favorite.', 4, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 2, 4, 1, 'Family \nFavorite', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (15, 'Swedish Fish are the perfect snack for road trips. Sweet, chewy, and easy to share.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 2, 5, 1, 'Road Trip Treat', 'United \nStates');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (16, 'I\'ve been a fan of Swedish Fish since childhood. Nostalgic and delicious, they never disappoint.', 4, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 2, 6, 1, 'Nostalgic \nTreat', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (17, 'These candies are a must-have at parties. They add a fun and sweet touch to any celebration.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 2, 1, 1, 'Party \nEssential', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (18, 'I enjoy the chewy texture and fruity flavor. Swedish Fish are my go-to sweet treat.', 4, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 2, 2, 1, 'Sweet Obsession', 'United \nStates');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (19, 'Swedish Fish never go out of style. A classic candy that brings back fond memories.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 2, 3, 1, 'Classic Delight', 'United \nStates');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (20, 'These candies are a hit in the office. Colleagues often ask for a share of my Swedish Fish stash.', 4, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 2, 4, 1, 'Office \nFavorite', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (21, 'The MacBook Pro is a powerhouse. It handles my demanding tasks effortlessly. Truly impressive!', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 3, 1, 1, 'Impressive \nPerformance', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (22, 'Fast and reliable. The MacBook Pro is my go-to device for work and creativity.', 4, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 3, 2, 1, 'Workhorse Device', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (23, 'The build quality of the MacBook Pro is exceptional. It\'s sleek, durable, and a joy to use.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 3, 3, 1, 'Exceptional \nBuild', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (24, 'As a designer, the MacBook Pro is my essential tool. The display and performance are top-notch.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 3, 4, 1, 'Designer\'s \nDelight', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (25, 'A bit on the expensive side, but the MacBook Pro is worth the investment for its quality.', 4, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 3, 5, 1, 'Quality \nInvestment', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (26, 'The MacBook Pro exceeded my expectations. It\'s a reliable companion for both work and play.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 3, 6, 1, 'Exceeded \nExpectations', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (27, 'I appreciate the sleek design and powerful performance of the MacBook Pro. A true professional\'s choice.', 5, '2024-01-05 17:16:44', '2024-01-05 \n17:16:44', 3, 1, 1, 'Professional\'s Choice', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (28, 'The MacBook Pro is a perfect match for content creators. It handles video editing like a breeze.', 4, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 3, 2, 1, 'Content Creator\'s \nCompanion', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (29, 'Reliable and efficient. The MacBook Pro is my daily driver for work and personal use.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 3, 3, 1, 'Daily Workhorse', 'United \nStates');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (30, 'The MacBook Pro is an investment in productivity. It makes multitasking seamless and enjoyable.', 4, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 3, 4, 1, 'Productivity \nBoost', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (31, 'The sound quality of Beats Pro headphones is unmatched. Every beat is crystal clear.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 4, 1, 1, 'Crystal Clear Beats', 'United \nStates');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (32, 'Comfortable fit for long listening sessions. Beats Pro delivers on both style and comfort.', 4, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 4, 2, 1, 'Stylish \nComfort', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (33, 'The bass on these headphones is incredible. Beats Pro brings music to life with powerful lows.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 4, 3, 1, 'Powerful Bass \nExperience', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (34, 'Beats Pro is my favorite companion during workouts. The secure fit and great sound keep me motivated.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 4, 4, 1, 'Workout \nMotivation', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (35, 'Stylish design, premium sound. Beats Pro headphones are a fashion statement with exceptional audio quality.', 4, '2024-01-05 17:16:44', '2024-01-05 \n17:16:44', 4, 5, 1, 'Fashionable Audio', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (36, 'The noise isolation on Beats Pro is fantastic. It enhances the listening experience in any environment.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 4, 6, 1, 'Fantastic \nNoise Isolation', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (37, 'Beats Pro exceeded my expectations. The build quality is sturdy, and the sound is immersive.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 4, 1, 1, 'Immersive Sound \nExperience', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (38, 'Great for travel. Beats Pro headphones are my go-to for enjoying music on long flights.', 4, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 4, 2, 1, 'Travel Companion', 'United \nStates');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (39, 'Beats Pro is perfect for gaming. The audio precision enhances the gaming experience.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 4, 3, 1, 'Gaming Precision', 'United \nStates');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (40, 'The wireless feature on Beats Pro is a game-changer. No more tangled cords during workouts!', 4, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 4, 4, 1, 'Wireless \nFreedom', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (41, 'The Nintendo Switch is a versatile gaming console. I love the ability to play on the go or at home.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 5, 1, 1, 'Versatile \nGaming', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (42, 'Great for family game nights. The Nintendo Switch brings everyone together for fun and laughter.', 4, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 5, 2, 1, 'Family \nFun', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (43, 'The portability of the Nintendo Switch is a game-changer. I can enjoy my favorite games anywhere.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 5, 3, 1, 'Gaming on the \nGo', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (44, 'Nintendo Switch is perfect for multiplayer gaming. The joy-cons make it easy to share the fun.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 5, 4, 1, 'Multiplayer \nJoy', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (45, 'I\'m impressed with the game library on Nintendo Switch. There\'s something for every gamer.', 4, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 5, 5, 1, 'Diverse Game \nLibrary', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (46, 'The graphics on Nintendo Switch are top-notch. It delivers an immersive gaming experience.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 5, 6, 1, 'Immersive \nGraphics', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (47, 'Nintendo Switch is a great stress-reliever. Playing my favorite games helps me unwind after work.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 5, 1, 1, 'Stress-Relief \nGaming', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (48, 'The switch between handheld and docked mode is seamless. Nintendo Switch adapts to my gaming needs.', 4, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 5, 2, 1, 'Seamless \nSwitching', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (49, 'Nintendo Switch has brought back the joy of gaming for me. I love the nostalgic feel of it.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 5, 3, 1, 'Joyful \nNostalgia', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (50, 'I appreciate the innovative design of Nintendo Switch. It adds an element of excitement to gaming.', 4, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 5, 4, 1, 'Exciting \nDesign', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (51, 'Liquid Death Water is my go-to choice for staying hydrated. The packaging is metal and eco-friendly!', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 6, 1, 1, 'Metallic \nHydration', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (52, 'Refreshing and environmentally conscious. Liquid Death Water helps me stay hydrated throughout the day.', 4, '2024-01-05 17:16:44', '2024-01-05 \n17:16:44', 6, 2, 1, 'Eco-Friendly Refreshment', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (53, 'Liquid Death Water is perfect for outdoor adventures. The can is durable and easy to carry.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 6, 3, 1, 'Outdoor \nHydration', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (54, 'I appreciate the unique branding of Liquid Death Water. It adds a fun element to staying hydrated.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 6, 4, 1, 'Fun \nHydration', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (55, 'The fizziness of Liquid Death Water is a pleasant surprise. It\'s a refreshing alternative to flat water.', 4, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 6, 5, 1, 'Fizzed \nRefreshment', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (56, 'I\'m a fan of the sustainable approach of Liquid Death Water. It\'s guilt-free hydration in a can.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 6, 6, 1, 'Guilt-Free \nHydration', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (57, 'Liquid Death Water is my choice for staying hydrated during workouts. The cold can is a bonus.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 6, 1, 1, 'Workout \nHydration', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (58, 'I love the rebellious branding of Liquid Death Water. It adds a unique flair to hydration.', 4, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 6, 2, 1, 'Rebellious \nHydration', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (59, 'Liquid Death Water is a conversation starter. I\'ve had people ask me about it, and it\'s a fun story to share.', 5, '2024-01-05 17:16:44', '2024-01-05 \n17:16:44', 6, 3, 1, 'Conversation Spark', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (60, 'The compact size of Liquid Death Water cans is convenient. I can throw a few in my bag for on-the-go hydration.', 4, '2024-01-05 17:16:44', '2024-01-05 \n17:16:44', 6, 4, 1, 'On-the-Go Hydration', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (61, 'Gameboy Advance holds a special place in my gaming heart. Nostalgia at its finest!', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 7, 1, 1, 'Nostalgic Gaming', 'United \nStates');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (62, 'Compact and reliable. Gameboy Advance is the perfect handheld console for gaming on the go.', 4, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 7, 2, 1, 'Gaming on the \nGo', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (63, 'I\'ve spent countless hours playing my favorite games on Gameboy Advance. A true classic.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 7, 3, 1, 'Classic Gaming \nHours', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (64, 'The library of games for Gameboy Advance is diverse. There\'s something for every gamer.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 7, 4, 1, 'Diverse Game \nLibrary', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (65, 'Gameboy Advance is the reason I fell in love with gaming. It\'s a nostalgic journey every time I play.', 4, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 7, 5, 1, 'Love for \nGaming', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (66, 'Portable gaming at its best. Gameboy Advance has been my travel companion for years.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 7, 6, 1, 'Travel Gaming \nCompanion', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (67, 'The design of Gameboy Advance is iconic. It\'s a piece of gaming history in my hands.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 7, 1, 1, 'Iconic Design', 'United \nStates');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (68, 'Gameboy Advance is perfect for quick gaming sessions. Ideal for breaks during a busy day.', 4, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 7, 2, 1, 'Quick Gaming \nBreaks', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (69, 'I\'ve introduced my younger siblings to the joy of Gameboy Advance. It\'s a family tradition.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 7, 3, 1, 'Family Gaming \nTradition', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (70, 'Gameboy Advance has stood the test of time. A reliable gaming companion that never gets old.', 4, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 7, 4, 1, 'Timeless \nGaming', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (71, 'Sony Playstation 5 is a gaming marvel. The graphics and performance are next level.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 8, 1, 1, 'Next-Level Gaming \nExperience', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (72, 'The controller on Sony Playstation 5 adds a new dimension to gaming. It\'s immersive and responsive.', 4, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 8, 2, 1, 'Immersive \nController', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (73, 'Sony Playstation 5 is a beast of a console. It handles demanding games with ease.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 8, 3, 1, 'Gaming Beast', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (74, 'I appreciate the sleek design and minimalistic approach of Sony Playstation 5.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 8, 4, 1, 'Sleek and Minimal', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (75, 'The load times on Sony Playstation 5 are impressively fast. It enhances the overall gaming experience.', 4, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 8, 5, 1, 'Fast Load \nTimes', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (76, 'Sony Playstation 5 has become the centerpiece of my gaming setup. It\'s a true entertainment hub.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 8, 6, 1, 'Gaming \nEntertainment Hub', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (77, 'The 3D audio on Sony Playstation 5 is a game-changer. It adds depth to every gaming session.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 8, 1, 1, 'Game-Changing \nAudio', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (78, 'Sony Playstation 5 is a must-have for any gaming enthusiast. It sets the bar high for the industry.', 4, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 8, 2, 1, 'Bar-Setting \nConsole', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (79, 'The exclusive titles on Sony Playstation 5 make it a worthwhile investment for any gamer.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 8, 3, 1, 'Worthwhile \nInvestment', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (80, 'Sony Playstation 5 delivers on the promise of next-gen gaming. It\'s a step into the future of entertainment.', 4, '2024-01-05 17:16:44', '2024-01-05 \n17:16:44', 8, 4, 1, 'Next-Gen Gaming Future', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (81, 'The Xbox Series X 1TB Console is a gaming powerhouse. The performance is top-notch.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 9, 1, 1, 'Gaming Powerhouse', 'United \nStates');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (82, 'The sleek design of Xbox Series X is a visual delight. It adds a modern touch to my setup.', 4, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 9, 2, 1, 'Sleek Visuals', 'United \nStates');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (83, 'Xbox Series X delivers on speed and graphics. It\'s a significant upgrade for any gamer.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 9, 3, 1, 'Speed and \nGraphics', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (84, 'The quick resume feature on Xbox Series X is a game-changer. Switching between games is seamless.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 9, 4, 1, 'Game-Changing \nQuick Resume', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (85, 'Xbox Series X is my go-to console for multiplayer gaming. The online experience is smooth.', 4, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 9, 5, 1, 'Smooth \nMultiplayer', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (86, 'I appreciate the backward compatibility of Xbox Series X. It\'s a great way to revisit old favorites.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 9, 6, 1, 'Backward \nCompatibility Joy', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (87, 'Xbox Series X enhances my home entertainment setup. It\'s not just a console; it\'s an experience.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 9, 1, 1, 'Home \nEntertainment Upgrade', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (88, 'The storage capacity of Xbox Series X is impressive. No need to worry about running out of space.', 4, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 9, 2, 1, 'Impressive \nStorage', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (89, 'Xbox Series X is a worthy investment for any gamer. It brings the best of gaming to your fingertips.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 9, 3, 1, 'Worthy Gaming \nInvestment', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (90, 'The user interface on Xbox Series X is intuitive and user-friendly. It\'s easy to navigate and enjoy.', 4, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 9, 4, 1, 'User-Friendly \nInterface', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (91, 'Loose Fit Midweight Tapered Sweatpants are the epitome of comfort. Perfect for lounging at home.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 10, 1, 1, 'Comfortable \nLounging', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (92, 'I\'m in love with the loose fit of these sweatpants. They strike the right balance between style and comfort.', 4, '2024-01-05 17:16:44', '2024-01-05 \n17:16:44', 10, 2, 1, 'Stylish Comfort', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (93, 'Loose Fit Midweight Tapered Sweatpants are my go-to for weekend relaxation. Pure bliss!', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 10, 3, 1, 'Weekend Bliss', 'United \nStates');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (94, 'The tapered design of these sweatpants adds a modern touch. I feel both cozy and stylish.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 10, 4, 1, 'Cozy Modernity', 'United \nStates');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (95, 'Perfect for chilly evenings. These sweatpants keep me warm without sacrificing style.', 4, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 10, 5, 1, 'Warm Style', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (96, 'Loose Fit Midweight Tapered Sweatpants are a staple in my casual wardrobe. Versatile and comfortable.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 10, 6, 1, 'Casual \nWardrobe Staple', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (97, 'I appreciate the attention to detail in the design of these sweatpants. It\'s quality craftsmanship.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 10, 1, 1, 'Quality \nCraftsmanship', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (98, 'Loose Fit Midweight Tapered Sweatpants are perfect for light workouts. Flexible and easy to move in.', 4, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 10, 2, 1, 'Light \nWorkout Companion', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (99, 'These sweatpants are a must-have for cozy movie nights. The loose fit adds to the relaxation.', 5, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 10, 3, 1, 'Movie Night \nEssential', 'United States');
INSERT INTO `product_review` (`id`, `content`, `rating`, `created_on`, `last_updated`, `product_id`, `user_id`, `enabled`, `title`, `location`) VALUES (100, 'Loose Fit Midweight Tapered Sweatpants are my all-season favorite. Comfortable in any weather.', 4, '2024-01-05 17:16:44', '2024-01-05 17:16:44', 10, 4, 1, 'All-Season \nComfort', 'United States');

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
INSERT INTO `category` (`id`, `name`, `created_on`, `enabled`) VALUES (1, 'Food And Beverage', '2024-01-05 17:16:44', 1);
INSERT INTO `category` (`id`, `name`, `created_on`, `enabled`) VALUES (2, 'Beauty', '2024-01-05 17:16:44', 1);
INSERT INTO `category` (`id`, `name`, `created_on`, `enabled`) VALUES (3, 'Tech', '2024-01-05 17:16:44', 1);
INSERT INTO `category` (`id`, `name`, `created_on`, `enabled`) VALUES (4, 'Gaming', '2024-01-05 17:16:44', 1);
INSERT INTO `category` (`id`, `name`, `created_on`, `enabled`) VALUES (5, 'Clothing', '2024-01-05 17:16:44', 1);
INSERT INTO `category` (`id`, `name`, `created_on`, `enabled`) VALUES (6, 'Workwear', '2024-01-05 17:16:44', 1);
INSERT INTO `category` (`id`, `name`, `created_on`, `enabled`) VALUES (7, 'Health And Wellness', '2024-01-05 17:16:44', 1);
INSERT INTO `category` (`id`, `name`, `created_on`, `enabled`) VALUES (8, 'Laptops', '2024-01-05 17:16:44', 1);
INSERT INTO `category` (`id`, `name`, `created_on`, `enabled`) VALUES (9, 'Audio', '2024-01-05 17:16:44', 1);
INSERT INTO `category` (`id`, `name`, `created_on`, `enabled`) VALUES (10, 'Pharmaceuticals', '2024-01-05 17:16:44', 1);
INSERT INTO `category` (`id`, `name`, `created_on`, `enabled`) VALUES (11, 'Gaming Consoles', '2024-01-05 17:16:44', 1);
INSERT INTO `category` (`id`, `name`, `created_on`, `enabled`) VALUES (12, 'Automotive', '2024-01-05 17:16:44', 1);
INSERT INTO `category` (`id`, `name`, `created_on`, `enabled`) VALUES (13, 'Baby And Child', '2024-01-05 17:16:44', 1);
INSERT INTO `category` (`id`, `name`, `created_on`, `enabled`) VALUES (14, 'Menswear', '2024-01-05 17:16:44', 1);
INSERT INTO `category` (`id`, `name`, `created_on`, `enabled`) VALUES (15, 'Womenswear', '2024-01-05 17:16:44', 1);
INSERT INTO `category` (`id`, `name`, `created_on`, `enabled`) VALUES (16, 'Watches', '2024-01-05 17:16:44', 1);
INSERT INTO `category` (`id`, `name`, `created_on`, `enabled`) VALUES (17, 'Smartphones And Tablets', '2024-01-05 17:16:44', 1);
INSERT INTO `category` (`id`, `name`, `created_on`, `enabled`) VALUES (18, 'Car Electronics', '2024-01-05 17:16:44', 1);
INSERT INTO `category` (`id`, `name`, `created_on`, `enabled`) VALUES (19, 'Lighting Equipment', '2024-01-05 17:16:44', 1);
INSERT INTO `category` (`id`, `name`, `created_on`, `enabled`) VALUES (20, 'Jewelry', '2024-01-05 17:16:44', 1);
INSERT INTO `category` (`id`, `name`, `created_on`, `enabled`) VALUES (21, 'Fashion', '2024-01-05 17:16:44', 1);
INSERT INTO `category` (`id`, `name`, `created_on`, `enabled`) VALUES (22, 'Footwear', '2024-01-05 17:16:44', 1);
INSERT INTO `category` (`id`, `name`, `created_on`, `enabled`) VALUES (23, 'Athletic wear', '2024-01-05 17:16:44', 1);
INSERT INTO `category` (`id`, `name`, `created_on`, `enabled`) VALUES (24, 'Appliances', '2024-01-05 17:16:44', 1);
INSERT INTO `category` (`id`, `name`, `created_on`, `enabled`) VALUES (25, 'Furniture', '2024-01-05 17:16:44', 1);
INSERT INTO `category` (`id`, `name`, `created_on`, `enabled`) VALUES (26, 'Books', '2024-01-05 17:16:44', 1);
INSERT INTO `category` (`id`, `name`, `created_on`, `enabled`) VALUES (27, 'Pet Care', '2024-01-05 17:16:44', 1);
INSERT INTO `category` (`id`, `name`, `created_on`, `enabled`) VALUES (28, 'Power Tools', '2024-01-05 17:16:44', 1);
INSERT INTO `category` (`id`, `name`, `created_on`, `enabled`) VALUES (29, 'Hand Tools', '2024-01-05 17:16:44', 1);
INSERT INTO `category` (`id`, `name`, `created_on`, `enabled`) VALUES (30, 'Kitchen', '2024-01-05 17:16:44', 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `product_category`
-- -----------------------------------------------------
START TRANSACTION;
USE `reviewitdb`;
INSERT INTO `product_category` (`category_id`, `product_id`) VALUES (1, 6);
INSERT INTO `product_category` (`category_id`, `product_id`) VALUES (1, 1);
INSERT INTO `product_category` (`category_id`, `product_id`) VALUES (1, 2);
INSERT INTO `product_category` (`category_id`, `product_id`) VALUES (3, 4);
INSERT INTO `product_category` (`category_id`, `product_id`) VALUES (3, 7);
INSERT INTO `product_category` (`category_id`, `product_id`) VALUES (3, 3);
INSERT INTO `product_category` (`category_id`, `product_id`) VALUES (3, 5);
INSERT INTO `product_category` (`category_id`, `product_id`) VALUES (3, 8);
INSERT INTO `product_category` (`category_id`, `product_id`) VALUES (3, 9);
INSERT INTO `product_category` (`category_id`, `product_id`) VALUES (5, 10);
INSERT INTO `product_category` (`category_id`, `product_id`) VALUES (14, 10);
INSERT INTO `product_category` (`category_id`, `product_id`) VALUES (5, 11);
INSERT INTO `product_category` (`category_id`, `product_id`) VALUES (4, 7);
INSERT INTO `product_category` (`category_id`, `product_id`) VALUES (4, 5);
INSERT INTO `product_category` (`category_id`, `product_id`) VALUES (4, 9);
INSERT INTO `product_category` (`category_id`, `product_id`) VALUES (4, 8);
INSERT INTO `product_category` (`category_id`, `product_id`) VALUES (11, 5);
INSERT INTO `product_category` (`category_id`, `product_id`) VALUES (11, 8);
INSERT INTO `product_category` (`category_id`, `product_id`) VALUES (11, 9);
INSERT INTO `product_category` (`category_id`, `product_id`) VALUES (8, 3);

COMMIT;


-- -----------------------------------------------------
-- Data for table `auth_token`
-- -----------------------------------------------------
START TRANSACTION;
USE `reviewitdb`;
INSERT INTO `auth_token` (`id`, `token`, `user_id`, `created_on`, `expires_on`, `enabled`) VALUES (1, 'a', 1, '2024-01-05 17:16:44', '2026-01-05 17:16:44', 1);
INSERT INTO `auth_token` (`id`, `token`, `user_id`, `created_on`, `expires_on`, `enabled`) VALUES (2, 'admin-auth-token', 5, '2024-01-05 17:16:44', '2026-01-05 17:16:44', 1);

COMMIT;

