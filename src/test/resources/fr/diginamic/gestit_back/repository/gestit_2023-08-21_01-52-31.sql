# ************************************************************
# Antares - SQL Client
# Version 0.7.15
# 
# https://antares-sql.app/
# https://github.com/antares-sql/antares
# 
# Host: 127.0.0.1 (MySQL Community Server - GPL 8.0.34)
# Database: gestit
# Generation time: 2023-08-21T01:53:26+02:00
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
SET NAMES utf8mb4;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table adresse
# ------------------------------------------------------------

CREATE TABLE `adresse` (
  `commune_id` int DEFAULT NULL,
  `id` int NOT NULL AUTO_INCREMENT,
  `numero` int DEFAULT NULL,
  `voie` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK85m6y7vy6lfud2ae3tnq375of` (`commune_id`),
  CONSTRAINT `FK85m6y7vy6lfud2ae3tnq375of` FOREIGN KEY (`commune_id`) REFERENCES `commune` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `adresse` WRITE;
/*!40000 ALTER TABLE `adresse` DISABLE KEYS */;

INSERT INTO `adresse` (`commune_id`, `id`, `numero`, `voie`) VALUES
	(1, 1, 12, "rue du Faubourg de Boutonnet"),
	(1, 2, 1065, "Avenue du Père Soulas"),
	(2, 3, 35, "Boulevard Gambetta"),
	(2, 4, 13, "Avenue Georges Pompidou"),
	(3, 5, 2, "Quai du Port"),
	(3, 6, 15, "Avenue du Prado"),
	(1, 7, 12, "rue du Faubourg de Boutonnet"),
	(1, 8, 1065, "Avenue du Père Soulas"),
	(2, 9, 35, "Boulevard Gambetta"),
	(2, 10, 13, "Avenue Georges Pompidou"),
	(3, 11, 2, "Quai du Port"),
	(3, 12, 15, "Avenue du Prado"),
	(1, 13, 12, "rue du Faubourg de Boutonnet"),
	(1, 14, 1065, "Avenue du Père Soulas"),
	(2, 15, 35, "Boulevard Gambetta"),
	(2, 16, 13, "Avenue Georges Pompidou"),
	(3, 17, 2, "Quai du Port"),
	(3, 18, 15, "Avenue du Prado");

/*!40000 ALTER TABLE `adresse` ENABLE KEYS */;
UNLOCK TABLES;



# Dump of table commune
# ------------------------------------------------------------

CREATE TABLE `commune` (
  `code_postal` int DEFAULT NULL,
  `id` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `commune` WRITE;
/*!40000 ALTER TABLE `commune` DISABLE KEYS */;

INSERT INTO `commune` (`code_postal`, `id`, `nom`) VALUES
	(34000, 1, "Montpellier"),
	(30000, 2, "Nîmes"),
	(13000, 3, "Marseille");

/*!40000 ALTER TABLE `commune` ENABLE KEYS */;
UNLOCK TABLES;



# Dump of table covoiturage
# ------------------------------------------------------------

CREATE TABLE `covoiturage` (
  `adresse_arrivee_id` int DEFAULT NULL,
  `adresse_depart_id` int DEFAULT NULL,
  `distance_km` int DEFAULT NULL,
  `duree_trajet` int DEFAULT NULL,
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre_places_restantes` int DEFAULT NULL,
  `organisateur_id` int DEFAULT NULL,
  `vehicule_perso_id` int DEFAULT NULL,
  `date_depart` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbua1pqlugqx4k0c6g4i0k9la1` (`adresse_arrivee_id`),
  KEY `FKm2kpb3ef2mjivibmtgltfomc5` (`adresse_depart_id`),
  KEY `FK50108k1ra3ns71tup24vu1kxq` (`organisateur_id`),
  KEY `FKp7j10vdqhi2gkapw5ruqr105r` (`vehicule_perso_id`),
  CONSTRAINT `FK50108k1ra3ns71tup24vu1kxq` FOREIGN KEY (`organisateur_id`) REFERENCES `utilisateur` (`id`),
  CONSTRAINT `FKbua1pqlugqx4k0c6g4i0k9la1` FOREIGN KEY (`adresse_arrivee_id`) REFERENCES `adresse` (`id`),
  CONSTRAINT `FKm2kpb3ef2mjivibmtgltfomc5` FOREIGN KEY (`adresse_depart_id`) REFERENCES `adresse` (`id`),
  CONSTRAINT `FKp7j10vdqhi2gkapw5ruqr105r` FOREIGN KEY (`vehicule_perso_id`) REFERENCES `vehicule_perso` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `covoiturage` WRITE;
/*!40000 ALTER TABLE `covoiturage` DISABLE KEYS */;

INSERT INTO `covoiturage` (`adresse_arrivee_id`, `adresse_depart_id`, `distance_km`, `duree_trajet`, `id`, `nombre_places_restantes`, `organisateur_id`, `vehicule_perso_id`, `date_depart`) VALUES
	(4, 6, 456, 789, 1, 2, 3, 1, NULL),
	(5, 7, 5, 45, 2, 4, 5, 1, "2023-01-11"),
	(1, 5, 5, 23, 3, 1, 6, 1, "2024-06-01"),
	(1, 5, 5, 23, 78, 1, 6, 1, "2024-06-01");

/*!40000 ALTER TABLE `covoiturage` ENABLE KEYS */;
UNLOCK TABLES;



# Dump of table covoiturages_collaborateur
# ------------------------------------------------------------

CREATE TABLE `covoiturages_collaborateur` (
  `collaborateur_id` int NOT NULL,
  `covoiturage_id` int NOT NULL,
  PRIMARY KEY (`collaborateur_id`,`covoiturage_id`),
  KEY `FKbs321v8h0uods3hlwqlv9c6nd` (`covoiturage_id`),
  CONSTRAINT `FK4vvbmhc5yy0or8b45obpqs98i` FOREIGN KEY (`collaborateur_id`) REFERENCES `utilisateur` (`id`),
  CONSTRAINT `FKbs321v8h0uods3hlwqlv9c6nd` FOREIGN KEY (`covoiturage_id`) REFERENCES `covoiturage` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `covoiturages_collaborateur` WRITE;
/*!40000 ALTER TABLE `covoiturages_collaborateur` DISABLE KEYS */;

INSERT INTO `covoiturages_collaborateur` (`collaborateur_id`, `covoiturage_id`) VALUES
	(3, 2),
	(5, 2);

/*!40000 ALTER TABLE `covoiturages_collaborateur` ENABLE KEYS */;
UNLOCK TABLES;



# Dump of table marque
# ------------------------------------------------------------

CREATE TABLE `marque` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `marque` WRITE;
/*!40000 ALTER TABLE `marque` DISABLE KEYS */;

INSERT INTO `marque` (`id`, `nom`) VALUES
	(1, "Peugeot"),
	(2, "Renault"),
	(3, "Citroën"),
	(4, "Fiat"),
	(5, "Dacia"),
	(6, "Toyota"),
	(7, "BMW"),
	(8, "Hyundai"),
	(9, "Kia"),
	(10, "Volkswagen"),
	(11, "Skoda"),
	(12, "Seat");

/*!40000 ALTER TABLE `marque` ENABLE KEYS */;
UNLOCK TABLES;



# Dump of table modele
# ------------------------------------------------------------

CREATE TABLE `modele` (
  `id` int NOT NULL AUTO_INCREMENT,
  `marque_id` int DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8y0qsdtgm60haj45cmgypuvf6` (`marque_id`),
  CONSTRAINT `FK8y0qsdtgm60haj45cmgypuvf6` FOREIGN KEY (`marque_id`) REFERENCES `marque` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `modele` WRITE;
/*!40000 ALTER TABLE `modele` DISABLE KEYS */;

INSERT INTO `modele` (`id`, `marque_id`, `nom`) VALUES
	(1, 1, "208"),
	(2, 1, "2008"),
	(3, 1, "308"),
	(4, 1, "3008"),
	(5, 1, "508"),
	(6, 1, "5008"),
	(7, 2, "Clio"),
	(8, 2, "Megane"),
	(9, 2, "Twingo"),
	(10, 3, "C3"),
	(11, 3, "C4"),
	(12, 3, "C5"),
	(13, 4, "500"),
	(14, 4, "500X"),
	(15, 4, "Panda"),
	(16, 5, "Duster"),
	(17, 5, "Spring"),
	(18, 6, "Yaris"),
	(19, 6, "Corolla"),
	(20, 6, "Rav4"),
	(21, 7, "Serie 3"),
	(22, 7, "Serie 5"),
	(23, 7, "Serie 7"),
	(24, 8, "i20"),
	(25, 8, "i30"),
	(26, 9, "Ceed"),
	(27, 9, "Niro"),
	(28, 9, "Sportage"),
	(29, 10, "Golf"),
	(30, 10, "Polo"),
	(31, 11, "Fabia"),
	(32, 11, "Octavia"),
	(33, 12, "Leon"),
	(34, 12, "Cupra");

/*!40000 ALTER TABLE `modele` ENABLE KEYS */;
UNLOCK TABLES;



# Dump of table reservation_vehicule_service
# ------------------------------------------------------------

CREATE TABLE `reservation_vehicule_service` (
  `collaborateur_id` int DEFAULT NULL,
  `id` int NOT NULL AUTO_INCREMENT,
  `vehicule_service_id` int DEFAULT NULL,
  `date_heure_depart` datetime(6) DEFAULT NULL,
  `date_heure_retour` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKj1vjldv9s25741qvsal40w8mv` (`collaborateur_id`),
  KEY `FKt5105g4iogxlagxnbjrbnty90` (`vehicule_service_id`),
  CONSTRAINT `FKj1vjldv9s25741qvsal40w8mv` FOREIGN KEY (`collaborateur_id`) REFERENCES `utilisateur` (`id`),
  CONSTRAINT `FKt5105g4iogxlagxnbjrbnty90` FOREIGN KEY (`vehicule_service_id`) REFERENCES `vehicule_service` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;





# Dump of table utilisateur
# ------------------------------------------------------------

CREATE TABLE `utilisateur` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `mot_de_passe` varchar(255) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `date_non_valide` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `utilisateur` WRITE;
/*!40000 ALTER TABLE `utilisateur` DISABLE KEYS */;

INSERT INTO `utilisateur` (`id`, `email`, `mot_de_passe`, `nom`, `date_non_valide`) VALUES
	(1, "admin1@gestit.fr", "passAdmin1", "Admin1", NULL),
	(2, "admin2@gestit.fr", "passAdmin2", "Admin2", NULL),
	(3, "collab1@gestit.fr", "passCollab1", "Collab1", NULL),
	(4, "collab2@gestit.fr", "passCollab2", "Collab2", NULL),
	(5, "orga1@gestit.fr", "passOrga1", "Orga1", NULL),
	(6, "orga2@gestit.fr", "passOrga2", "Orga2", NULL);

/*!40000 ALTER TABLE `utilisateur` ENABLE KEYS */;
UNLOCK TABLES;



# Dump of table utilisateur_roles
# ------------------------------------------------------------

CREATE TABLE `utilisateur_roles` (
  `roles` enum('ADMINISTRATEUR','COLLABORATEUR') DEFAULT NULL,
  `utilisateur_id` int NOT NULL,
  KEY `FK9lop304xtodorgho9w56lpjhn` (`utilisateur_id`),
  CONSTRAINT `FK9lop304xtodorgho9w56lpjhn` FOREIGN KEY (`utilisateur_id`) REFERENCES `utilisateur` (`id`),
  CONSTRAINT `utilisateur_roles_chk_1` CHECK ((`roles` between 0 and 1))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `utilisateur_roles` WRITE;
/*!40000 ALTER TABLE `utilisateur_roles` DISABLE KEYS */;

INSERT INTO `utilisateur_roles` (`roles`, `utilisateur_id`) VALUES
	("ADMINISTRATEUR", 1),
	("ADMINISTRATEUR", 2);

/*!40000 ALTER TABLE `utilisateur_roles` ENABLE KEYS */;
UNLOCK TABLES;



# Dump of table vehicule_perso
# ------------------------------------------------------------

CREATE TABLE `vehicule_perso` (
  `id` int NOT NULL AUTO_INCREMENT,
  `modele_id` int DEFAULT NULL,
  `nombre_de_place_disponibles` int DEFAULT NULL,
  `proprietaire_id` int DEFAULT NULL,
  `immatriculation` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9rrbpgwynygc9e6dxrluw66pj` (`modele_id`),
  KEY `FKlra1uavm94k0iy3y87rr5xcfl` (`proprietaire_id`),
  CONSTRAINT `FK9rrbpgwynygc9e6dxrluw66pj` FOREIGN KEY (`modele_id`) REFERENCES `modele` (`id`),
  CONSTRAINT `FKlra1uavm94k0iy3y87rr5xcfl` FOREIGN KEY (`proprietaire_id`) REFERENCES `utilisateur` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `vehicule_perso` WRITE;
/*!40000 ALTER TABLE `vehicule_perso` DISABLE KEYS */;

INSERT INTO `vehicule_perso` (`id`, `modele_id`, `nombre_de_place_disponibles`, `proprietaire_id`, `immatriculation`) VALUES
	(1, 1, 5, 5, "34-XXX-43"),
	(2, 8, 2, 1, "88-88-77");

/*!40000 ALTER TABLE `vehicule_perso` ENABLE KEYS */;
UNLOCK TABLES;



# Dump of table vehicule_service
# ------------------------------------------------------------

CREATE TABLE `vehicule_service` (
  `categorie` enum('BERLINES_TAILLE_L','BERLINES_TAILLE_M','BERLINES_TAILLE_S','CITADINES_POLYVALENTES','COMPACTES','MICRO_URBAINES','MINI_CITADINES','SUV_TOUT_TERRAINS_PICKUP') DEFAULT NULL,
  `emission_co2` double DEFAULT NULL,
  `id` int NOT NULL AUTO_INCREMENT,
  `modele_id` int DEFAULT NULL,
  `motorisation` enum('ELECTRIQUE','HYBRIDE','THERMIQUE') DEFAULT NULL,
  `nombre_de_place_disponibles` int DEFAULT NULL,
  `statut` enum('EN_REPARATION','EN_SERVICE','HORS_SERVICE') DEFAULT NULL,
  `immatriculation` varchar(255) DEFAULT NULL,
  `photo_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKt6ewqodbwm43eo878uuqvrakv` (`modele_id`),
  CONSTRAINT `FKt6ewqodbwm43eo878uuqvrakv` FOREIGN KEY (`modele_id`) REFERENCES `modele` (`id`),
  CONSTRAINT `vehicule_service_chk_1` CHECK ((`categorie` between 0 and 7)),
  CONSTRAINT `vehicule_service_chk_2` CHECK ((`motorisation` between 0 and 2)),
  CONSTRAINT `vehicule_service_chk_3` CHECK ((`statut` between 0 and 2))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;





/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

# Dump completed on 2023-08-21T01:53:27+02:00
