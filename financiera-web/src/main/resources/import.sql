-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 23-05-2017 a las 13:24:28
-- Versión del servidor: 5.7.14
-- Versión de PHP: 5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `consejo_dev`
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

--
-- Volcado de datos para la tabla `ciudad`
--

INSERT INTO `ciudad` (`id`, `nombre`, `departamento_id`) VALUES
  (1, 'Viedma', 1),
  (2, 'San Javier', 1),
  (3, 'El Juncal', 1),
  (4, 'Guardia Mitre', 1),
  (5, 'El Condor', 1),
  (6, 'Las Bayas', 2),
  (7, 'Ojos de Agua', 2),
  (8, 'Rio Chico', 2),
  (9, 'Ñorquinco', 2),
  (10, 'Mamuel', 2),
  (11, 'Choele Choel', 5),
  (12, 'Lamarque', 5),
  (13, 'Chimpay', 5),
  (14, 'Pomona', 5),
  (15, 'Luis Beltran', 5),
  (16, 'El Bolson', 6),
  (17, 'El Manso', 6),
  (18, 'Villa Campanario', 6),
  (19, 'San Carlos', 6),
  (20, 'Rio Villegas', 6),
  (21, 'Gral Conesa', 7),
  (22, 'El Cuy', 8),
  (23, 'Las Perlas', 8),
  (24, 'Mencue', 8),
  (25, 'Cerro Policia', 8),
  (26, 'Valle Azul', 8),
  (27, 'Allen', 9),
  (28, 'Catriel', 9),
  (29, 'Cipolletti', 9),
  (30, 'Ferri', 9),
  (31, 'Paso Cordoba', 9),
  (32, 'Chichinales', 9),
  (33, 'Comico', 10),
  (34, 'Treneta', 10),
  (35, 'Cona niyeu', 10),
  (36, 'Yaminue', 10),
  (37, 'Sierra Colorada', 10);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `departamento`
--

CREATE TABLE `departamento` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `provincia_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `departamento`
--

INSERT INTO `departamento` (`id`, `nombre`, `provincia_id`) VALUES
  (1, 'Adolfo Alsina', 1),
  (2, 'Ñorquinco', 1),
  (5, 'Avellaneda', 1),
  (6, 'Bariloche', 1),
  (7, 'Conesa', 1),
  (8, 'El Cuy', 1),
  (9, 'Gral. Roca', 1),
  (10, '9 de Julio', 1),
  (11, 'Pichi Mauida', 1),
  (12, 'Pilcaniyeu', 1),
  (13, 'San Antonio', 1),
  (14, 'Valcheta', 1),
  (15, '25 de Mayo', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empresa`
--

