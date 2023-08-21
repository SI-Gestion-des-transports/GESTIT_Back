CREATE SCHEMA IF NOT EXISTS test;

CREATE TABLE adresse (
    commune_id int DEFAULT NULL,
    id int NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    numero int DEFAULT NULL,
    voie varchar(255) DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE commune (
    code_postal int DEFAULT NULL,
    id int NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    nom varchar(255) DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE covoiturage (
    adresse_arrivee_id int DEFAULT NULL,
    adresse_depart_id int DEFAULT NULL,
    distance_km int DEFAULT NULL,
    duree_trajet int DEFAULT NULL,
    id int NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    nombre_places_restantes int DEFAULT NULL,
    organisateur_id int DEFAULT NULL,
    vehicule_perso_id int DEFAULT NULL,
    date_depart date DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE covoiturages_collaborateur (
    collaborateur_id int NOT NULL,
    covoiturage_id int NOT NULL,
    PRIMARY KEY (collaborateur_id, covoiturage_id)
);

CREATE TABLE marque (
    id int NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    nom varchar(255) DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE modele (
    id int NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    marque_id int DEFAULT NULL,
    nom varchar(255) DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE reservation_vehicule_service (
    collaborateur_id int DEFAULT NULL,
    id int NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    vehicule_service_id int DEFAULT NULL,
    date_heure_depart timestamp(6) DEFAULT NULL,
    date_heure_retour timestamp(6) DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE utilisateur (
    id int NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    email varchar(255) DEFAULT NULL,
    mot_de_passe varchar(255) DEFAULT NULL,
    nom varchar(255) DEFAULT NULL,
    date_non_valide date DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE utilisateur_roles (
    roles ENUM('ADMINISTRATEUR', 'COLLABORATEUR') DEFAULT NULL,
    utilisateur_id int NOT NULL
);

CREATE TABLE vehicule_perso (
    id int NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    modele_id int DEFAULT NULL,
    nombre_de_place_disponibles int DEFAULT NULL,
    proprietaire_id int DEFAULT NULL,
    immatriculation varchar(255) DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE vehicule_service (
    categorie ENUM(
        'BERLINES_TAILLE_L',
        'BERLINES_TAILLE_M',
        'BERLINES_TAILLE_S',
        'CITADINES_POLYVALENTES',
        'COMPACTES',
        'MICRO_URBAINES',
        'MINI_CITADINES',
        'SUV_TOUT_TERRAINS_PICKUP'
    ) DEFAULT NULL,
    emission_co2 double DEFAULT NULL,
    id int NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    modele_id int DEFAULT NULL,
    motorisation ENUM('ELECTRIQUE', 'HYBRIDE', 'THERMIQUE') DEFAULT NULL,
    nombre_de_place_disponibles int DEFAULT NULL,
    statut ENUM('EN_REPARATION', 'EN_SERVICE', 'HORS_SERVICE') DEFAULT NULL,
    immatriculation varchar(255) DEFAULT NULL,
    photo_url varchar(255) DEFAULT NULL,
    PRIMARY KEY (id)
);