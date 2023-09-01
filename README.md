# GEStion de Transport Intra enTreprise
![logo du projet](logo.png "SIGET_logo")

L'application WEB de gestion des transports intra muros permettant à tous vos collaborateurs de :

- réserver un véhicules de services pour un déplacement professionnel
- d’organiser facilement des covoiturages
- de réserver une place sur un covoiturage existant.

Gérez votre parc de véhicule comme bon vous semble:

- Ajouter un véhicule
- Mettre un véhicule en travaux
- Supprimer un véhicule
- Gérer le cycle de vie


## Remarque

Pour l'interface de test, l'application est fournie avec une interface grapghique sur navigateur : SwaggerUI.
Elle est accessible depuis cette adresse :
http://localhost:8080/swagger-ui.html

Il reste possible de faire manuellement des requêtes http depuis 

## Pour démarrer
Pour démarrer l'application et l'utiliser, veiller à entrer au préalable les informations de votre database locale dans le fichier applications.properties :
spring.datasource.url=jdbc:mariadb://localhost:3306/gestit
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=root

Les tests quant à eux se feront via une database en mémoire, H2.


## Fonctionnalités

## Authentification

Nous avons utilisé la base de donnée Redis, il faut tout d'abord installer : redis et mysql. 
Le Redis est pour contrôler le système logIn et logOut.

Pour faire marcher le système, veuillez suivre les instructions suivantes: 

1- Démarrer l'application

src/main/java/fr/diginamic/gestit_back/GestitBackApplication.java

2- Aller le fichier start.sql en utilisant le chemin ci-dessous:
src/main/resources/scripts_sql/start.sql
Copier le script dans la base de donnée 

#### Note: `Le fichier loginMailsPW.txt dans le même répertoire contient les mots de passe en clair pour tous les utilisateurs insérés en base via le script. C'est nécessaire pour s'autentifier.`

3- Authentification
- Faire votre login et vous recevrez un TOKEN-JWT.
Aller dans  : http/testPourOA/authentification.http
Vous pouvez vous logger en COllaborateur ou en administrateur.

#### Note: `Veillez à conserver ce token JWT pour toutes les requêtes HTTP que vous souhaitez tester.`

- pour tester le log out: 

POST localhost:8080/signout
Content-Type: application/json
JWT-TOKEN: "votre JWT-TOKEN reçu lors du logIn"

## Autorisation
Des rôles, collaborateur et administrateur, sont définis et permettent de limiter l'accès à certaines méthodes.

## Gérer les utilisateurs
Pour faire l'opération CRUD pour les utilisateur, utilisez le chemin suivant :
http/testPourOA/utilisateur.http

## Gérer les covoiturages
Pour faire l'opération CRUD pour les covoiturages, utilisez le chemin suivant :
http/testPourOA/covoiturages.http

## Gérer les véhicules personnels 
Pour faire l'opération CRUD pour les véhicules personnels, utilisez le chemin suivant :
http/testPourOA/vehiculePerso.http

## Gérer les réservation des véhicules de service
Pour faire l'opération CRUD pour les réservations de véhicules de service, utilisez le chemin suivant :
http/testPourOA/reservationVS.http

## Gérer les véhicules de service
Pour faire l'opération CRUD pour les véhicules de service, utilisez le chemin suivant : 
http/testPourOA/vehiculeService.http
