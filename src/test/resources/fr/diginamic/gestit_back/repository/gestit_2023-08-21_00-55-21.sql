INSERT INTO
  `adresse` (`commune_id`, `id`, `numero`, `voie`)
VALUES
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

INSERT INTO
  `commune` (`code_postal`, `id`, `nom`)
VALUES
  (34000, 1, "Montpellier"),
  (30000, 2, "Nîmes"),
  (13000, 3, "Marseille");

INSERT INTO
  `covoiturage` (
    `adresse_arrivee_id`,
    `adresse_depart_id`,
    `distance_km`,
    `duree_trajet`,
    `id`,
    `nombre_places_restantes`,
    `organisateur_id`,
    `vehicule_perso_id`,
    `date_depart`
  )
VALUES
  (4, 6, 456, 789, 1, 2, 3, 1, NULL),
  (5, 7, 5, 45, 2, 4, 5, 1, "2023-01-11"),
  (1, 5, 5, 23, 3, 1, 6, 1, "2024-06-01"),
  (1, 5, 5, 23, 78, 1, 6, 1, "2024-06-01");

INSERT INTO
  `covoiturages_collaborateur` (`collaborateur_id`, `covoiturage_id`)
VALUES
  (3, 2),
  (5, 2);

INSERT INTO
  `marque` (`id`, `nom`)
VALUES
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

INSERT INTO
  `modele` (`id`, `marque_id`, `nom`)
VALUES
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

INSERT INTO
  `utilisateur` (
    `id`,
    `email`,
    `mot_de_passe`,
    `nom`,
    `date_non_valide`
  )
VALUES
  (
    1,
    "admin1@gestit.fr",
    "passAdmin1",
    "Admin1",
    NULL
  ),
  (
    2,
    "admin2@gestit.fr",
    "passAdmin2",
    "Admin2",
    NULL
  ),
  (
    3,
    "collab1@gestit.fr",
    "passCollab1",
    "Collab1",
    NULL
  ),
  (
    4,
    "collab2@gestit.fr",
    "passCollab2",
    "Collab2",
    NULL
  ),
  (5, "orga1@gestit.fr", "passOrga1", "Orga1", NULL),
  (6, "orga2@gestit.fr", "passOrga2", "Orga2", NULL);

INSERT INTO
  `utilisateur_roles` (`roles`, `utilisateur_id`)
VALUES
  ("ADMINISTRATEUR", 1),
  ("ADMINISTRATEUR", 2);

INSERT INTO
  `vehicule_perso` (
    `id`,
    `modele_id`,
    `nombre_de_place_disponibles`,
    `proprietaire_id`,
    `immatriculation`
  )
VALUES
  (1, 1, 5, 5, "34-XXX-43"),
  (2, 8, 2, 1, "88-88-77");