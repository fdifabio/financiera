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