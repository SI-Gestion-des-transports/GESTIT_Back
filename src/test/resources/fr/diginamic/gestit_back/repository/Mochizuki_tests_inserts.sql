INSERT INTO
    adresse (commune_id, id, numero, voie)
VALUES
    (
        1,
        1,
        12,
        'rue du faubourg de boutonnet'
    ),
    (1, 2, 1065, 'avenue du père soulas'),
    (2, 3, 35, 'boulevard gambetta'),
    (2, 4, 13, 'avenue georges pompidou'),
    (3, 5, 2, 'quai du port'),
    (3, 6, 15, 'avenue du prado'),
    (
        1,
        7,
        12,
        'rue du faubourg de boutonnet'
    ),
    (1, 8, 1065, 'avenue du père soulas'),
    (2, 9, 35, 'boulevard gambetta'),
    (
        2,
        10,
        13,
        'avenue georges pompidou'
    ),
    (3, 11, 2, 'quai du port'),
    (3, 12, 15, 'avenue du prado'),
    (
        1,
        13,
        12,
        'rue du faubourg de boutonnet'
    ),
    (
        1,
        14,
        1065,
        'avenue du père soulas'
    ),
    (2, 15, 35, 'boulevard gambetta'),
    (
        2,
        16,
        13,
        'avenue georges pompidou'
    ),
    (3, 17, 2, 'quai du port'),
    (3, 18, 15, 'avenue du prado');

INSERT INTO
    commune (code_postal, id, nom)
VALUES
    (34000, 1, 'montpellier'),
    (30000, 2, 'nîmes'),
    (13000, 3, 'marseille');

INSERT INTO
    covoiturage (
        adresse_arrivee_id,
        adresse_depart_id,
        distance_km,
        duree_trajet,
        id,
        nombre_places_restantes,
        organisateur_id,
        vehicule_perso_id,
        date_depart
    )
VALUES
    (
        5,
        7,
        5,
        45,
        2,
        4,
        5,
        1,
        '2023-01-11'
    ),
    (
        1,
        5,
        5,
        23,
        3,
        1,
        6,
        1,
        '2024-06-01'
    ),
    (
        1,
        5,
        5,
        23,
        78,
        1,
        6,
        1,
        '2024-06-01'
    );

INSERT INTO
    covoiturages_collaborateur (collaborateur_id, covoiturage_id)
VALUES
    (3, 2),
    (5, 2);

INSERT INTO
    marque (id, nom)
VALUES
    (1, 'peugeot'),
    (2, 'renault'),
    (3, 'citroën'),
    (4, 'fiat'),
    (5, 'dacia'),
    (6, 'toyota'),
    (7, 'bmw'),
    (8, 'hyundai'),
    (9, 'kia'),
    (10, 'volkswagen'),
    (11, 'skoda'),
    (12, 'seat');

INSERT INTO
    modele (id, marque_id, nom)
VALUES
    (1, 1, '208'),
    (2, 1, '2008'),
    (3, 1, '308'),
    (4, 1, '3008'),
    (5, 1, '508'),
    (6, 1, '5008'),
    (7, 2, 'clio'),
    (8, 2, 'megane'),
    (9, 2, 'twingo'),
    (10, 3, 'c3'),
    (11, 3, 'c4'),
    (12, 3, 'c5'),
    (13, 4, '500'),
    (14, 4, '500x'),
    (15, 4, 'panda'),
    (16, 5, 'duster'),
    (17, 5, 'spring'),
    (18, 6, 'yaris'),
    (19, 6, 'corolla'),
    (20, 6, 'rav4'),
    (21, 7, 'serie 3'),
    (22, 7, 'serie 5'),
    (23, 7, 'serie 7'),
    (24, 8, 'i20'),
    (25, 8, 'i30'),
    (26, 9, 'ceed'),
    (27, 9, 'niro'),
    (28, 9, 'sportage'),
    (29, 10, 'golf'),
    (30, 10, 'polo'),
    (31, 11, 'fabia'),
    (32, 11, 'octavia'),
    (33, 12, 'leon'),
    (34, 12, 'cupra');

/*
 INSERT INTO
 utilisateur (
 id,
 email,
 mot_de_passe,
 nom,
 date_non_valide
 )
 VALUES
 (
 1,
 'admin1@gestit.fr',
 'passadmin1',
 'admin1',
 NULL
 ),
 (
 2,
 'admin2@gestit.fr',
 'passadmin2',
 'admin2',
 NULL
 ),
 (
 3,
 'collab1@gestit.fr',
 'passcollab1',
 'collab1',
 NULL
 ),
 (
 4,
 'collab2@gestit.fr',
 'passcollab2',
 'collab2',
 NULL
 ),
 (
 5,
 'orga1@gestit.fr',
 'passorga1',
 'orga1',
 NULL
 ),
 (
 6,
 'orga2@gestit.fr',
 'passorga2',
 'orga2',
 NULL
 );
 
 INSERT INTO
 utilisateur_roles (roles, utilisateur_id)
 VALUES
 ('administrateur', 1),
 ('administrateur', 2);
 
 INSERT INTO
 vehicule_perso (
 id,
 modele_id,
 nombre_de_place_disponibles,
 proprietaire_id,
 immatriculation
 )
 VALUES
 (1, 1, 5, 5, '34-xxx-43'),
 (2, 8, 2, 1, '88-88-77');
 
 * /