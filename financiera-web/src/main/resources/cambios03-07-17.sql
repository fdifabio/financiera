ALTER TABLE `perfil_agrimensor`
  CHANGE `domicilio` `domicilio_legal` VARCHAR(255)
CHARACTER SET latin1
COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  CHANGE `ciudad_id` `ciudad_legal_id` BIGINT(20) NULL DEFAULT NULL;


ALTER TABLE `perfil_agrimensor`
  ADD `domicilio_real` VARCHAR(255) NULL DEFAULT NULL
  AFTER `ciudad_legal_id`,
  ADD `ciudad_real_id` BIGINT(20) NULL DEFAULT NULL
  AFTER `domicilio_real`;

ALTER TABLE `perfil_agrimensor`
  DROP `iva`,
  DROP `ingresoBrutos`;

ALTER TABLE `perfil_agrimensor`
  ADD `fecha_nacimiento` DATETIME NULL DEFAULT NULL
  AFTER `legajo`,
  ADD `celular` VARCHAR(255) NULL DEFAULT NULL
  AFTER `fecha_nacimiento`,
  ADD `fecha_egreso` DATETIME NULL DEFAULT NULL
  AFTER `celular`,
  ADD `universidad` VARCHAR(255) NULL DEFAULT NULL
  AFTER `fecha_egreso`;

ALTER TABLE `perfil_agrimensor`
  ADD `telefono` VARCHAR(255) NULL DEFAULT NULL
  AFTER `fecha_nacimiento`;

---------------------------08/07----en Produccion--------------
ALTER TABLE `perfil_agrimensor` DROP `legajo`;

ALTER TABLE `eprofesional` CHANGE `lat` `lat` DOUBLE NULL, CHANGE `lng` `lng` DOUBLE NULL;

---------------------------------------------------10/07----------en Produccion-----------------
ALTER TABLE `eprofesional` CHANGE `ods_monto` `ods_monto_total_pagado` DOUBLE NOT NULL;

---------------------------------------11/07-----------------
UPDATE `parameter` SET `value_col` = '400' WHERE `parameter`.`id` = 25

----------------------------------17/'7-----en prod----------
INSERT INTO `parameter` (`id`, `key_col`, `value_col`, `description`, `uses_class`, `data_type`) VALUES (NULL, 'pdf.ods.cupon.parrafo.visada', 'Formulario Visado Cpa', 'Texto ODS Cupon parrafo 1 Visada', 'PDF', 'String');
INSERT INTO `parameter` (`id`, `key_col`, `value_col`, `description`, `uses_class`, `data_type`) VALUES (NULL, 'pdf.ley.provincial.5216', 'Ley Provincial N° 5216', 'TEXTO en PDF', 'PDF', 'String');
UPDATE `parameter` SET `value_col` = 'ORDEN DE TRABAJO Formulada de acuerdo a la Ley Provincial N°5216 Consejo Profesional De Agrimensura de la Provincia de Río Negro.' WHERE `parameter`.`id` = 31;