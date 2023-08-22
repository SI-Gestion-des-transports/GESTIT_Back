# GEStion de Transport Intra enTreprise


L'application WEB de gestion des transports intra muros permettant à tous vos collaborateurs de :

- réserver un véhicules de services pour un déplacement professionnel
- d’organiser facilement des covoiturages
- de réserver une place sur un covoiturage existant.

Gérez votre parc de véhicule comme bon vous semble:

- Ajouter un véhicule
- Mettre un véhicule en travaux
- Supprimer un véhicule
- Gérer le cycle de vie

## Fonctionnalités

## Authentification

Nous avons utilisé la base de donnée Redis, il faut tout d'abord installer : redis et mysql. 
Le Redis est pour contrôler le système logIn et logOut.

Pour faire marcher le système, veuillez suivre les instructions suivantes: 

1- Aller le fichier start.sql en utilisant le chemin ci-dessous:
src/main/resources/scripts_sql/start.sql
copier le script dans la base de donnée 

2- Authentification
aller dans http/test/authentification.http
- créer un nouveau utilisateur

POST localhost:8080/utilisateur/create
Content-Type: application/json

{
  "nom": "Admin",
  "email": "dijialeiAdmin@gmail.com",
  "motDePasse": "Admin"
}

- faire votre log in et vous recevrez un TOKEN-JWT:

POST localhost:8080/login
Content-Type: application/json

{
  "email": "dijialeiAdmin@gmail.com",
  "motDePasse": "Admin"
}

- pour tester le log out: 

POST localhost:8080/signout
Content-Type: application/json
JWT-TOKEN: "votre JWT-TOKEN reçu lors du logIn"

## Autorisation

## Gérer les utilisateurs
Pour faire l'opération CRUD pour les utilisateur, utilisez le chemin suivant: 
http/test/utilisateur.http

## Gérer les covoiturages



## Gérer les véhicules personnelles 
Pour faire l'opération CRUD pour les utilisateur, utilisez le chemin suivant: 
http/test/vehiculePerso.http

## Gérer les réservation des véhicules de service



## Gérer les véhicules de service
Pour faire l'opération CRUD pour les utilisateur, utilisez le chemin suivant: 
http/test/vehiculeService.http
