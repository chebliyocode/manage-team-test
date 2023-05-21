# Projet Spring Boot avec Architecture Hexagonale

Ce projet est développé en utilisant une architecture hexagonale (également connue sous le nom de "Ports and Adapters" ou "Clean Architecture") pour organiser et structurer le code de manière modulaire et indépendante.


## Prérequis
Java 17 ou une version ultérieure

## Structure du projet

La structure du projet est faite de la manière suivante :

- **src/main/java**: Contient le code source de l'application.
  - **com.manage.team**: Package racine de l'application.
    - **application**: Classes de la logique métier de l'application.
    - **domain**: Classes du domaine métier.
    - **infrastructure**: Classes de l'infrastructure.
- **src/test/java**: Contient les tests unitaires et d'intégration.
- **src/main/resources**: Contient les fichiers de configuration de l'application.

## Packages

- **com.manage.team.infrastructure**: Ce package contient les classes qui fournissent une implémentation de l'infrastructure de l'application. Il s'agit de la couche qui interagit avec des systèmes externes tels que les bases de données, les services Web ou d'autres services tiers.

  - **Adapters**: Ce sous-package contient les classes d'adaptateur spécifiques pour les entités du domaine. Par exemple, le sous-package "player" contient l'adaptateur "PlayerAdapter" qui implémente l'interface "PlayerPort" définie dans la couche de domaine. Ces adaptateurs permettent de convertir les objets du domaine en objets adaptés à la communication avec les systèmes externes.

  - **Entities**: Ce sous-package contient les classes d'entité qui représentent les objets persistants dans la base de données. Par exemple, le sous-package "player" contient l'entité "PlayerEntity" qui représente un joueur dans la base de données. Les entités sont généralement utilisées pour mapper les données entre les objets du domaine et la représentation persistante.

  - **Repositories**: Ce sous-package contient les interfaces de repository qui définissent les opérations de lecture/écriture des entités dans la base de données. Par exemple, le sous-package "player" contient l'interface "PlayerRepository" qui définit les opérations de lecture/écriture pour les joueurs. Les implémentations concrètes de ces interfaces se trouvent généralement dans ce même sous-package.

- **com.manage.team.domain**: Ce package contient les classes qui représentent le cœur métier de l'application. Il s'agit de la couche la plus interne de l'architecture hexagonale.

  - **Models**: Ce sous-package contient les classes de modèle qui représentent les entités principales du domaine. Par exemple, les classes "Player" et "Team" représentent respectivement un joueur et une équipe. Ces modèles encapsulent les attributs et le comportement spécifique au domaine.

  - **Usecase**: Ce sous-package contient les cas d'utilisation du domaine. Chaque cas d'utilisation est représenté par une classe dans ce sous-package. Par exemple, le sous-package "player" contient les classes liées au cas d'utilisation "player". Ces classes contiennent la logique métier spécifique au cas d'utilisation, en utilisant les modèles et en interagissant avec les interfaces de port définies.

  - **Exceptions**: Ce sous-package contient les exceptions spécifiques au domaine. Il contient les classes qui étendent les exceptions de base ou les exceptions personnalisées pour gérer les erreurs et les situations exceptionnelles spécifiques au domaine. Par exemple, les classes "BadInputException", "NotFoundBusinessException" sont utilisées pour gérer respectivement les erreurs de mauvaise entrée et les erreurs de ressource non trouvée.

## Configuration

- Les fichiers de configuration de l'application se trouvent dans le répertoire `src/main/resources`.
- Les propriétés d'application sont définies dans `application.properties`.
- Les propriétés de test sont définies dans `application-test.properties`.
- Le fichier de configuration du logging est `logback-test.xml`.


Cette architecture permet de séparer les préoccupations et de faciliter la maintenance et l'évolution du code. Les différentes parties de l'application sont indépendantes les unes des autres.

# Tester le projet

## API Postman

Vous pouvez utiliser le fichier de collection Postman fourni dans le répertoire `resources/postman` pour tester les points de terminaison de l'API. Importez ce fichier dans Postman pour obtenir tous les endpoints prédéfinis avec leurs configurations.
### Instructions

1. Une fois la collection importée, tu trouveras les services disponibles répartis en deux catégories :

  - **Player Services** : Services liés aux joueurs.
  - **Team Services** : Services liés aux équipes.

