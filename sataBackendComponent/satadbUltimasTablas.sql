-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 18, 2023 at 01:15 AM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `satadb`
--
CREATE DATABASE IF NOT EXISTS `satadb` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;
USE `satadb`;

-- --------------------------------------------------------

--
-- Table structure for table `archivo`
--

DROP TABLE IF EXISTS `archivo`;
CREATE TABLE `archivo` (
  `id` bigint(20) NOT NULL,
  `id_equipo` bigint(20) NOT NULL,
  `data` blob NOT NULL,
  `tipo` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `componente`
--

DROP TABLE IF EXISTS `componente`;
CREATE TABLE `componente` (
  `id` bigint(20) NOT NULL,
  `id_equipo` bigint(20) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  `url` varchar(100) NOT NULL,
  `estado` varchar(20) NOT NULL,
  `tipo` varchar(20) NOT NULL,
  `tipo_placa` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `componente`
--

INSERT INTO `componente` (`id`, `id_equipo`, `nombre`, `descripcion`, `url`, `estado`, `tipo`, `tipo_placa`) VALUES
(1, 1, 'Sensor1', 'sensorcito equis de', 'a', 'estado1', 'Pluviometro', 'tipo1'),
(2, 1, 'sensor2', 'b', 'a', 'estado1', 'Humedad', 'tipo1');

-- --------------------------------------------------------

--
-- Table structure for table `ejecucion`
--

DROP TABLE IF EXISTS `ejecucion`;
CREATE TABLE `ejecucion` (
  `id` bigint(20) NOT NULL,
  `id_simulacion` bigint(20) NOT NULL,
  `agua_caida` double NOT NULL DEFAULT 0,
  `fecha_ejecucion` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `ejecucionsecuencia`
--

DROP TABLE IF EXISTS `ejecucionsecuencia`;
CREATE TABLE `ejecucionsecuencia` (
  `id` bigint(20) NOT NULL,
  `id_ejecucion` bigint(20) NOT NULL,
  `id_secuencia` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `entidad`
--

DROP TABLE IF EXISTS `entidad`;
CREATE TABLE `entidad` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `entidad`
--

INSERT INTO `entidad` (`id`, `nombre`) VALUES
(2, 'EQUIPO'),
(3, 'SIMULACION'),
(1, 'USUARIO');

-- --------------------------------------------------------

--
-- Table structure for table `equipo`
--

DROP TABLE IF EXISTS `equipo`;
CREATE TABLE `equipo` (
  `id` bigint(20) NOT NULL,
  `id_configurador` bigint(20) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  `url_repositorio` varchar(100) NOT NULL,
  `estado` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `equipo`
--

INSERT INTO `equipo` (`id`, `id_configurador`, `nombre`, `descripcion`, `url_repositorio`, `estado`) VALUES
(1, 4, 'simulador12', 'A12EHEHEH', 'www.simu12.cl', 'PROTOTIPO'),
(2, 4, 'Genesis77', 'This is for entry test', 'www.gen77.com', 'PROTOTIPO'),
(3, 4, 'Sigma35', 'This is for entry test', 'www.sig35.com', 'PROTOTIPO'),
(4, 4, 'Fixflex9969577', 'Balanced tertiary emulation', 'google.com.au', 'PROTOTIPO'),
(5, 4, 'Overhold3165893', 'Intuitive bandwidth-monitored data-warehouse', 'ted.com', 'PROTOTIPO'),
(6, 4, 'Lotlux1715575', 'Re-engineered mobile workforce', 'woothemes.com', 'PROTOTIPO'),
(7, 4, 'Opela4399628', 'Assimilated motivating leverage', 'blogtalkradio.com', 'PROTOTIPO'),
(8, 4, 'Zoolab5150580', 'Inverse incremental website', '123-reg.co.uk', 'PROTOTIPO'),
(9, 4, 'Tin6270153', 'Mandatory holistic paradigm', 'bloglines.com', 'PROTOTIPO'),
(10, 4, 'Rank9395712', 'Streamlined motivating service-desk', 'accuweather.com', 'PROTOTIPO'),
(11, 4, 'Tres-Zap7856705', 'Switchable bandwidth-monitored help-desk', 'amazon.co.jp', 'PROTOTIPO'),
(12, 4, 'Fix San7823121', 'Inverse discrete migration', 'printfriendly.com', 'PROTOTIPO'),
(13, 4, 'Rank1822447', 'Future-proofed local success', 'lycos.com', 'PROTOTIPO'),
(14, 4, 'Otcom5264468', 'Quality-focused multi-state leverage', 'uol.com.br', 'PROTOTIPO'),
(15, 4, 'Namfix5141708', 'Organized fault-tolerant website', 'nhs.uk', 'PROTOTIPO'),
(16, 4, 'Namfix6742300', 'Enterprise-wide bifurcated time-frame', 'amazon.de', 'PROTOTIPO'),
(17, 4, 'Alphazap6320885', 'Advanced neutral application', 'sitemeter.com', 'PROTOTIPO'),
(18, 4, 'Bigtax9598852', 'Down-sized demand-driven contingency', 'icq.com', 'PROTOTIPO'),
(19, 4, 'Transcof5981912', 'Fundamental non-volatile hardware', 'howstuffworks.com', 'PROTOTIPO'),
(20, 4, 'Alpha4189578', 'Distributed secondary Graphic Interface', 'adobe.com', 'PROTOTIPO'),
(21, 4, 'Bytecard3482303', 'Switchable explicit frame', 'youtu.be', 'PROTOTIPO'),
(22, 4, 'Flowdesk3607288', 'Seamless mobile local area network', 'alibaba.com', 'PROTOTIPO'),
(23, 4, 'Home Ing6957745', 'Proactive web-enabled process improvement', 'nbcnews.com', 'PROTOTIPO'),
(24, 4, 'Opela8018812', 'Fully-configurable analyzing access', 'mapy.cz', 'PROTOTIPO'),
(25, 4, 'Tempsoft564453', 'Optimized object-oriented workforce', 'taobao.com', 'PROTOTIPO'),
(26, 4, 'Keylex8952243', 'Assimilated 4th generation model', 'github.com', 'PROTOTIPO'),
(27, 4, 'Asoka3515013', 'Public-key needs-based task-force', 'php.net', 'PROTOTIPO'),
(28, 4, 'Zathin6707680', 'Open-source discrete service-desk', 'macromedia.com', 'PROTOTIPO'),
(29, 4, 'Bitchip2673237', 'Assimilated solution-oriented extranet', 'webeden.co.uk', 'PROTOTIPO'),
(30, 4, 'Zontrax963811', 'Innovative zero administration collaboration', 'barnesandnoble.com', 'PROTOTIPO'),
(31, 4, 'Y-find4506166', 'Grass-roots homogeneous focus group', 'ovh.net', 'PROTOTIPO'),
(32, 4, 'Trippledex4917593', 'Vision-oriented motivating moratorium', 'xrea.com', 'PROTOTIPO'),
(33, 4, 'Flowdesk1644723', 'Right-sized uniform migration', 'wikispaces.com', 'PROTOTIPO'),
(34, 4, 'Job7743989', 'Monitored 24/7 pricing structure', 'slideshare.net', 'PROTOTIPO'),
(35, 4, 'Ronstring8555613', 'Configurable demand-driven capability', 'ucoz.ru', 'PROTOTIPO'),
(36, 4, 'Treeflex6952943', 'Innovative context-sensitive initiative', 'infoseek.co.jp', 'PROTOTIPO'),
(37, 4, 'Viva5515019', 'Inverse bifurcated intranet', 'pagesperso-orange.fr', 'PROTOTIPO'),
(38, 4, 'Rank7129941', 'Future-proofed optimizing access', 'bing.com', 'PROTOTIPO'),
(39, 4, 'Holdlamis2869634', 'Adaptive executive function', 'discuz.net', 'PROTOTIPO'),
(40, 4, 'Regrant475237', 'User-friendly intermediate encryption', 'blogtalkradio.com', 'PROTOTIPO'),
(41, 4, 'Alpha2463460', 'Integrated bifurcated application', 'w3.org', 'PROTOTIPO'),
(42, 4, 'Sub-Ex4368929', 'Focused dedicated open architecture', 'over-blog.com', 'PROTOTIPO'),
(43, 4, 'Alpha3421192', 'Reverse-engineered clear-thinking conglomeration', 'illinois.edu', 'PROTOTIPO'),
(44, 4, 'Transcof1012518', 'Assimilated multi-state interface', 'nytimes.com', 'PROTOTIPO'),
(45, 4, 'Trippledex8718447', 'Innovative homogeneous open system', 'ning.com', 'PROTOTIPO'),
(46, 4, 'Bigtax5898153', 'Ameliorated zero tolerance open architecture', 'myspace.com', 'PROTOTIPO'),
(47, 4, 'Regrant7674664', 'Cross-group dedicated attitude', 'blinklist.com', 'PROTOTIPO'),
(48, 4, 'Lotlux2337826', 'Expanded solution-oriented workforce', 'paypal.com', 'PROTOTIPO'),
(49, 4, 'Y-Solowarm1713042', 'Cloned secondary orchestration', 'geocities.jp', 'PROTOTIPO'),
(50, 4, 'Subin8125712', 'Optimized human-resource Graphic Interface', 'phpbb.com', 'PROTOTIPO'),
(51, 4, 'Prodder7473469', 'Persistent well-modulated matrices', 'jalbum.net', 'PROTOTIPO'),
(52, 4, 'Bitchip3854130', 'Adaptive system-worthy artificial intelligence', 'walmart.com', 'PROTOTIPO'),
(53, 4, 'Zathin2448468', 'Configurable clear-thinking process improvement', 'globo.com', 'PROTOTIPO'),
(54, 4, 'Flexidy7567111', 'Optimized attitude-oriented emulation', 'istockphoto.com', 'PROTOTIPO'),
(55, 4, 'Ronstring7770859', 'Face to face real-time adapter', 't-online.de', 'PROTOTIPO'),
(56, 4, 'Treeflex1265128', 'Distributed 24/7 matrix', 'ihg.com', 'PROTOTIPO'),
(57, 4, 'Namfix5057372', 'Up-sized fault-tolerant customer loyalty', 'umn.edu', 'PROTOTIPO'),
(58, 4, 'Gembucket1647130', 'Virtual clear-thinking hierarchy', 'amazon.com', 'PROTOTIPO'),
(59, 4, 'Bamity3677201', 'Pre-emptive client-server Graphical User Interface', 'cloudflare.com', 'PROTOTIPO'),
(60, 4, 'Namfix3659835', 'Business-focused local protocol', 'arizona.edu', 'PROTOTIPO'),
(61, 4, 'Bytecard7794541', 'Ameliorated homogeneous info-mediaries', 'java.com', 'PROTOTIPO'),
(62, 4, 'Veribet5804765', 'Multi-layered zero tolerance structure', 'ftc.gov', 'PROTOTIPO'),
(63, 4, 'Zontrax7525219', 'Robust content-based hardware', 'yellowbook.com', 'PROTOTIPO'),
(64, 4, 'Alpha676152', 'Ergonomic directional access', 'yellowbook.com', 'PROTOTIPO'),
(65, 4, 'Voyatouch5753370', 'Cross-group multi-state secured line', 'rambler.ru', 'PROTOTIPO'),
(66, 4, 'Andalax7375539', 'Visionary encompassing challenge', 'ed.gov', 'PROTOTIPO'),
(67, 4, 'Aerified4738589', 'Focused actuating structure', 'youtu.be', 'PROTOTIPO'),
(68, 4, 'Zaam-Dox2759346', 'Ameliorated intangible budgetary management', 'comsenz.com', 'PROTOTIPO'),
(69, 4, 'Stim3485095', 'Enterprise-wide composite task-force', 'desdev.cn', 'PROTOTIPO'),
(70, 4, 'Stim914369', 'Centralized grid-enabled frame', 'seattletimes.com', 'PROTOTIPO'),
(71, 4, 'Tres-Zap5603473', 'Ameliorated actuating budgetary management', 'hibu.com', 'PROTOTIPO'),
(72, 4, 'Home Ing5321883', 'Robust logistical capacity', 'icq.com', 'PROTOTIPO'),
(73, 4, 'Subin8909338', 'Stand-alone local artificial intelligence', 'smh.com.au', 'PROTOTIPO'),
(74, 4, 'Greenlam698324', 'Extended didactic standardization', 'netlog.com', 'PROTOTIPO'),
(75, 4, 'Cardify9831414', 'Profit-focused bottom-line standardization', 'oakley.com', 'PROTOTIPO'),
(76, 4, 'Bigtax8286931', 'Multi-channelled bottom-line monitoring', 'google.it', 'PROTOTIPO'),
(77, 4, 'Voyatouch1714813', 'Face to face high-level project', 'auda.org.au', 'PROTOTIPO'),
(78, 4, 'Bitwolf4768320', 'Exclusive dedicated Graphical User Interface', 'earthlink.net', 'PROTOTIPO'),
(79, 4, 'Job8246066', 'Universal background software', 'cnn.com', 'PROTOTIPO'),
(80, 4, 'Tin2151171', 'Customer-focused 24/7 system engine', 'gizmodo.com', 'PROTOTIPO'),
(81, 4, 'Mat Lam Tam7101875', 'Team-oriented interactive throughput', 'paypal.com', 'PROTOTIPO'),
(82, 4, 'Konklux263064', 'Streamlined mobile budgetary management', 'free.fr', 'PROTOTIPO'),
(83, 4, 'Pannier3736937', 'Organized well-modulated benchmark', 'telegraph.co.uk', 'PROTOTIPO'),
(84, 4, 'Stim7997717', 'Managed 3rd generation customer loyalty', 'goo.ne.jp', 'PROTOTIPO'),
(85, 4, 'Tin2354605', 'Assimilated zero administration Graphical User Interface', 'cnbc.com', 'PROTOTIPO'),
(86, 4, 'Trippledex1993797', 'Public-key bifurcated archive', 'netlog.com', 'PROTOTIPO'),
(87, 4, 'Flowdesk4584604', 'Enterprise-wide bottom-line core', 'ameblo.jp', 'PROTOTIPO'),
(88, 4, 'Asoka7101617', 'Function-based needs-based superstructure', 'apache.org', 'PROTOTIPO'),
(89, 4, 'Bitwolf5015801', 'User-friendly asymmetric service-desk', 'gizmodo.com', 'PROTOTIPO'),
(90, 4, 'Domainer9072419', 'Versatile multimedia parallelism', 'ezinearticles.com', 'PROTOTIPO'),
(91, 4, 'Biodex7058566', 'Automated radical Graphic Interface', 'ucsd.edu', 'PROTOTIPO'),
(92, 4, 'Namfix1168702', 'Compatible maximized portal', 'i2i.jp', 'PROTOTIPO'),
(93, 4, 'Tampflex7812206', 'Customer-focused uniform concept', 'washington.edu', 'PROTOTIPO'),
(94, 4, 'Bamity2493921', 'Distributed modular portal', 'dion.ne.jp', 'PROTOTIPO'),
(95, 4, 'Voyatouch3271582', 'Face to face directional Graphic Interface', 'sbwire.com', 'PROTOTIPO'),
(96, 4, 'Opela6203964', 'Reduced executive challenge', 'howstuffworks.com', 'PROTOTIPO'),
(97, 4, 'Mat Lam Tam1685754', 'Team-oriented methodical budgetary management', 'wix.com', 'PROTOTIPO'),
(98, 4, 'Redhold9596382', 'Organized heuristic hub', 'photobucket.com', 'PROTOTIPO'),
(99, 4, 'Cookley7070793', 'User-centric heuristic software', 'tuttocitta.it', 'PROTOTIPO'),
(100, 4, 'Flexidy4653059', 'Virtual demand-driven middleware', 'chron.com', 'PROTOTIPO');

-- --------------------------------------------------------

--
-- Table structure for table `evento`
--

DROP TABLE IF EXISTS `evento`;
CREATE TABLE `evento` (
  `id` bigint(20) NOT NULL,
  `id_secuencia` bigint(20) NOT NULL,
  `intensidad` int(11) NOT NULL,
  `duracion` int(11) NOT NULL,
  `posicion` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `pin`
--

DROP TABLE IF EXISTS `pin`;
CREATE TABLE `pin` (
  `id` bigint(20) NOT NULL,
  `id_placa` bigint(20) NOT NULL,
  `id_componente` bigint(20) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `numero` int(11) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  `conexion` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `placa`
--

DROP TABLE IF EXISTS `placa`;
CREATE TABLE `placa` (
  `id` bigint(20) NOT NULL,
  `id_equipo` bigint(20) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  `tipo` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `registro`
--

DROP TABLE IF EXISTS `registro`;
CREATE TABLE `registro` (
  `id` bigint(20) NOT NULL,
  `id_usuario` bigint(20) NOT NULL,
  `id_entidad` bigint(20) NOT NULL,
  `fecha` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `descripcion` varchar(255) NOT NULL,
  `tipo_registro` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `secuencia`
--

DROP TABLE IF EXISTS `secuencia`;
CREATE TABLE `secuencia` (
  `id` bigint(20) NOT NULL,
  `id_simulacion` bigint(20) NOT NULL,
  `id_componente` bigint(20) NOT NULL,
  `mes` int(11) NOT NULL,
  `id_sensor` bigint(20) NOT NULL,
  `id_ejecucion` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `simulacion`
--

DROP TABLE IF EXISTS `simulacion`;
CREATE TABLE `simulacion` (
  `id` bigint(20) NOT NULL,
  `id_equipo` bigint(20) NOT NULL,
  `id_operador` bigint(20) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  `fecha_creacion` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `caudal` bigint(20) NOT NULL,
  `temperatura` bigint(20) NOT NULL,
  `pluviometro` bigint(20) NOT NULL,
  `presion` bigint(20) NOT NULL,
  `humedad` bigint(20) NOT NULL,
  `id_ejecucion` bigint(20) NOT NULL,
  `id_sensor` bigint(20) NOT NULL,
  `ultima_medida` text NOT NULL,
  `timestamp` text NOT NULL,
  `last_second` int(11) NOT NULL,
  `last_entrities` int(11) NOT NULL,
  `mes` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
CREATE TABLE `usuario` (
  `id` bigint(20) NOT NULL,
  `rut` varchar(10) NOT NULL,
  `email` varchar(320) NOT NULL,
  `password` varchar(100) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `apellido` varchar(30) NOT NULL,
  `rol` varchar(15) NOT NULL,
  `estado` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `usuario`
--

INSERT INTO `usuario` (`id`, `rut`, `email`, `password`, `nombre`, `apellido`, `rol`, `estado`) VALUES
(2, '22334455-k', 'donald@ucn.cl', '$argon2id$v=19$m=1024,t=1,p=1$POQ8x+MLMR0wHtZwUGMljQ$X7dlZ9JyqaVqfFJix58UDxoOmhhP70VTNqlFOO9RW/E', 'donald', 'trump', 'OPERADOR', 'ACTIVO'),
(3, '44556677-k', 'eric@ucn.cl', '$argon2id$v=19$m=1024,t=1,p=1$McvMVXHfOVrzVy1MWrh6PQ$tmzWYItawgFV70nCxYyvqgwicmIvN7b/d+9n6byI+WA', 'eric', 'cartman', 'ADMINISTRADOR', 'ACTIVO'),
(4, '33445566-k', 'obama@ucn.cl', '$argon2id$v=19$m=1024,t=1,p=1$15PH6cBBjiuzOQ6zkMY+HA$6POBJBjpgQx5YSqKj26jJI9ftD4H3sLsgUBaNOlcOEs', 'obama', 'obamium', 'CONFIGURADOR', 'ACTIVO');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `archivo`
--
ALTER TABLE `archivo`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_equipo` (`id_equipo`);

--
-- Indexes for table `componente`
--
ALTER TABLE `componente`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_equipo` (`id_equipo`);

--
-- Indexes for table `ejecucion`
--
ALTER TABLE `ejecucion`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_simulacion` (`id_simulacion`);

--
-- Indexes for table `ejecucionsecuencia`
--
ALTER TABLE `ejecucionsecuencia`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_ejecucion` (`id_ejecucion`),
  ADD KEY `id_secuencia` (`id_secuencia`);

--
-- Indexes for table `entidad`
--
ALTER TABLE `entidad`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nombre` (`nombre`);

--
-- Indexes for table `equipo`
--
ALTER TABLE `equipo`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nombre` (`nombre`),
  ADD KEY `id_configurador` (`id_configurador`);

--
-- Indexes for table `evento`
--
ALTER TABLE `evento`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_secuencia` (`id_secuencia`);

--
-- Indexes for table `pin`
--
ALTER TABLE `pin`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_placa` (`id_placa`),
  ADD KEY `id_componente` (`id_componente`);

--
-- Indexes for table `placa`
--
ALTER TABLE `placa`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_equipo` (`id_equipo`);

--
-- Indexes for table `registro`
--
ALTER TABLE `registro`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_usuario` (`id_usuario`),
  ADD KEY `id_entidad` (`id_entidad`);

--
-- Indexes for table `secuencia`
--
ALTER TABLE `secuencia`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_simulacion` (`id_simulacion`),
  ADD KEY `id_componente` (`id_componente`);

--
-- Indexes for table `simulacion`
--
ALTER TABLE `simulacion`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nombre` (`nombre`),
  ADD KEY `id_equipo` (`id_equipo`),
  ADD KEY `id_operador` (`id_operador`);

--
-- Indexes for table `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `rut` (`rut`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `archivo`
--
ALTER TABLE `archivo`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `componente`
--
ALTER TABLE `componente`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `ejecucion`
--
ALTER TABLE `ejecucion`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `ejecucionsecuencia`
--
ALTER TABLE `ejecucionsecuencia`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `entidad`
--
ALTER TABLE `entidad`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `equipo`
--
ALTER TABLE `equipo`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=101;

--
-- AUTO_INCREMENT for table `evento`
--
ALTER TABLE `evento`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `pin`
--
ALTER TABLE `pin`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `placa`
--
ALTER TABLE `placa`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `registro`
--
ALTER TABLE `registro`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `secuencia`
--
ALTER TABLE `secuencia`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `simulacion`
--
ALTER TABLE `simulacion`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `archivo`
--
ALTER TABLE `archivo`
  ADD CONSTRAINT `archivo_ibfk_1` FOREIGN KEY (`id_equipo`) REFERENCES `equipo` (`id`);

--
-- Constraints for table `componente`
--
ALTER TABLE `componente`
  ADD CONSTRAINT `componente_ibfk_1` FOREIGN KEY (`id_equipo`) REFERENCES `equipo` (`id`);

--
-- Constraints for table `ejecucion`
--
ALTER TABLE `ejecucion`
  ADD CONSTRAINT `ejecucion_ibfk_1` FOREIGN KEY (`id_simulacion`) REFERENCES `simulacion` (`id`);

--
-- Constraints for table `ejecucionsecuencia`
--
ALTER TABLE `ejecucionsecuencia`
  ADD CONSTRAINT `ejecucionsecuencia_ibfk_1` FOREIGN KEY (`id_ejecucion`) REFERENCES `ejecucion` (`id`),
  ADD CONSTRAINT `ejecucionsecuencia_ibfk_2` FOREIGN KEY (`id_secuencia`) REFERENCES `secuencia` (`id`);

--
-- Constraints for table `equipo`
--
ALTER TABLE `equipo`
  ADD CONSTRAINT `equipo_ibfk_1` FOREIGN KEY (`id_configurador`) REFERENCES `usuario` (`id`);

--
-- Constraints for table `evento`
--
ALTER TABLE `evento`
  ADD CONSTRAINT `evento_ibfk_1` FOREIGN KEY (`id_secuencia`) REFERENCES `secuencia` (`id`);

--
-- Constraints for table `pin`
--
ALTER TABLE `pin`
  ADD CONSTRAINT `pin_ibfk_1` FOREIGN KEY (`id_placa`) REFERENCES `placa` (`id`),
  ADD CONSTRAINT `pin_ibfk_2` FOREIGN KEY (`id_componente`) REFERENCES `componente` (`id`);

--
-- Constraints for table `placa`
--
ALTER TABLE `placa`
  ADD CONSTRAINT `placa_ibfk_1` FOREIGN KEY (`id_equipo`) REFERENCES `equipo` (`id`);

--
-- Constraints for table `registro`
--
ALTER TABLE `registro`
  ADD CONSTRAINT `registro_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`),
  ADD CONSTRAINT `registro_ibfk_2` FOREIGN KEY (`id_entidad`) REFERENCES `entidad` (`id`);

--
-- Constraints for table `secuencia`
--
ALTER TABLE `secuencia`
  ADD CONSTRAINT `secuencia_ibfk_1` FOREIGN KEY (`id_simulacion`) REFERENCES `simulacion` (`id`),
  ADD CONSTRAINT `secuencia_ibfk_2` FOREIGN KEY (`id_componente`) REFERENCES `componente` (`id`);

--
-- Constraints for table `simulacion`
--
ALTER TABLE `simulacion`
  ADD CONSTRAINT `simulacion_ibfk_1` FOREIGN KEY (`id_equipo`) REFERENCES `equipo` (`id`),
  ADD CONSTRAINT `simulacion_ibfk_2` FOREIGN KEY (`id_operador`) REFERENCES `usuario` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
