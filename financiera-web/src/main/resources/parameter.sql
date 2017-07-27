CREATE TABLE IF NOT EXISTS `parameter` (
  `id`          INT(3)       NOT NULL AUTO_INCREMENT,
  `key_col`     VARCHAR(50)  NOT NULL,
  `value_col`   VARCHAR(520) NOT NULL,
  `description` VARCHAR(255) NULL,
  `uses_class`  VARCHAR(100) NULL,
  `data_type`   VARCHAR(255) NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = MyISAM;

ALTER TABLE `parameter` ADD UNIQUE(`key_col`);

--
-- Volcado de datos para la tabla `parameter`
--

INSERT INTO `parameter` (`id`, `key_col`, `value_col`, `description`, `uses_class`, `data_type`) VALUES
  (1, 'spring.mail.host', 'smtp.gmail.com', 'email server hostname', 'MailConfig', 'String'),
  (2, 'spring.mail.username', 'consejo.agrimensores@gmail.com', 'email server username', 'MailConfig', 'String'),
  (3, 'spring.mail.password', 'cpa.2017', 'email server password', 'MailConfig', 'String'),
  (4, 'spring.mail.port', '465', 'email server port', 'MailConfig', 'Int'),
  (5, 'spring.mail.smtp.ssl.enable', 'true', 'email server properties enable', 'MailConfig', 'String'),
  (6, 'mail.smtp.auth', 'true', 'email server properties auth', 'MailConfig', 'String'),
  (7, 'montoGEO', '100', 'Monto fondo GEO', 'EProfesional', 'Double'),
  (8, 'global.clave', 'ab', 'Clave Global', 'Constantes', 'String'),
  (9, 'random.number.bound', '0123456789ABCDEF', 'Random number bound', 'Constantes', 'String'),
  (10, 'leyenda.profesional', '\r\nLos datos personales, correos electrónicos y domicilios consignados en el presente formulario tienen el carácter de declaración jurada. Los mismos serán utilizados para la confección del Padrón de matriculados del CPA y en ellos serán válidas todas las comunicaciones y notificaciones hacia los profesionales que emanen de las autoridades del CPA, de acuerdo a lo previsto en el Artículo 1º de la Disposición 002-2017 de la Comisión Transitoria Art. 71º.', 'Leyenda del Profesional', 'Bean', 'String'),
  (11, 'pattern.date', 'dd-MM-yyyy', 'Formato de fechas', 'Bean', 'String'),
  (12, 'pattern.dateTime', 'dd-MM-yyyy HH:mm', 'Formato Fecha-Hora', 'Bean', 'String'),
  (13, 'pattern.moneda', '$', 'Signo Moneda', 'Bean', 'String');


INSERT INTO `parameter`(`key_col`, `value_col`, `description`, `uses_class`, `data_type`) VALUES
  ('pdf.imagen.width', '160f', 'Ancho de imagen', 'PDF', 'Float'),
  ('pdf.imagen.height', '80f', 'Altura de imagen', 'PDF', 'Float'),
  ('pdf.aligncenter', '1', 'Alineal al centro', 'PDF', 'Integer'),
  ('pdf.alignright', '2', 'Alineal a la derecha', 'PDF', 'Integer'),
  ('pdf.aligncleft', '0', 'Alineal a la izquierda', 'PDF', 'Integer'),
  ('pdf.alignmiddle', '5', 'Alineal al medio', 'PDF', 'Integer'),
  ('pdf.font.name', 'arial', 'Nombre de la fuente', 'PDF', 'String'),
  ('pdf.font.size', '12', 'Tamaño de la fuente', 'PDF', 'Integer'),
  ('pdf.font.size.leyenda', '9', 'Tamaño de la fuente (LEYENDA)', 'PDF', 'Integer'),
  ('pdf.qr.absolute.y', '10f', 'Posicion QR eje y', 'PDF', 'Float'),
  ('pdf.qr.absolute.x', '10f', 'Posicion QR eje x', 'PDF', 'Float'),
  ('pdf.qr.percent', '300', 'QR Tamaño', 'PDF', 'Float'),
  ('pdf.font.titulo', '1', 'Fuente en negrita', 'PDF', 'Integer'),
  ('pdf.font.detalle', '0', 'Fuente normal', 'PDF', 'Integer'),
  ('pdf.font.underline', '4', 'Fuente subrayada', 'PDF', 'Integer'),
  ('pdf.odt.footer.tabla1', 'Se establece que el plazo de la presente orden de trabajo será de: ', 'Texto ODT footer tabla 1', 'PDF', 'String'),
  ('pdf.odt.footer.tabla2', 'En prueba de conformidad se firma la presente Orden de Trabajo en 3( tres) ejemplares de mismo tenor y un solo efecto en : ................................ en el dia ................................', 'Texto ODT footer tabla 2', 'PDF', 'String'),
  ('pdf.odt.encabezado.parrafo1', 'Por la presente ORDEN DE TRABAJO formulada de acuerdo al Art. 4° de la ley 442 y ley 3198; y art 4° apartado 6) del decreto Reglamentario 120-74 y ley 2541', 'Texto ODT encabezado parrafo 1', 'PDF', 'String'),
  ('pdf.odt.encabezado.parrafo2', 'Nro de ODT ', 'Texto ODT encabezado parrafo 2', 'PDF', 'String'),
  ('pdf.odt.encabezado.parrafo3', 'NOMENCLATURA CATASTRAL: ', 'Texto ODT encabezado parrafo 3', 'PDF', 'String'),
  ('pdf.ods.cupon.parrafo1', 'CUPON DE PAGO ODS DEPOSITO', 'Texto ODS Cupon parrafo 1', 'PDF', 'String'),
  ('pdf.ods.cupon.parrafo2', 'BANCARIO/PAGO POR CAJA', 'Texto ODS Cupon parrafo 2', 'PDF', 'String'),
  ('pdf.ods.cupon.parrafo3', 'Banco Patagonia Sudameris s.a.', 'Texto ODS Cupon parrafo 3', 'PDF', 'String'),
  ('pdf.ods.cupon.parrafo4', 'Nota de crédito para la cuenta cte Nro ……………', 'Texto ODS Cupon parrafo 4', 'PDF', 'String'),
  ('pdf.ods.cupon.parrafo5', 'ODT NRO ', 'Texto ODS Cupon parrafo 5', 'PDF', 'String'),
  ('pdf.ods.cupon.parrafo6', 'NOMENCLATURA CATASTRAL: ', 'Texto ODS Cupon parrafo 6', 'PDF', 'String'),
  ('pdf.ods.depositante.parrafo1', 'Sello Caja/Banco', 'Texto ODS depositante parrafo 1', 'PDF', 'String'),
  ('pdf.ods.depositante.parrafo2', 'Total $: ', 'Texto ODS depositante parrafo 2', 'PDF', 'String'),
  ('pdf.ods.depositante.parrafo3', 'Esta boleta para ser valida deberá llevar el sello del banco y firma del cajero recibidor.', 'Texto ODS depositante parrafo 3', 'PDF', 'String'),
  ('pdf.ods.depositante.parrafo4', 'El banco certifica únicamente el importe depositado.', 'Texto ODS depositante parrafo 4', 'PDF', 'String'),
  ('pdf.ods.qr.parrafo1', 'Visado Consejo Profesional De Agrimensura de Río Negro
ODS: Nro trans - ', 'Texto ODS QR parrafo 1', 'PDF', 'String'),
  ('pdf.ods.qr.parrafo2', 'Abonado', 'Texto ODS QR parrafo 2', 'PDF', 'String');
INSERT INTO `parameter` (`id`, `key_col`, `value_col`, `description`, `uses_class`, `data_type`) VALUES (NULL, 'pdf.ods.cupon.parrafo.visada', 'Formulario Visado Cpa', 'Texto ODS Cupon parrafo 1 Visada', 'PDF', 'String');
INSERT INTO `parameter` (`id`, `key_col`, `value_col`, `description`, `uses_class`, `data_type`) VALUES (NULL, 'pdf.ley.provincial.5216', 'Ley Provincial N° 5216', 'TEXTO en PDF', 'PDF', 'String');

INSERT INTO `parameter` (`key_col`, `value_col`, `description`, `uses_class`, `data_type`) VALUES
  ('spring.mail.subject.forgot', 'Datos de acceso', 'Subject forgot mail', 'Bean', 'String'),
  ('spring.mail.subject.new', 'Nuevo usuario', 'Subject new mail', 'Bean', 'String'),
  ('spring.mail.subject.new.pass', 'Nueva contraseña', 'Subject new pass mail', 'Bean', 'String'),
  ('spring.mail.body', '<h3 style="color: brown;">Consejo Profesional de Agrimensura de Rio Negro</h3>', 'Body mail', 'Bean', 'String'),
  ('spring.mail.body.acceso', 'Su contraseña de acceso es:', 'Contraseña de acceso mail', 'Bean', 'String'),
  ('spring.mail.msj.activar','<h4>Para activar el usuario ingrese al siguiente link: </h4>','Mensaje activar mail', 'Bean', 'String');
--
-- Índices para tablas volcadas
--
ALTER TABLE `parameter` CHANGE `value_col` `value_col` VARCHAR(520) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL;
--
-- Indices de la tabla `parameter`
--
ALTER TABLE `parameter`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `parameter`
--
ALTER TABLE `parameter`
  MODIFY `id` INT(3) NOT NULL AUTO_INCREMENT,
  AUTO_INCREMENT = 19;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