2. Exploite les services selon tes besoins en suivant les instructions ci-dessous.

### Player Services

#### Create Player 1

- Description : Crée un nouveau joueur.
- Méthode : POST
- URL : `http://localhost:8080/manage/player`
- Corps de la requête (raw) :

   ```json
   {
       "playerId" : "OGCN12355672",
       "name": "Kasper Schmeichel",
       "position": "keeper"
   }

#### Create Player 2

- Description : Crée un nouveau joueur.
- Méthode : POST
- URL : `http://localhost:8080/manage/player`
- Corps de la requête (raw) :

   ```json
   {
       "playerId" : "PSG12345772",
       "name": "Marco Verratti",
       "position": "Midfielder"
   }

#### Find All Players Pageable

- Description : Récupère tous les joueurs de manière paginée.
- Méthode : GET
- URL : `http://localhost:8080/manage/players`
- Corps de la requête (raw) :

   ```json
   {
     "page": 0,
     "size": 10,
     "sort": "position",
     "order": "ASC"
   }

### Team Services

Les services suivants sont disponibles pour la gestion des équipes.

#### Create PSG Team With Players

- Description : Crée une équipe du Paris Saint-Germain avec des joueurs.
- Méthode : POST
- URL : `http://localhost:8080/manage/team`
- Corps de la requête (raw) :

   ```json
   {
       "id" : 1,
       "name": "Paris Saint-Germain",
       "acronym": "PSG",
       "budget": "700000000",
       "players" : [
           {
               "playerId" : "PSG12345672",
               "name": "Kilian Mbappe",
               "position": "Attacker"
           },
           {
               "playerId" : "PSG1234362",
               "name": "Hakimi achraf",
               "position": "Defender"
           },
           {
               "playerId" : "PSG1234363",
               "name": "Lionel Messi",
               "position": "Attacker"
           },
           {
               "playerId" : "PSG1234364",
               "name": "Neymar",
               "position": "Attacker"
           }
       ]
   }

#### Create Nice Team With Players

- Description : Crée une équipe de l'OGC Nice avec des joueurs.
- Méthode : POST
- URL : `http://localhost:8080/manage/team`
- Corps de la requête (raw) :

   ```json
   {
       "id" : 2,
       "name": "OGC Nice",
       "acronym": "OGCN",
       "budget": "100000000",
       "players" : [
           {
               "playerId" : "OGCN12345672",
               "name": "Aaron Ramsey",
               "position": "Midfielder"
           },
           {
               "playerId" : "OGCN1234593",
               "name": "Nicolas Pépé",
               "position": "Attacker"
           },
           {
               "playerId" : "OGCN1234594",
               "name": "Marcin Bulka",
               "position": "Keeper"
           }
       ]
   }

#### Create Marseille Team Without Players

- Description : Crée une équipe de l'Olympique de Marseille sans joueurs.
- Méthode : POST
- URL : `http://localhost:8080/manage/team`
- Corps de la requête (raw) :

   ```json
   {
       "id" : 3,
       "name": "Olympique de Marseille",
       "acronym": "OM",
       "budget": "500000000"
   }

#### Find All Teams Pageable

- Description : Récupère toutes les équipes avec pagination.
- Méthode : GET
- URL : `http://localhost:8080/manage/teams`
- Corps de la requête (raw) :

   ```json
   {
     "page": 0,
     "size": 10,
     "sort": "budget",
     "order": "DESC"
   }


## Tests unitaires

Le répertoire `test` contient les tests unitaires de l'application. Les tests sont organisés en fonctionnalités et regroupés par cas d'utilisation.

## Docker

Le projet peut être exécuté dans un conteneur Docker. Vous pouvez créer une image Docker à partir de l'application et la déployer dans un conteneur Docker pour faciliter le déploiement et la portabilité de l'application. Assurez-vous d'avoir Docker installé sur votre système avant de continuer.

Pour créer une image Docker de l'application, utilisez la commande suivante :

```bash
docker build -t manage-team-test .
```

Une fois la construction terminée, vous pouvez exécuter le conteneur Docker en utilisant la commande suivante :
```bash
docker run -p 8080:8080 manage-team-test
```

## API docs

- Swagger UI : http://localhost:8080/swagger-ui/index.html