CREATE TABLE `empresa` (
  `id` bigint(20) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `cuit` varchar(255) DEFAULT NULL,
  `domicilio` varchar(255) DEFAULT NULL,
  `ciudad_id` bigint(20) DEFAULT NULL,
  `resp_cargo` varchar(255) DEFAULT NULL,
  `resp_dni` varchar(255) DEFAULT NULL,
  `resp_email` varchar(255) DEFAULT NULL,
  `resp_nombre` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `eprofesional`
--

CREATE TABLE `eprofesional` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `fecha` tinyblob,
  `estado` varchar(255) NOT NULL,
  `lat` double NOT NULL,
  `lng` double NOT NULL,
  `empresa_id` bigint(20) DEFAULT NULL,
  `inmueble_id` bigint(20) DEFAULT NULL,
  `perfil_agrimensor_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inmueble`
--

CREATE TABLE `inmueble` (
  `id` bigint(20) NOT NULL,
  `parcela` varchar(255) DEFAULT NULL,
  `mzaQta` varchar(255) DEFAULT NULL,
  `seccion` varchar(255) DEFAULT NULL,
  `tipo` varchar(255) NOT NULL,
  `departamento_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `perfil_agrimensor`
--

CREATE TABLE `perfil_agrimensor` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `apellido` varchar(255) DEFAULT NULL,
  `titulo` varchar(255) DEFAULT NULL,
  `domicilio` varchar(255) DEFAULT NULL,
  `ciudad_id` bigint(20) DEFAULT NULL,
  `matricula` varchar(255) DEFAULT NULL,
  `cuit` varchar(255) DEFAULT NULL,
  `iva` varchar(255) DEFAULT NULL,
  `ingresoBrutos` varchar(255) DEFAULT NULL,
  `legajo` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `perfil_agrimensor`
--

INSERT INTO `perfil_agrimensor` (`id`, `nombre`, `apellido`, `titulo`, `domicilio`, `ciudad_id`, `matricula`, `cuit`, `iva`, `ingresoBrutos`, `legajo`, `user_id`) VALUES
  (1, 'Guillermo', 'Difabio', NULL, NULL, NULL, 'gdifabio', NULL, NULL, NULL, NULL, 1),
  (2, 'Federico', 'Difabio', NULL, NULL, NULL, 'fdifabio', NULL, NULL, NULL, NULL, 2),
  (5, 'Lucas', 'Difabio', NULL, NULL, NULL, 'ldifabio', NULL, NULL, NULL, NULL, 5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `provincia`
--

CREATE TABLE `provincia` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `provincia`
--

INSERT INTO `provincia` (`id`, `nombre`) VALUES
  (1, 'Río Negro');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ubicacion`
--

CREATE TABLE `ubicacion` (
  `id` bigint(20) NOT NULL,
  `calle` varchar(255) DEFAULT NULL,
  `ciudad_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `id` bigint(20) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `rol` varchar(255) NOT NULL,
  `active` bit(1) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `intentos_de_login` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id`, `username`, `password`, `email`, `rol`, `active`, `enabled`, `intentos_de_login`) VALUES
  (1, 'gdifabio', '$2a$10$/3c1Exn2LO04i5uRZegN2..iUzSIE.mZ6/DZWteNllMftWDUVrJGS', 'gdifabio@unrn.edu.ar', 'ROLE_AGR', b'1', b'1', 0),
  (2, 'fdifabio', '$2a$10$/3c1Exn2LO04i5uRZegN2..iUzSIE.mZ6/DZWteNllMftWDUVrJGS', 'fdifabio@unrn.edu.ar', 'ROLE_ADMIN', b'1', b'1', 0),
  (5, 'ldifabio', '$2a$10$HJFecfuxvPlwBp9XqT7fMuVeX0oXJoRATBjNOfkrPRPGseA2kcZ4G', 'ldifabio@unrn.edu.ar', 'ROLE_AGR', b'1', b'1', 0);

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
-- Indices de la tabla `departamento`
--
ALTER TABLE `departamento`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKrt88l0yaxiht7dldscug7jnon` (`provincia_id`);

--
-- Indices de la tabla `empresa`
--
ALTER TABLE `empresa`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKfqkswwwaj91l9lpd5ay9hfstm` (`ciudad_id`);

--
-- Indices de la tabla `eprofesional`
--
ALTER TABLE `eprofesional`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKahl6sljvk6hj9qn665di2364i` (`empresa_id`),
  ADD KEY `FK7qfteithorg7120082cchexr` (`inmueble_id`),
  ADD KEY `FK85eg2aaw49gj9uxfxsf8cyq32` (`perfil_agrimensor_id`);

--
-- Indices de la tabla `inmueble`
--
ALTER TABLE `inmueble`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKeg8bg67enux6tvsenoi3i124g` (`departamento_id`);

--
-- Indices de la tabla `perfil_agrimensor`
--
ALTER TABLE `perfil_agrimensor`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKgrd8jeefji5c4xyc5vnjhxqgt` (`ciudad_id`),
  ADD KEY `FKljukt4c9g90b5gnnhi8gogm2t` (`user_id`);

--
-- Indices de la tabla `provincia`
--
ALTER TABLE `provincia`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `ubicacion`
--
ALTER TABLE `ubicacion`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKd7hhv1mvfyh60562d65st8o50` (`ciudad_id`);

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
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;
--
-- AUTO_INCREMENT de la tabla `departamento`
--
ALTER TABLE `departamento`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
--
-- AUTO_INCREMENT de la tabla `empresa`
--
ALTER TABLE `empresa`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `eprofesional`
--
ALTER TABLE `eprofesional`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `inmueble`
--
ALTER TABLE `inmueble`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `perfil_agrimensor`
--
ALTER TABLE `perfil_agrimensor`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT de la tabla `provincia`
--
ALTER TABLE `provincia`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT de la tabla `ubicacion`
--
ALTER TABLE `ubicacion`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
