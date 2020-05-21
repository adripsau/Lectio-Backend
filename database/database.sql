-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         10.4.12-MariaDB - mariadb.org binary distribution
-- SO del servidor:              Win64
-- HeidiSQL Versión:             11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Volcando estructura de base de datos para lectio
DROP DATABASE IF EXISTS `lectio`;
CREATE DATABASE IF NOT EXISTS `lectio` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `lectio`;

-- Volcando estructura para tabla lectio.booklists
DROP TABLE IF EXISTS `booklists`;
CREATE TABLE IF NOT EXISTS `booklists` (
  `book_id` int(11) unsigned NOT NULL DEFAULT 0,
  `list_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`book_id`,`list_id`),
  KEY `list_id_fk` (`list_id`),
  CONSTRAINT `book_list_id_fk` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`),
  CONSTRAINT `list_id_fk` FOREIGN KEY (`list_id`) REFERENCES `userlists` (`list_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla lectio.booklists: ~7 rows (aproximadamente)
/*!40000 ALTER TABLE `booklists` DISABLE KEYS */;
REPLACE INTO `booklists` (`book_id`, `list_id`) VALUES
	(1, 1),
	(2, 9),
	(3, 1),
	(5, 1),
	(5, 2),
	(8, 2),
	(8, 9);
/*!40000 ALTER TABLE `booklists` ENABLE KEYS */;

-- Volcando estructura para tabla lectio.books
DROP TABLE IF EXISTS `books`;
CREATE TABLE IF NOT EXISTS `books` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `author` varchar(255) NOT NULL,
  `publisher` varchar(255) NOT NULL,
  `pages` varchar(255) NOT NULL,
  `isbn` varchar(20) NOT NULL,
  `genres` varchar(255) NOT NULL,
  `synopsis` varchar(20000) DEFAULT 'There are no synopsis.',
  PRIMARY KEY (`id`),
  UNIQUE KEY `isbn` (`isbn`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla lectio.books: ~8 rows (aproximadamente)
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
REPLACE INTO `books` (`id`, `title`, `author`, `publisher`, `pages`, `isbn`, `genres`, `synopsis`) VALUES
	(1, 'Libro', 'Autor', 'Editorial', '6969', '9788448005009', 'Science fiction,Fantasy', 'Sinopsis del libro'),
	(2, 'Pilotes', 'Misco Jones', 'Editorial', '6969', '97884480067009', 'Science fiction,Adventure', NULL),
	(3, 'Juega', 'Misco Jones', 'Editorial', '6969', '97884420067009', 'Science fiction,Adventure', NULL),
	(4, 'Una breve historia de casi todo', 'Devil Bryson', 'Editorial', '639', '97884421267009', 'Science fiction,Adventure', NULL),
	(5, 'La teroia del todo', 'Stephen Hawking', 'Editorial', '639', '89884421267009', 'Science fiction,Adventure', NULL),
	(6, 'Padre rico, Padre pobre', 'Kiyosaki', 'Editorial', '639', '89894421267009', 'Science fiction,Adventure', NULL),
	(7, 'Rimas y leyendas', 'Gustavo Adolfo Becquer', 'Austral', '345', '89894421266509', 'Science fiction,Adventure', NULL),
	(8, 'Delirios de grandeza', 'George Orwell', 'La Santillana', '362', '9781234567897', 'Action,Adventure,Science fiction,Historical fiction', 'Es una novela profunda que representa, como un ser cae en los deseos del poder.');
/*!40000 ALTER TABLE `books` ENABLE KEYS */;

-- Volcando estructura para tabla lectio.reviews
DROP TABLE IF EXISTS `reviews`;
CREATE TABLE IF NOT EXISTS `reviews` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `book_id` int(11) unsigned NOT NULL,
  `user_id` int(11) NOT NULL,
  `comment` varchar(255) NOT NULL,
  `punctuation` int(11) NOT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `book_id_user_id` (`book_id`,`user_id`),
  KEY `user_id_reviews_fk` (`user_id`),
  KEY `book_id_reviewes_fk` (`book_id`),
  CONSTRAINT `book_id_reviews_fk` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_id_reviews_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla lectio.reviews: ~4 rows (aproximadamente)
/*!40000 ALTER TABLE `reviews` DISABLE KEYS */;
REPLACE INTO `reviews` (`id`, `book_id`, `user_id`, `comment`, `punctuation`, `user_name`) VALUES
	(3, 1, 33, 'Guay', 4, 'Jose Gonzalez'),
	(4, 8, 32, 'Meh', 2, 'edu rodriguez'),
	(5, 8, 21, 'Me ha gustado', 4, 'alvaro suarez'),
	(6, 8, 31, 'Obra maestra', 5, 'evan sanz');
/*!40000 ALTER TABLE `reviews` ENABLE KEYS */;

-- Volcando estructura para tabla lectio.userlists
DROP TABLE IF EXISTS `userlists`;
CREATE TABLE IF NOT EXISTS `userlists` (
  `list_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT 0,
  `list_name` varchar(255) NOT NULL DEFAULT '',
  `list_description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`list_id`),
  UNIQUE KEY `user_id_list_name` (`user_id`,`list_name`),
  KEY `user_list_id_fk` (`user_id`),
  CONSTRAINT `user_list_id_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla lectio.userlists: ~6 rows (aproximadamente)
/*!40000 ALTER TABLE `userlists` DISABLE KEYS */;
REPLACE INTO `userlists` (`list_id`, `user_id`, `list_name`, `list_description`) VALUES
	(1, 32, 'Esperando', 'Esperando nuevos caps'),
	(2, 32, 'Pending', NULL),
	(5, 32, 'Bacano', 'Unos libros bien chingones'),
	(7, 33, 'Pending', ''),
	(8, 33, 'Finished', ''),
	(9, 32, 'Anime', 'Me gusta ser otaco');
/*!40000 ALTER TABLE `userlists` ENABLE KEYS */;

-- Volcando estructura para tabla lectio.users
DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `additional` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `role` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `fullname` (`first_name`,`last_name`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COMMENT='Nota: el nombre de las columnas no puede contener mayúsculas, si no, hibernate peta.';

-- Volcando datos para la tabla lectio.users: ~4 rows (aproximadamente)
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
REPLACE INTO `users` (`user_id`, `additional`, `email`, `first_name`, `last_name`, `photo`, `role`, `password`) VALUES
	(21, NULL, 'a.s@email.com', 'alvaro', 'suarez', NULL, 'Student', '$2a$10$jvPMqIomjLhUGz1Y.q8XTOcVdt64qOelHUZw9SAPuPj6ML7pBwIHq'),
	(31, NULL, 'es@email.com', 'evan', 'sanz', NULL, 'Student', '$2a$10$M2yfiweQ9hZ8Gx6lU0Z/9OpcT.9jN89dJ9FmyyDappS65oBeMkV3S'),
	(32, NULL, 'edu@email.com', 'edu', 'rodriguez', NULL, 'Student', '$2a$10$4N9zW1on9MBJOhNMFi5/t.r1tynnWux5ls85w/CpglLmlg2M7pqUq'),
	(33, NULL, 'jose@email.com', 'Jose', 'Gonzalez', NULL, 'Librarian', '$2a$10$.V7ImD8VXqmlnfb36ikpy.izZgrRC4pZwcaGSv1Ldig8FcV3m/uum');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
