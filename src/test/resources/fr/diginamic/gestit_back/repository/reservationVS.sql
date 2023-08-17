

insert into utilisateur (email, mot_de_passe, nom) values('admin1@gestit.fr', 'passAdmin1', 'Admin1');
insert into utilisateur (email, mot_de_passe, nom) values('collab1@gestit.fr', 'passCollab1', 'Collab1');
insert into utilisateur_roles (roles, utilisateur_id) values('ADMINISTRATEUR',1);
insert into utilisateur_roles (roles, utilisateur_id) values('COLLABORATEUR',2);
insert into marque (nom) values('Peugeot');
insert into modele (nom, marque_id) values('208',1);
insert into vehicule_service(modele_id, nombre_de_place_disponibles, immatriculation, categorie, motorisation, statut, photo_url) values (1, 5, '11-ZZZ-11', 'COMPACTES', 'THERMIQUE', 'EN_SERVICE','https://m.media-amazon.com/images/I/71E32iPE92L._AC_SL1500_.jpg');
insert into reservation_vehicule_service (collaborateur_id, vehicule_service_id, date_heure_depart, date_heure_retour) values (1, 1, '2023-07-30 14:00:00', '2023-08-01 16:00:00');
insert into reservation_vehicule_service (collaborateur_id, vehicule_service_id, date_heure_depart, date_heure_retour) values (2, 1, '2023-07-31 18:00:00', '2023-08-04 13:30:00');