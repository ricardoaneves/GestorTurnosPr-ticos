-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema dssbd
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema dssbd
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `dssbd` DEFAULT CHARACTER SET utf8 ;
USE `dssbd` ;

-- -----------------------------------------------------
-- Table `dssbd`.`Docente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dssbd`.`Docente` (
  `idDocente` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idDocente`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dssbd`.`UC`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dssbd`.`UC` (
  `idUC` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `idDocenteResponsavel` INT NOT NULL,
  PRIMARY KEY (`idUC`),
  INDEX `fk_UC_Docente1_idx` (`idDocenteResponsavel` ASC),
  CONSTRAINT `fk_UC_Docente1`
    FOREIGN KEY (`idDocenteResponsavel`)
    REFERENCES `dssbd`.`Docente` (`idDocente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dssbd`.`Aluno`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dssbd`.`Aluno` (
  `idAluno` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `estatutoEspecial` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idAluno`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dssbd`.`Sala`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dssbd`.`Sala` (
  `idSala` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(10) NOT NULL,
  `capacidade` INT NOT NULL,
  PRIMARY KEY (`idSala`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dssbd`.`Turno`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dssbd`.`Turno` (
  `idTurno` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(15) NOT NULL,
  `capacidade` INT NOT NULL,
  `idSala` INT NOT NULL,
  `idUC` INT NOT NULL,
  `idDocente` INT NOT NULL,
  PRIMARY KEY (`idTurno`, `idSala`, `idUC`),
  INDEX `fk_Turno_Sala1_idx` (`idSala` ASC),
  INDEX `fk_Turno_UCs1_idx` (`idUC` ASC),
  INDEX `fk_Turno_Docente1_idx` (`idDocente` ASC),
  CONSTRAINT `fk_Turno_Sala1`
    FOREIGN KEY (`idSala`)
    REFERENCES `dssbd`.`Sala` (`idSala`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Turno_UCs1`
    FOREIGN KEY (`idUC`)
    REFERENCES `dssbd`.`UC` (`idUC`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Turno_Docente1`
    FOREIGN KEY (`idDocente`)
    REFERENCES `dssbd`.`Docente` (`idDocente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dssbd`.`Aluno_has_Turno`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dssbd`.`Aluno_has_Turno` (
  `idAluno` INT NOT NULL,
  `idTurno` INT NOT NULL,
  `faltas` INT NOT NULL,
  `avaliacao` INT NOT NULL,
  PRIMARY KEY (`idAluno`, `idTurno`),
  INDEX `fk_Aluno_has_Turno_Turno1_idx` (`idTurno` ASC),
  INDEX `fk_Aluno_has_Turno_Aluno1_idx` (`idAluno` ASC),
  CONSTRAINT `fk_Aluno_has_Turno_Aluno1`
    FOREIGN KEY (`idAluno`)
    REFERENCES `dssbd`.`Aluno` (`idAluno`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Aluno_has_Turno_Turno1`
    FOREIGN KEY (`idTurno`)
    REFERENCES `dssbd`.`Turno` (`idTurno`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
