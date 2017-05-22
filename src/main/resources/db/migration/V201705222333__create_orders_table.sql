CREATE TABLE `orders`.`orders` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `author` VARCHAR(45) NOT NULL,
  `title` VARCHAR(45) NOT NULL,
  `comment` VARCHAR(45) NULL,
  `quantity` INT NULL,
  `price` DECIMAL(19,2) NULL,
  `last_changed` TIMESTAMP NULL,
  `opt_lock_version` INT NULL,
  PRIMARY KEY (`id`));
