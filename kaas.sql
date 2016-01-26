-- --------------------------------------------------------
-- Host:                         145.97.16.190
-- Server versie:                5.5.44-0+deb7u1 - (Debian)
-- Server OS:                    debian-linux-gnu
-- HeidiSQL Versie:              9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Structuur van  tabel IPSEN3G6.account wordt geschreven
CREATE TABLE IF NOT EXISTS `account` (
  `klant_email` varchar(255) NOT NULL,
  `account_isklant` tinyint(1) NOT NULL,
  `account_islid` tinyint(1) NOT NULL,
  `account_isms` tinyint(1) NOT NULL,
  `account_isadmin` tinyint(1) NOT NULL,
  `account_password` varchar(50) NOT NULL,
  `account_isactief` tinyint(1) NOT NULL,
  PRIMARY KEY (`klant_email`),
  CONSTRAINT `account_ibfk_1` FOREIGN KEY (`klant_email`) REFERENCES `klant` (`klant_email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumpen data van tabel IPSEN3G6.account: ~0 rows (ongeveer)
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
/*!40000 ALTER TABLE `account` ENABLE KEYS */;


-- Structuur van  tabel IPSEN3G6.factuur wordt geschreven
CREATE TABLE IF NOT EXISTS `factuur` (
  `factuur_order_id` int(255) NOT NULL,
  `factuur_factuur` blob NOT NULL,
  `factuur_isVerzonden` int(1) NOT NULL,
  KEY `factuur_ibfk_1` (`factuur_order_id`),
  CONSTRAINT `factuur_ibfk_1` FOREIGN KEY (`factuur_order_id`) REFERENCES `order` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumpen data van tabel IPSEN3G6.factuur: ~0 rows (ongeveer)
/*!40000 ALTER TABLE `factuur` DISABLE KEYS */;
/*!40000 ALTER TABLE `factuur` ENABLE KEYS */;


-- Structuur van  tabel IPSEN3G6.klant wordt geschreven
CREATE TABLE IF NOT EXISTS `klant` (
  `klant_email` varchar(255) NOT NULL,
  `klant_voornaam` varchar(255) NOT NULL,
  `klant_tussenvoegsel` varchar(255) NOT NULL,
  `klant_achternaam` varchar(255) NOT NULL,
  `klant_straatnaam` varchar(255) NOT NULL,
  `klant_huisnummer` int(10) NOT NULL,
  `klant_huisnummer_toevoeging` varchar(255) DEFAULT NULL,
  `klant_postcode` smallint(5) NOT NULL,
  `klant_postcode_toevoeging` varchar(255) NOT NULL,
  `klant_plaatsnaam` varchar(255) NOT NULL,
  `klant_telefoon` varchar(10) DEFAULT NULL,
  `klant_gastlid` varchar(4) NOT NULL,
  `klant_notitie` blob,
  `klant_isactief` tinyint(1) NOT NULL,
  `klant_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`klant_email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumpen data van tabel IPSEN3G6.klant: ~4 rows (ongeveer)
/*!40000 ALTER TABLE `klant` DISABLE KEYS */;
INSERT INTO `klant` (`klant_email`, `klant_voornaam`, `klant_tussenvoegsel`, `klant_achternaam`, `klant_straatnaam`, `klant_huisnummer`, `klant_huisnummer_toevoeging`, `klant_postcode`, `klant_postcode_toevoeging`, `klant_plaatsnaam`, `klant_telefoon`, `klant_gastlid`, `klant_notitie`, `klant_isactief`, `klant_date`) VALUES
	('boere.m@hsleiden.nl', 'Michiel', '', '', 'kweekhoven ', 22, NULL, 1068, 'ZT', 'Amsterdam', NULL, 'Gast', NULL, 1, '2015-08-25 16:07:48'),
	('h.visser@llz.nl', 'Henk-Jan', '', 'Visser', 'Terweeweg ', 71, NULL, 2341, 'CP', 'Oegstgeest', NULL, 'Gast', NULL, 1, '2015-08-21 20:13:02'),
	('thomas@boose.nl', 'Thomas', '', 'boose', 'Koningsspil ', 2, NULL, 2291, 'MA', 'Wateringen', NULL, 'Gast', NULL, 1, '2015-08-28 10:03:26'),
	('verlinden@metaalunie.nl', 'Paul', '', 'Verlinden', 'E. van Beinumlaan ', 68, NULL, 2343, 'MS', 'Oegstgeest', NULL, 'Gast', NULL, 1, '2015-08-21 14:27:35');
/*!40000 ALTER TABLE `klant` ENABLE KEYS */;


-- Structuur van  tabel IPSEN3G6.order wordt geschreven
CREATE TABLE IF NOT EXISTS `order` (
  `order_id` int(10) NOT NULL AUTO_INCREMENT,
  `order_klantemail` varchar(255) NOT NULL,
  `order_factuuradres` varchar(255) NOT NULL,
  `order_order_datum` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `order_isactief` tinyint(1) NOT NULL,
  PRIMARY KEY (`order_id`),
  KEY `order_klantid` (`order_klantemail`),
  CONSTRAINT `klant_mail` FOREIGN KEY (`order_klantemail`) REFERENCES `klant` (`klant_email`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumpen data van tabel IPSEN3G6.order: ~0 rows (ongeveer)
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
/*!40000 ALTER TABLE `order` ENABLE KEYS */;


-- Structuur van  tabel IPSEN3G6.orderregel wordt geschreven
CREATE TABLE IF NOT EXISTS `orderregel` (
  `orderregel_id` int(10) NOT NULL AUTO_INCREMENT,
  `orderregel_wijnid` int(10) NOT NULL,
  `orderregel_wijnnaam` varchar(255) NOT NULL,
  `orderregel_wijnjaartal` int(4) NOT NULL,
  `orderregel_aantal` int(3) NOT NULL,
  `orderregel_wijnprijs` double NOT NULL,
  `orderregel_orderid` int(10) NOT NULL,
  `orderregel_isactief` tinyint(1) NOT NULL,
  PRIMARY KEY (`orderregel_id`),
  KEY `orderregel_wijnid` (`orderregel_wijnid`),
  KEY `orderregel_orderid` (`orderregel_orderid`),
  CONSTRAINT `orderregel_ibfk_1` FOREIGN KEY (`orderregel_wijnid`) REFERENCES `wijn` (`wijn_id`),
  CONSTRAINT `orderregel_ibfk_2` FOREIGN KEY (`orderregel_orderid`) REFERENCES `order` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumpen data van tabel IPSEN3G6.orderregel: ~0 rows (ongeveer)
/*!40000 ALTER TABLE `orderregel` DISABLE KEYS */;
/*!40000 ALTER TABLE `orderregel` ENABLE KEYS */;


-- Structuur van  tabel IPSEN3G6.wijn wordt geschreven
CREATE TABLE IF NOT EXISTS `wijn` (
  `wijn_id` int(10) NOT NULL AUTO_INCREMENT,
  `wijn_serie_id` int(11) NOT NULL,
  `wijn_naam` varchar(255) NOT NULL,
  `wijn_inkoopprijs` double NOT NULL,
  `wijn_prijs` double NOT NULL,
  `wijn_type` int(2) NOT NULL,
  `wijn_merk` varchar(255) NOT NULL,
  `wijn_afkomst` int(5) DEFAULT NULL,
  `wijn_jaartal` int(5) NOT NULL,
  `wijn_isactief` tinyint(1) NOT NULL,
  PRIMARY KEY (`wijn_id`),
  KEY `wijn_category` (`wijn_afkomst`),
  CONSTRAINT `wijn_ibfk_1` FOREIGN KEY (`wijn_afkomst`) REFERENCES `wijn_afkomst` (`afkomst_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=latin1;

-- Dumpen data van tabel IPSEN3G6.wijn: ~42 rows (ongeveer)
/*!40000 ALTER TABLE `wijn` DISABLE KEYS */;
INSERT INTO `wijn` (`wijn_id`, `wijn_serie_id`, `wijn_naam`, `wijn_inkoopprijs`, `wijn_prijs`, `wijn_type`, `wijn_merk`, `wijn_afkomst`, `wijn_jaartal`, `wijn_isactief`) VALUES
	(1, 1, 'Cava Brut Reserva Stars', 0, 12.95, 0, '', 18, 2014, 1),
	(2, 2, 'Sauvignon Belles du Sud', 0, 6.95, 0, '', 19, 2014, 1),
	(3, 3, 'Muscat Sec Le Pot', 0, 7.95, 0, '', 20, 2014, 1),
	(4, 4, 'Gros Manseng', 5.99, 7.95, 0, '', 21, 2014, 1),
	(5, 5, 'Syrah Grenache Belles du Sud', 4.54, 5.95, 2, '', 19, 2014, 1),
	(6, 6, 'Merlot Belles du Sud', 4.54, 5.95, 1, '', 19, 2014, 1),
	(7, 7, 'Vigne de Castellan', 5.69, 7.95, 1, '', 22, 2012, 1),
	(8, 8, 'Castelmaure', 6.72, 8.5, 1, '', 23, 2013, 1),
	(9, 9, 'ChÃƒÂ¢teau Nuit des Dames', 5.69, 7.95, 1, '', 24, 2013, 1),
	(10, 10, 'ChÃƒÂ¢teau Vignol ', 6.84, 8.95, 1, '', 25, 2013, 1),
	(11, 11, 'Rueda', 6.66, 8.95, 0, '', 26, 2014, 1),
	(12, 12, 'Masia Tinto', 4.54, 5.95, 1, '', 27, 2013, 1),
	(13, 13, 'Finca Cuco Tinto', 7.18, 9.95, 1, '', 28, 2012, 1),
	(14, 14, '5 Fincas Reserva', 12.58, 16.95, 1, '', 29, 2011, 1),
	(15, 15, 'Vinho Verde Escolha', 6.32, 8.95, 0, '', 30, 2014, 1),
	(16, 16, 'Paulo Laureano Classico Branco', 5.45, 7.5, 0, '', 31, 2014, 1),
	(17, 17, 'Paulo Laureano Classico Tinto', 5.45, 7.5, 1, '', 31, 2014, 1),
	(18, 18, 'La Chapelle Chardonnay ', 5.98, 7.8, 0, '', 32, 2014, 1),
	(19, 19, 'Isa Blanc Chemins de Bassac', 7.99, 9.95, 0, '', 33, 2014, 1),
	(20, 20, 'Grillo', 8.11, 12.5, 0, '', 34, 2013, 1),
	(21, 21, 'Isa Rose Chemind de Bassac', 7.99, 9.95, 2, '', 35, 2014, 1),
	(22, 22, 'La Chapelle Cabernet', 5.75, 7.8, 0, '', 32, 2013, 1),
	(23, 23, 'Isa Rouge Chemins de Bassac', 7.99, 9.95, 1, '', 35, 2013, 1),
	(24, 24, 'Menetou Salon Rouge', 0, 19, 1, '', 36, 2012, 1),
	(25, 25, 'Alasia sec', 0, 7.5, 0, '', 37, 2013, 1),
	(26, 26, 'Pinot Grigio', 0, 7.6, 0, '', 38, 2014, 1),
	(27, 27, 'Verdicchio Pignocco', 0, 9.7, 0, '', 39, 2013, 1),
	(28, 28, 'Vernaccia di San Giminano', 0, 13.25, 0, '', 40, 2014, 1),
	(29, 29, 'Rosso Piceno Superiore', 0, 8.4, 0, '', 41, 2011, 1),
	(30, 30, 'Nebbiolo d Alba', 0, 12.75, 0, '', 42, 2013, 1),
	(31, 31, 'Barbera d Alba Maria Gioana', 0, 15.5, 0, '', 42, 2012, 1),
	(32, 32, 'Rosso Toscana Adone', 0, 10.75, 0, '', 43, 2011, 1),
	(33, 33, 'Chianti Vertunno', 0, 13.4, 0, '', 40, 2010, 1),
	(34, 34, 'Chardonnay', 0, 8.6, 0, '', 44, 2013, 1),
	(35, 35, 'Zinfandel', 0, 8.6, 0, '', 1, 2012, 1),
	(36, 36, 'Merlot', 0, 8.6, 0, '', 1, 2012, 1),
	(37, 37, 'De Bos Chenin sur lie', 0, 8.95, 0, '', 45, 2014, 1),
	(38, 38, 'De Bos Sauvignon', 0, 9.35, 0, '', 1, 2014, 1),
	(39, 39, 'De Bos Adama White', 0, 14.95, 0, '', 1, 2013, 1),
	(40, 40, 'De Bos Cabernet Sauvignon', 0, 9.5, 0, '', 1, 2012, 1),
	(41, 41, 'De Bos Merlot', 0, 9.5, 0, '', 1, 2013, 1),
	(42, 42, 'Bosman Adama Red ', 0, 14.95, 0, '', 1, 2013, 1);
/*!40000 ALTER TABLE `wijn` ENABLE KEYS */;


-- Structuur van  tabel IPSEN3G6.wijn_afkomst wordt geschreven
CREATE TABLE IF NOT EXISTS `wijn_afkomst` (
  `afkomst_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_id` int(11) NOT NULL,
  `afkomst_naam` varchar(255) NOT NULL,
  PRIMARY KEY (`afkomst_id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `wijn_afkomst_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `wijn_category` (`category_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=latin1;

-- Dumpen data van tabel IPSEN3G6.wijn_afkomst: ~29 rows (ongeveer)
/*!40000 ALTER TABLE `wijn_afkomst` DISABLE KEYS */;
INSERT INTO `wijn_afkomst` (`afkomst_id`, `category_id`, `afkomst_naam`) VALUES
	(1, 1, 'Other'),
	(18, 4, 'Castillo Perelada - Penedes'),
	(19, 5, 'IGP pays d\'oc'),
	(20, 5, 'CÃƒÂ´tes de Castalans'),
	(21, 5, 'CÃƒÂ´tes de Gascogne'),
	(22, 5, 'Gaillac'),
	(23, 5, 'CorbiÃƒÂ¨res'),
	(24, 5, 'CÃƒÂ´tes du Rhone'),
	(25, 5, 'Bordeaux Superieur'),
	(26, 6, 'Duquesa de Valladolid'),
	(27, 6, 'Castillo Perelada - Catalunya'),
	(28, 6, 'DO Montsant'),
	(29, 6, 'Cast. Perelada - Do EmpordÃƒÂ '),
	(30, 7, 'Quinta do Alivio'),
	(31, 7, 'Alentejo'),
	(32, 8, 'IGP pays d\'oc'),
	(33, 8, 'CÃƒÂ´tes de Thongue'),
	(34, 8, 'Di Giovanna - Sicilia'),
	(35, 8, 'CÃƒÂ´tes de Thonque'),
	(36, 8, 'Domaine Philippe Gilbert'),
	(37, 9, 'Araldica -  Piemonte'),
	(38, 9, 'Villa San Martino - Veneto '),
	(39, 9, 'Santa Barbara -  Marken'),
	(40, 9, 'Panizzi - Toscana'),
	(41, 9, 'De Angelis  -  Marken'),
	(42, 9, 'Giacosa Fratelli - Piemonte'),
	(43, 9, 'Collemattoni - Toscana'),
	(44, 10, 'Woodhaven - CaliforniÃƒÂ«'),
	(45, 11, 'Wellington  Fairtrade');
/*!40000 ALTER TABLE `wijn_afkomst` ENABLE KEYS */;


-- Structuur van  tabel IPSEN3G6.wijn_category wordt geschreven
CREATE TABLE IF NOT EXISTS `wijn_category` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_naam` varchar(255) NOT NULL,
  PRIMARY KEY (`category_id`),
  UNIQUE KEY `category_naam` (`category_naam`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

-- Dumpen data van tabel IPSEN3G6.wijn_category: ~9 rows (ongeveer)
/*!40000 ALTER TABLE `wijn_category` DISABLE KEYS */;
INSERT INTO `wijn_category` (`category_id`, `category_naam`) VALUES
	(8, 'Bio'),
	(10, 'Californie'),
	(4, 'Cava'),
	(5, 'Frankrijk'),
	(9, 'Italie'),
	(1, 'Other'),
	(7, 'Portugal'),
	(6, 'Spanje'),
	(11, 'Zuid Afrika');
/*!40000 ALTER TABLE `wijn_category` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
