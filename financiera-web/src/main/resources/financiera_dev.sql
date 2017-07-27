-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 27-07-2017 a las 21:04:01
-- Versión del servidor: 5.7.14
-- Versión de PHP: 5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `financiera_dev`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ciudad`
--

CREATE TABLE `ciudad` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `departamento_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `id` bigint(20) NOT NULL,
  `apellido` varchar(255) DEFAULT NULL,
  `celular` varchar(255) DEFAULT NULL,
  `dni` varchar(255) DEFAULT NULL,
  `domicilio` varchar(255) DEFAULT NULL,
  `fecha_nacimiento` datetime DEFAULT NULL,
  `garantia` bit(1) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `observacion` varchar(255) DEFAULT NULL,
  `telefono` varchar(255) DEFAULT NULL,
  `trabajo_horario` varchar(255) DEFAULT NULL,
  `trabajo_lugar` varchar(255) DEFAULT NULL,
  `ciudad_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `departamento`
--

CREATE TABLE `departamento` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `provincia_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `parameter`
--

CREATE TABLE `parameter` (
  `id` int(3) NOT NULL,
  `key_col` varchar(50) NOT NULL,
  `value_col` varchar(250) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `uses_class` varchar(100) DEFAULT NULL,
  `data_type` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

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
(10, 'leyenda.profesional', '\r\nTodos los datos consignados tienen carácter de declaración Jurada, solo podrán ser modificados por el profesional y serán utilizados a efectos de establecer una comunicación valida entre el CPA y el matriculado.', 'Leyenda del Profesional', 'Bean', 'String'),
(11, 'pattern.date', 'dd-MM-yyyy', 'Formato de fechas', 'Bean', 'String'),
(12, 'pattern.dateTime', 'dd-MM-yyyy HH:mm', 'Formato Fecha-Hora', 'Bean', 'String'),
(13, 'pattern.moneda', '$', 'Signo Moneda', 'Bean', 'String'),
(14, 'pdf.imagen.width', '160f', 'Ancho de imagen', 'PDF', 'Float'),
(15, 'pdf.imagen.height', '80f', 'Altura de imagen', 'PDF', 'Float'),
(16, 'pdf.aligncenter', '1', 'Alineal al centro', 'PDF', 'Integer'),
(17, 'pdf.alignright', '2', 'Alineal a la derecha', 'PDF', 'Integer'),
(18, 'pdf.aligncleft', '0', 'Alineal a la izquierda', 'PDF', 'Integer'),
(19, 'pdf.alignmiddle', '5', 'Alineal al medio', 'PDF', 'Integer'),
(20, 'pdf.font.name', 'arial', 'Nombre de la fuente', 'PDF', 'String'),
(21, 'pdf.font.size', '12', 'Tamaño de la fuente', 'PDF', 'Integer'),
(22, 'pdf.font.size.leyenda', '9', 'Tamaño de la fuente (LEYENDA)', 'PDF', 'Integer'),
(23, 'pdf.qr.absolute.y', '10f', 'Posicion QR eje y', 'PDF', 'Float'),
(24, 'pdf.qr.absolute.x', '10f', 'Posicion QR eje x', 'PDF', 'Float'),
(25, 'pdf.qr.percent', '400', 'QR Tamaño', 'PDF', 'Float'),
(26, 'pdf.font.titulo', '1', 'Fuente en negrita', 'PDF', 'Integer'),
(27, 'pdf.font.detalle', '0', 'Fuente normal', 'PDF', 'Integer'),
(28, 'pdf.font.underline', '4', 'Fuente subrayada', 'PDF', 'Integer'),
(29, 'pdf.odt.footer.tabla1', 'Se establece que el plazo de la presente orden de trabajo será de: ', 'Texto ODT footer tabla 1', 'PDF', 'String'),
(30, 'pdf.odt.footer.tabla2', 'En prueba de conformidad se firma la presente Orden de Trabajo en 3( tres) ejemplares de mismo tenor y un solo efecto en : ................................ en el dia ................................', 'Texto ODT footer tabla 2', 'PDF', 'String'),
(31, 'pdf.odt.encabezado.parrafo1', 'Por la presente ORDEN DE TRABAJO formulada de acuerdo al Art. 4° de la ley 442 y ley 3198; y art 4° apartado 6) del decreto Reglamentario 120-74 y ley 2541', 'Texto ODT encabezado parrafo 1', 'PDF', 'String'),
(32, 'pdf.odt.encabezado.parrafo2', 'Nro de ODT ', 'Texto ODT encabezado parrafo 2', 'PDF', 'String'),
(33, 'pdf.odt.encabezado.parrafo3', 'NOMENCACATASTRAL: ', 'Texto ODT encabezado parrafo 3', 'PDF', 'String'),
(34, 'pdf.ods.cupon.parrafo1', 'CUPON DE PAGO ODS DEPOSITO', 'Texto ODS Cupon parrafo 1', 'PDF', 'String'),
(35, 'pdf.ods.cupon.parrafo2', 'BANCARIO/PAGO POR CAJA', 'Texto ODS Cupon parrafo 2', 'PDF', 'String'),
(36, 'pdf.ods.cupon.parrafo3', 'Banco Patagonia Sudameris s.a.', 'Texto ODS Cupon parrafo 3', 'PDF', 'String'),
(37, 'pdf.ods.cupon.parrafo4', 'Nota de credito para la cuenta ccte Nro ……………', 'Texto ODS Cupon parrafo 4', 'PDF', 'String'),
(38, 'pdf.ods.cupon.parrafo5', 'ODT NRO ', 'Texto ODS Cupon parrafo 5', 'PDF', 'String'),
(39, 'pdf.ods.cupon.parrafo6', 'NOMENCACATASTRAL: ', 'Texto ODS Cupon parrafo 6', 'PDF', 'String'),
(40, 'pdf.ods.depositante.parrafo1', 'Sello Caja/Banco', 'Texto ODS depositante parrafo 1', 'PDF', 'String'),
(41, 'pdf.ods.depositante.parrafo2', 'Total $: ', 'Texto ODS depositante parrafo 2', 'PDF', 'String'),
(42, 'pdf.ods.depositante.parrafo3', 'Esta boleta para ser valida deberá llevar el sello del banco y firma del cajero recibidor.', 'Texto ODS depositante parrafo 3', 'PDF', 'String'),
(43, 'pdf.ods.depositante.parrafo4', 'El banco certifica únicamente el importe depositado.', 'Texto ODS depositante parrafo 4', 'PDF', 'String'),
(44, 'pdf.ods.qr.parrafo1', 'Visado Consejo Prefesional De Agrimensura de Rio Negro \r\nODS: Nro trans - ', 'Texto ODS QR parrafo 1', 'PDF', 'String'),
(45, 'pdf.ods.qr.parrafo2', 'Abonado', 'Texto ODS QR parrafo 2', 'PDF', 'String'),
(46, 'spring.mail.subject.forgot', 'Datos de acceso', 'Subject forgot mail', 'Bean', 'String'),
(47, 'spring.mail.subject.new', 'Nuevo usuario', 'Subject new mail', 'Bean', 'String'),
(48, 'spring.mail.subject.new.pass', 'Nueva contraseña', 'Subject new pass mail', 'Bean', 'String'),
(49, 'spring.mail.body', '<h3 style="color: brown;">Consejo Profesional de Agrimensura de Rio Negro</h3>', 'Body mail', 'Bean', 'String'),
(50, 'spring.mail.body.acceso', ' Su contraseña de acceso es:', 'Contraseña de acceso mail', 'Bean', 'String'),
(51, 'spring.mail.msj.activar', '<h4>Para activar el usuario ingrese al siguiente link: </h4>', 'Mensaje activar mail', 'Bean', 'String'),
(52, 'pdf.ods.cupon.parrafo.visada', 'Formulario Visado Cpa', 'Texto ODS Cupon parrafo 1 Visada', 'PDF', 'String'),
(53, 'pdf.ley.provincial.5216', 'Ley Provincial N° 5216', 'TEXTO en PDF', 'PDF', 'String');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `provincia`
--

CREATE TABLE `provincia` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `id` bigint(20) NOT NULL,
  `active` bit(1) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `intentos_de_login` int(11) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `rol` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id`, `active`, `email`, `enabled`, `intentos_de_login`, `password`, `rol`, `username`) VALUES
(1, b'1', 'fdifabio@unrn.edu.ar', b'1', NULL, '$2a$10$LsoUFkOYQJhyNjrk.LGA.em49O/cSQWwGWEEzG.x7UcYxmVKvvI9m', 'ROLE_PRESTAMISTA', 'fdifabio@unrn.edu.ar');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `ciudad`
--
ALTER TABLE `ciudad`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKev870hgt8a7wx5t21q4mnsu3k` (`departamento_id`);

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK1le6orj8lrio9vg2jdayn5kqy` (`ciudad_id`),
  ADD KEY `FK51q58w7p2gonvu6xsuyp3t2bo` (`user_id`);

--
-- Indices de la tabla `departamento`
--
ALTER TABLE `departamento`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKrt88l0yaxiht7dldscug7jnon` (`provincia_id`);

--
-- Indices de la tabla `parameter`
--
ALTER TABLE `parameter`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `key_col` (`key_col`);

--
-- Indices de la tabla `provincia`
--
ALTER TABLE `provincia`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_863n1y3x0jalatoir4325ehal` (`username`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `ciudad`
--
ALTER TABLE `ciudad`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `cliente`
--
ALTER TABLE `cliente`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `departamento`
--
ALTER TABLE `departamento`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `provincia`
--
ALTER TABLE `provincia`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
