SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `guestbook` DEFAULT CHARACTER SET latin1 ;
USE `guestbook` ;

-- -----------------------------------------------------
-- Table `guestbook`.`guest`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `guestbook`.`guest` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `firstName` VARCHAR(45) NOT NULL ,
  `lastName` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `guestbook`.`entry`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `guestbook`.`entry` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `guest_id` INT NOT NULL ,
  `content` VARCHAR(255) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `fk_entry_guest`
    FOREIGN KEY (`guest_id` )
    REFERENCES `guestbook`.`guest` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 6;

CREATE INDEX `fk_entry_guest` ON `guestbook`.`entry` (`guest_id` ASC) ;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
