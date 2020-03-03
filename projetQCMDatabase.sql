-- --------------------------------------------------------
-- Hôte :                        127.0.0.1
-- Version du serveur:           10.1.37-MariaDB - mariadb.org binary distribution
-- SE du serveur:                Win32
-- HeidiSQL Version:             10.1.0.5464
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Listage de la structure de la base pour qcm
CREATE DATABASE IF NOT EXISTS `qcm` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;
USE `qcm`;

-- Listage de la structure de la table qcm. examen
CREATE TABLE IF NOT EXISTS `examen` (
  `id` int(11) NOT NULL,
  `id_matiere` int(11) NOT NULL,
  `titre` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `temps` time NOT NULL,
  `point_max` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_examen_matiere` (`id_matiere`),
  CONSTRAINT `FK_examen_matiere` FOREIGN KEY (`id_matiere`) REFERENCES `matiere` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Listage des données de la table qcm.examen : ~1 rows (environ)
/*!40000 ALTER TABLE `examen` DISABLE KEYS */;
INSERT INTO `examen` (`id`, `id_matiere`, `titre`, `temps`, `point_max`) VALUES
	(0, 0, 'Mathematique 1', '02:00:00', 50);
/*!40000 ALTER TABLE `examen` ENABLE KEYS */;

-- Listage de la structure de la table qcm. examenaquestion
CREATE TABLE IF NOT EXISTS `examenaquestion` (
  `id_examen` int(11) NOT NULL,
  `id_question` int(11) NOT NULL,
  `point` int(11) DEFAULT NULL,
  KEY `id_examen_id_question` (`id_examen`,`id_question`),
  KEY `FK_examenaquestion_question` (`id_question`),
  CONSTRAINT `FK_examenaquestion_examen` FOREIGN KEY (`id_examen`) REFERENCES `examen` (`id`),
  CONSTRAINT `FK_examenaquestion_question` FOREIGN KEY (`id_question`) REFERENCES `question` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Listage des données de la table qcm.examenaquestion : ~2 rows (environ)
/*!40000 ALTER TABLE `examenaquestion` DISABLE KEYS */;
INSERT INTO `examenaquestion` (`id_examen`, `id_question`, `point`) VALUES
	(0, 1, NULL),
	(0, 0, NULL);
/*!40000 ALTER TABLE `examenaquestion` ENABLE KEYS */;

-- Listage de la structure de la table qcm. matiere
CREATE TABLE IF NOT EXISTS `matiere` (
  `id` int(11) NOT NULL,
  `nom` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Listage des données de la table qcm.matiere : ~3 rows (environ)
/*!40000 ALTER TABLE `matiere` DISABLE KEYS */;
INSERT INTO `matiere` (`id`, `nom`) VALUES
	(0, 'Math'),
	(1, 'Science'),
	(2, 'Francais');
/*!40000 ALTER TABLE `matiere` ENABLE KEYS */;

-- Listage de la structure de la table qcm. question
CREATE TABLE IF NOT EXISTS `question` (
  `id` int(11) NOT NULL,
  `question` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `reponse` bit(1) NOT NULL,
  `point_max` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Listage des données de la table qcm.question : ~2 rows (environ)
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` (`id`, `question`, `reponse`, `point_max`) VALUES
	(0, 'Peut-on survivre dans eau ?', b'0', 2),
	(1, 'Bob ferait un meilleur président ?', b'1', 48);
/*!40000 ALTER TABLE `question` ENABLE KEYS */;

-- Listage de la structure de la table qcm. type
CREATE TABLE IF NOT EXISTS `type` (
  `id` int(11) NOT NULL,
  `nom` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Listage des données de la table qcm.type : ~2 rows (environ)
/*!40000 ALTER TABLE `type` DISABLE KEYS */;
INSERT INTO `type` (`id`, `nom`) VALUES
	(0, 'Professeur'),
	(1, 'Eleve');
/*!40000 ALTER TABLE `type` ENABLE KEYS */;

-- Listage de la structure de la table qcm. utilisateur
CREATE TABLE IF NOT EXISTS `utilisateur` (
  `id` int(11) NOT NULL,
  `username` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `type_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_utilisateur_type` (`type_id`),
  CONSTRAINT `FK_utilisateur_type` FOREIGN KEY (`type_id`) REFERENCES `type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Listage des données de la table qcm.utilisateur : ~2 rows (environ)
/*!40000 ALTER TABLE `utilisateur` DISABLE KEYS */;
INSERT INTO `utilisateur` (`id`, `username`, `password`, `type_id`) VALUES
	(0, 'agentfeux', '1234', 0),
	(1, 'SleepyWrangller', '1234', 1);
/*!40000 ALTER TABLE `utilisateur` ENABLE KEYS */;

-- Listage de la structure de la table qcm. utilisateuraexamen
CREATE TABLE IF NOT EXISTS `utilisateuraexamen` (
  `id_utilisateur` int(11) NOT NULL,
  `id_examen` int(11) NOT NULL,
  `note` int(11) DEFAULT NULL,
  KEY `id_utilisateur_id_examen` (`id_utilisateur`,`id_examen`),
  KEY `FK_utilisateuraexam_examen` (`id_examen`),
  CONSTRAINT `FK_utilisateuraexam_examen` FOREIGN KEY (`id_examen`) REFERENCES `examen` (`id`),
  CONSTRAINT `FK_utilisateuraexam_utilisateur` FOREIGN KEY (`id_utilisateur`) REFERENCES `utilisateur` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Listage des données de la table qcm.utilisateuraexamen : ~1 rows (environ)
/*!40000 ALTER TABLE `utilisateuraexamen` DISABLE KEYS */;
INSERT INTO `utilisateuraexamen` (`id_utilisateur`, `id_examen`, `note`) VALUES
	(1, 0, 0);
/*!40000 ALTER TABLE `utilisateuraexamen` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
