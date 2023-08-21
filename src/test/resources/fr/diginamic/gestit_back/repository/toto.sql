CREATE DATABASE `TEST_GESTIT`
/*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */
/*!80016 DEFAULT ENCRYPTION='N' */
;

CREATE TABLE `adresse` (
	`commune_id` int DEFAULT NULL,
	`id` int NOT NULL AUTO_INCREMENT,
	`numero` int DEFAULT NULL,
	`voie` varchar(255) DEFAULT NULL,
	PRIMARY KEY (`id`),
	KEY `FK85m6y7vy6lfud2ae3tnq375of` (`commune_id`),
	CONSTRAINT `FK85m6y7vy6lfud2ae3tnq375of` FOREIGN KEY (`commune_id`) REFERENCES `commune` (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 19 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `commune` (
	`code_postal` int DEFAULT NULL,
	`id` int NOT NULL AUTO_INCREMENT,
	`nom` varchar(255) DEFAULT NULL,
	PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 4 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

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
) ENGINE = InnoDB AUTO_INCREMENT = 79 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `covoiturages_collaborateur` (
	`collaborateur_id` int NOT NULL,
	`covoiturage_id` int NOT NULL,
	PRIMARY KEY (`collaborateur_id`, `covoiturage_id`),
	KEY `FKbs321v8h0uods3hlwqlv9c6nd` (`covoiturage_id`),
	CONSTRAINT `FK4vvbmhc5yy0or8b45obpqs98i` FOREIGN KEY (`collaborateur_id`) REFERENCES `utilisateur` (`id`),
	CONSTRAINT `FKbs321v8h0uods3hlwqlv9c6nd` FOREIGN KEY (`covoiturage_id`) REFERENCES `covoiturage` (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `marque` (
	`id` int NOT NULL AUTO_INCREMENT,
	`nom` varchar(255) DEFAULT NULL,
	PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 13 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `modele` (
	`id` int NOT NULL AUTO_INCREMENT,
	`marque_id` int DEFAULT NULL,
	`nom` varchar(255) DEFAULT NULL,
	PRIMARY KEY (`id`),
	KEY `FK8y0qsdtgm60haj45cmgypuvf6` (`marque_id`),
	CONSTRAINT `FK8y0qsdtgm60haj45cmgypuvf6` FOREIGN KEY (`marque_id`) REFERENCES `marque` (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 35 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

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
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `utilisateur` (
	`id` int NOT NULL AUTO_INCREMENT,
	`email` varchar(255) DEFAULT NULL,
	`mot_de_passe` varchar(255) DEFAULT NULL,
	`nom` varchar(255) DEFAULT NULL,
	`date_non_valide` date DEFAULT NULL,
	PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 7 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `utilisateur_roles` (
	`roles` enum('ADMINISTRATEUR', 'COLLABORATEUR') DEFAULT NULL,
	`utilisateur_id` int NOT NULL,
	KEY `FK9lop304xtodorgho9w56lpjhn` (`utilisateur_id`),
	CONSTRAINT `FK9lop304xtodorgho9w56lpjhn` FOREIGN KEY (`utilisateur_id`) REFERENCES `utilisateur` (`id`),
	CONSTRAINT `utilisateur_roles_chk_1` CHECK (
		(
			`roles` between 0
			and 1
		)
	)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

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
) ENGINE = InnoDB AUTO_INCREMENT = 3 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `vehicule_service` (
	`categorie` enum(
		'BERLINES_TAILLE_L',
		'BERLINES_TAILLE_M',
		'BERLINES_TAILLE_S',
		'CITADINES_POLYVALENTES',
		'COMPACTES',
		'MICRO_URBAINES',
		'MINI_CITADINES',
		'SUV_TOUT_TERRAINS_PICKUP'
	) DEFAULT NULL,
	`emission_co2` double DEFAULT NULL,
	`id` int NOT NULL AUTO_INCREMENT,
	`modele_id` int DEFAULT NULL,
	`motorisation` enum('ELECTRIQUE', 'HYBRIDE', 'THERMIQUE') DEFAULT NULL,
	`nombre_de_place_disponibles` int DEFAULT NULL,
	`statut` enum('EN_REPARATION', 'EN_SERVICE', 'HORS_SERVICE') DEFAULT NULL,
	`immatriculation` varchar(255) DEFAULT NULL,
	`photo_url` varchar(255) DEFAULT NULL,
	PRIMARY KEY (`id`),
	KEY `FKt6ewqodbwm43eo878uuqvrakv` (`modele_id`),
	CONSTRAINT `FKt6ewqodbwm43eo878uuqvrakv` FOREIGN KEY (`modele_id`) REFERENCES `modele` (`id`),
	CONSTRAINT `vehicule_service_chk_1` CHECK (
		(
			`categorie` between 0
			and 7
		)
	),
	CONSTRAINT `vehicule_service_chk_2` CHECK (
		(
			`motorisation` between 0
			and 2
		)
	),
	CONSTRAINT `vehicule_service_chk_3` CHECK (
		(
			`statut` between 0
			and 2
		)
	)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;