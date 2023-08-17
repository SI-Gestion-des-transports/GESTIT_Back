CREATE SCHEMA IF NOT EXISTS test;

CREATE TABLE IF NOT EXISTS utilisateur (
    id INT AUTO_INCREMENT PRIMARY KEY,
    mot_de_passe VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    nom VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS utilisateur_roles (
     id INT AUTO_INCREMENT PRIMARY KEY,
    roles VARCHAR(255) NOT NULL,
    utilisateur_id INT,
    FOREIGN KEY (utilisateur_id) REFERENCES utilisateur(id)
    );

CREATE TABLE IF NOT EXISTS commune (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    code_postal INT NOT NULL
    );

CREATE TABLE IF NOT EXISTS adresse (
    id INT AUTO_INCREMENT PRIMARY KEY,
    numero INT NOT NULL,
    voie VARCHAR(255) NOT NULL,
    commune_id INT,
    FOREIGN KEY (commune_id) REFERENCES commune(id)
    );

CREATE TABLE IF NOT EXISTS marque (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS modele (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    marque_id INT,
    FOREIGN KEY (marque_id) REFERENCES marque(id)
    );

CREATE TABLE IF NOT EXISTS vehicule_perso (
    id INT AUTO_INCREMENT PRIMARY KEY,
    modele_id INT,
    nombre_de_place_disponibles INT NOT NULL,
    proprietaire_id INT,
    immatriculation VARCHAR(50) UNIQUE NOT NULL,
    FOREIGN KEY (modele_id) REFERENCES modele(id),
    FOREIGN KEY (proprietaire_id) REFERENCES utilisateur(id)
    );

CREATE TABLE IF NOT EXISTS vehicule_service (
    id INT AUTO_INCREMENT PRIMARY KEY,
    modele_id INT,
    nombre_de_place_disponibles INT,
    immatriculation VARCHAR(50) UNIQUE NOT NULL,
    categorie VARCHAR(255) NOT NULL,
    emission_co2 INT,
    motorisation VARCHAR(255) NOT NULL,
    statut VARCHAR(50) NOT NULL,
    photo_url TEXT
    );

CREATE TABLE IF NOT EXISTS reservation_vehicule_service (
    id INT AUTO_INCREMENT PRIMARY KEY,
    collaborateur_id INT,
    vehicule_service_id INT,
    date_heure_depart TIMESTAMP NOT NULL,
    date_heure_retour TIMESTAMP NOT NULL,
    FOREIGN KEY (collaborateur_id) REFERENCES utilisateur(id),
    FOREIGN KEY (vehicule_service_id) REFERENCES vehicule_service(id)
    );



