insert into marque (nom) values('Peugeot');
insert into marque (nom) values('Renault');
insert into marque (nom) values('Citroën');
insert into marque (nom) values('Fiat');
insert into marque (nom) values('Dacia');
insert into marque (nom) values('Toyota');
insert into marque (nom) values('BMW');
insert into marque (nom) values('Hyundai');
insert into marque (nom) values('Kia');
insert into marque (nom) values('Volkswagen');
insert into marque (nom) values('Skoda');
insert into marque (nom) values('Seat');
insert into modele (nom, marque_id) values('208',1);
insert into modele (nom, marque_id) values('2008',1);
insert into modele (nom, marque_id) values('308',1);
insert into modele (nom, marque_id) values('3008',1);
insert into modele (nom, marque_id) values('508',1);
insert into modele (nom, marque_id) values('5008',1);
insert into modele (nom, marque_id) values('Clio',2);
insert into modele (nom, marque_id) values('Megane',2);
insert into modele (nom, marque_id) values('Twingo',2);
insert into modele (nom, marque_id) values('C3',3);
insert into modele (nom, marque_id) values('C4',3);
insert into modele (nom, marque_id) values('C5',3);
insert into modele (nom, marque_id) values('500',4);
insert into modele (nom, marque_id) values('500X',4);
insert into modele (nom, marque_id) values('Panda',4);
insert into modele (nom, marque_id) values('Duster',5);
insert into modele (nom, marque_id) values('Spring',5);
insert into modele (nom, marque_id) values('Yaris',6);
insert into modele (nom, marque_id) values('Corolla',6);
insert into modele (nom, marque_id) values('Rav4',6);
insert into modele (nom, marque_id) values('Serie 3',7);
insert into modele (nom, marque_id) values('Serie 5',7);
insert into modele (nom, marque_id) values('Serie 7',7);
insert into modele (nom, marque_id) values('i20',8);
insert into modele (nom, marque_id) values('i30',8);
insert into modele (nom, marque_id) values('Ceed',9);
insert into modele (nom, marque_id) values('Niro',9);
insert into modele (nom, marque_id) values('Sportage',9);
insert into modele (nom, marque_id) values('Golf',10);
insert into modele (nom, marque_id) values('Polo',10);
insert into modele (nom, marque_id) values('Fabia',11);
insert into modele (nom, marque_id) values('Octavia',11);
insert into modele (nom, marque_id) values('Leon',12);
insert into modele (nom, marque_id) values('Cupra',12);
insert into commune (nom, code_postal) values('Montpellier',34000);
insert into commune (nom, code_postal) values('Nîmes',30000);
insert into commune (nom, code_postal) values('Marseille',13000);
insert into adresse (numero, voie, commune_id) values(12, 'rue du Faubourg de Boutonnet', 1);
insert into adresse (numero, voie, commune_id) values(1065, 'Avenue du Père Soulas', 1);
insert into adresse (numero, voie, commune_id) values(35, 'Boulevard Gambetta', 2);
insert into adresse (numero, voie, commune_id) values(13, 'Avenue Georges Pompidou', 2);
insert into adresse (numero, voie, commune_id) values(2, 'Quai du Port', 3);
insert into adresse (numero, voie, commune_id) values(15, 'Avenue du Prado', 3);
insert into utilisateur (email, mot_de_passe, nom) values('collaborateur1@gestit.fr', '{bcrypt}$2a$10$SEUQuY5MU/a0DhQQG8LMm.j0tsVGjExoIz1ecfysVkG9n88HJedhq', 'Collab1');
insert into utilisateur (email, mot_de_passe, nom) values('collaborateur2@gestit.fr', '{bcrypt}$2a$10$IDyjp2WHNrHrCGGXGMLoZu3sZLsSxAjeMiCChDKkC00HyQTC.qYBq', 'Collab2');
insert into utilisateur (email, mot_de_passe, nom) values('collaborateur3@gestit.fr', '{bcrypt}$2a$10$wpq.mTIiKa0Kn3WKGDdCjuamKUwxZ/Du.uGMkVBs8pwApWisnM5Wy', 'Collab3');
insert into utilisateur (email, mot_de_passe, nom) values('collaborateur4@gestit.fr', '{bcrypt}$2a$10$J8sQ8xYesEafg2//xWZlnOL5mb93GrZV0Pef7W47YjZOvqWAEiQ6S', 'Collab4');
insert into utilisateur (email, mot_de_passe, nom) values('collaborateur5@gestit.fr', '{bcrypt}$2a$10$L/EQZ3wYmahSmkz1K8sf8ec7Fl//1eHEyKmhXNgefxAbTwMIZKxom', 'Collab5');
insert into utilisateur (email, mot_de_passe, nom) values('collaborateur6@gestit.fr', '{bcrypt}$2a$10$7SGXBgy8uqI6achM.YSOMuGJeBDXs0fIPmLUq5aifFvuZzA7k8F.q', 'Collab6');
insert into utilisateur (email, mot_de_passe, nom) values('collaborateur7@gestit.fr', '{bcrypt}$2a$10$zXQkEgXm6CaRK4aRBL1xOOdK3jLsTp2dtoC.m04lSZ8a6u4b0ihJi', 'Collab7');
insert into utilisateur (email, mot_de_passe, nom) values('collaborateur8@gestit.fr', '{bcrypt}$2a$10$DjY9HgawXQv3pxXDzB2uTOQRerjbPiOMNjnyb0bywYDBEdmvPtvo6', 'Collab8');
insert into utilisateur (email, mot_de_passe, nom) values('organisateur1@gestit.fr', '{bcrypt}$2a$10$AmJTqxsvkrqwifEgaCbb7eGrkr26RjoESQxRkCivMyyIWyxcob9XS', 'Orga1');
insert into utilisateur (email, mot_de_passe, nom) values('organisateur2@gestit.fr', '{bcrypt}$2a$10$zyZFOVLXdoVnsMApvrzs/.snma322FQKQKwIR7GoaVGfMMRrT3F4i', 'Orga2');
insert into utilisateur (email, mot_de_passe, nom) values('organisateur3@gestit.fr', '{bcrypt}$2a$10$QkzVFhm9MQz0AUxRuJhQFu58bPH0S3ZOocfVL08xYLUcekIa/pSqm', 'Orga3');
insert into utilisateur (email, mot_de_passe, nom) values('organisateur4@gestit.fr', '{bcrypt}$2a$10$85cAUGUksAcPKxqP1vz6iOkahoAUPJtN/4uygoaiQtTAPYWNVs/T6', 'Orga4');
insert into utilisateur (email, mot_de_passe, nom) values('administrateur1@gestit.fr', '{bcrypt}$2a$10$TqavM2HmzsQtdN7iVC1JXe6VP0IG2jKf0Zc15GLzUh.NGATFW5dxi', 'Admin1');
insert into utilisateur (email, mot_de_passe, nom) values('administrateur2@gestit.fr', '{bcrypt}$2a$10$4I52vkZNijrAw4aoYM5by.wT.PTBvQbHSQZKxeW8xGUClJwvWHhDS', 'Admin2');
insert into utilisateur_roles (roles, utilisateur_id) values('COLLABORATEUR',1);
insert into utilisateur_roles (roles, utilisateur_id) values('COLLABORATEUR',2);
insert into utilisateur_roles (roles, utilisateur_id) values('COLLABORATEUR',3);
insert into utilisateur_roles (roles, utilisateur_id) values('COLLABORATEUR',4);
insert into utilisateur_roles (roles, utilisateur_id) values('COLLABORATEUR',5);
insert into utilisateur_roles (roles, utilisateur_id) values('COLLABORATEUR',6);
insert into utilisateur_roles (roles, utilisateur_id) values('COLLABORATEUR',7);
insert into utilisateur_roles (roles, utilisateur_id) values('COLLABORATEUR',8);
insert into utilisateur_roles (roles, utilisateur_id) values('COLLABORATEUR',9);
insert into utilisateur_roles (roles, utilisateur_id) values('COLLABORATEUR',10);
insert into utilisateur_roles (roles, utilisateur_id) values('COLLABORATEUR',11);
insert into utilisateur_roles (roles, utilisateur_id) values('COLLABORATEUR',12);
insert into utilisateur_roles (roles, utilisateur_id) values('ADMINISTRATEUR',13);
insert into utilisateur_roles (roles, utilisateur_id) values('ADMINISTRATEUR',14);
insert into vehicule_perso(modele_id, nombre_de_place_disponibles, proprietaire_id, immatriculation) values (1, 5, 5, '34-XXX-43');
insert into vehicule_perso(modele_id, nombre_de_place_disponibles, proprietaire_id, immatriculation) values (2, 5, 6, '43-YYY-34');
insert into vehicule_service(modele_id, nombre_de_place_disponibles, immatriculation, categorie, motorisation, statut, photo_url) values (1, 5, '11-ZZZ-11', 'COMPACTES', 'THERMIQUE', 'EN_SERVICE','https://m.media-amazon.com/images/I/71E32iPE92L._AC_SL1500_.jpg');
insert into vehicule_service(modele_id, nombre_de_place_disponibles, immatriculation, categorie, motorisation, statut, photo_url) values (3, 5, '22-AAA-22', 'COMPACTES', 'THERMIQUE', 'EN_SERVICE','https://m.media-amazon.com/images/I/71E32iPE92L._AC_SL1500_.jpg');
insert into reservation_vehicule_service (collaborateur_id, vehicule_service_id, date_heure_depart, date_heure_retour) values (1, 1, '2023-07-30 14:00:00', '2023-08-01 16:00:00');
insert into reservation_vehicule_service (collaborateur_id, vehicule_service_id, date_heure_depart, date_heure_retour) values (1, 1, '2023-07-31 18:00:00', '2023-08-04 13:30:00');
insert into reservation_vehicule_service (collaborateur_id, vehicule_service_id, date_heure_depart, date_heure_retour) values (12, 2, '2023-06-30 19:00:00', '2023-08-04 13:30:00');
insert into covoiturage (distance_km, duree_trajet, nombre_places_restantes, date_depart, adresse_arrivee_id, adresse_depart_id, organisateur_id, vehicule_perso_id) values (150, 3, 1, '2023-08-01', 1, 3, 5, 1);
insert into covoiturage (distance_km, duree_trajet, nombre_places_restantes, date_depart, adresse_arrivee_id, adresse_depart_id, organisateur_id, vehicule_perso_id) values (110, 3, 4, '2019-08-01', 1, 3, 5, 1);
insert into covoiturages_collaborateur (collaborateur_id, covoiturage_id) values (4,1);
insert into covoiturages_collaborateur (collaborateur_id, covoiturage_id) values (5,1);
insert into covoiturages_collaborateur (collaborateur_id, covoiturage_id) values (8,1);
