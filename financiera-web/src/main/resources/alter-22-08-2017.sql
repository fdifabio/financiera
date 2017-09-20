CREATE TABLE `financiera_dev`.`interes` (
  `id`    BIGINT(20) NOT NULL AUTO_INCREMENT,
  `valor` DOUBLE     NOT NULL,
  `orden` INT        NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB;
CREATE TABLE `financiera_dev`.`credito` (
  `id`                BIGINT(20)   NOT NULL AUTO_INCREMENT,
  `capital`           DOUBLE       NOT NULL,
  `cuotas`            INT          NOT NULL,
  `fecha`             DATE         NOT NULL,
  `fecha_vencimiento` DATE         NOT NULL,
  `estado`            VARCHAR(255) NOT NULL,
  `interes_id`        BIGINT(20)   NOT NULL,
  PRIMARY KEY (`id`),
  INDEX (`interes_id`)
)
  ENGINE = InnoDB;

ALTER TABLE `credito` ADD FOREIGN KEY (`interes_id`) REFERENCES `interes`(`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE `credito` ADD `cliente_id` BIGINT(20) NOT NULL AFTER `interes_id`, ADD INDEX (`cliente_id`);
ALTER TABLE `cliente` ENGINE = INNODB;
ALTER TABLE `credito` ADD FOREIGN KEY (`cliente_id`) REFERENCES `cliente`(`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;


ALTER TABLE credito DROP FOREIGN KEY credito_ibfk_1;
ALTER TABLE `credito` CHANGE `interes_id` `interes` DOUBLE NOT NULL;


ALTER TABLE credito DROP INDEX interes_id;
ALTER TABLE `credito` CHANGE `fecha_vencimiento` `fecha_vencimiento` DATE NULL;

/*07/09/2017*/

CREATE TABLE `financiera_dev`.`descuento` (
  `id`    BIGINT(20) NOT NULL AUTO_INCREMENT,
  `valor` DOUBLE     NOT NULL,
  `orden` INT        NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB;

INSERT INTO `descuento` (`id`, `valor`, `orden`) VALUES (NULL, '5', '1'), (NULL, '7', '2');


/*19/09/2017*/
CREATE TABLE `financiera_dev`.`cuota` (  `id` BIGINT(11) NOT NULL AUTO_INCREMENT, `cuota_capital` DOUBLE NOT NULL , `cuota_interes` DOUBLE NOT NULL , `saldo` DOUBLE NOT NULL , `fecha_inicio` DATE NOT NULL , `fecha_cierra` DATE NOT NULL , `estado` VARCHAR(255) NOT NULL , `credito_id` BIGINT(11) NOT NULL , PRIMARY KEY (`id`), INDEX `credito_id` (`credito_id`)) ENGINE = MyISAM;

CREATE TABLE `financiera_dev`.`cobro` ( `id` BIGINT(11) NOT NULL AUTO_INCREMENT , `monto` DOUBLE NOT NULL , `fecha` DATE NOT NULL , `descripcion` VARCHAR(255) NOT NULL , `cuota_id` INT NOT NULL , PRIMARY KEY (`id`), INDEX `cuota_id` (`cuota_id`)) ENGINE = MyISAM;

CREATE TABLE `financiera_dev`.`caja` ( `id` BIGINT(11) NOT NULL AUTO_INCREMENT , `fecha_apertura` DATE NOT NULL , `fecha_cierre` DATE NOT NULL , PRIMARY KEY (`id`)) ENGINE = MyISAM;

CREATE TABLE `financiera_dev`.`movimiento` ( `id` BIGINT(11) NOT NULL AUTO_INCREMENT , `monto` DOUBLE NOT NULL , `fecha` DATE NOT NULL , `descripcion` VARCHAR(255) NOT NULL , `tipo` VARCHAR(255) NOT NULL , `caja_id` BIGINT(11) NOT NULL , PRIMARY KEY (`id`), INDEX `caja_id` (`caja_id`)) ENGINE = MyISAM;

/*20/09/2017*/
ALTER TABLE `cuota` CHANGE `fecha_cierra` `fecha_cierre` DATE NOT NULL;