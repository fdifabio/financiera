ALTER TABLE `tasa` ADD `costo_adicional` DOUBLE NOT NULL AFTER `costo`;

ALTER TABLE `eprofesional` ADD `tasa_costo_adicional` DOUBLE NOT NULL AFTER `tasa_costo`, ADD `tasa_unidades` INT(11) NOT NULL AFTER `tasa_costo_adicional`, ADD `nomenclatura` VARCHAR(255) NOT NULL AFTER `tasa_unidades`;

ALTER TABLE `empresa` ADD `perfil_agrimensor_id` BIGINT(20) NULL DEFAULT NULL AFTER `ciudad_id`;

RENAME TABLE `empresa` TO `comitente`;

ALTER TABLE `eprofesional` CHANGE `lat` `lat` DOUBLE NULL;
ALTER TABLE `eprofesional` CHANGE `lng` `lng` DOUBLE NULL;