# Task Manager — API REST

API de gestion de projets et de tâches, sécurisée par JWT, développée avec Java 21 et Spring Boot 3.

> **Statut : en pause.** Le backend est fonctionnel (authentification, gestion des utilisateurs, projets et tâches). Les prochaines étapes sont listées dans la [feuille de route](#feuille-de-route).

## Fonctionnalités

- **Authentification** : inscription et connexion, émission d'un token JWT
- **Rôles** : distinction administrateur / utilisateur, compte administrateur créé au démarrage
- **Projets** : création et modification
- **Tâches** : création dans un projet, consultation, mise à jour complète ou partielle, suppression
  - Priorité : `LOW`, `MEDIUM`, `HIGH`
  - Statut : `TODO`, `IN_PROGRESS`, `DONE`
- **Administration** : liste paginée et suppression des utilisateurs
- **Gestion d'erreurs centralisée** : format de réponse uniforme (erreurs métier, validation, format JSON, typage)
- **Documentation interactive** via Swagger UI

## Stack technique

| Domaine | Technologies |
|---|---|
| Langage | Java 21 |
| Framework | Spring Boot 3.4.4, Spring Web, Spring Data JPA, Bean Validation |
| Sécurité | Spring Security, JWT (jjwt) |
| Base de données | MySQL (H2 pour les tests) |
| Mapping | MapStruct 1.5.5, DTO en records Java |
| Documentation | springdoc-openapi (Swagger UI) |
| Tests | JUnit 5, Mockito, Spring Security Test |
| Build | Maven 3.9.9 |

## Architecture

Le code est organisé **par domaine fonctionnel** (feature-based) plutôt que par couche technique : chaque module regroupe son contrôleur, son service, son repository, ses entités, ses DTO, son mapper et ses exceptions.

```
src/main/java/com/dim/taskmanager/
├── auth/        # inscription, connexion, JWT, filtre d'authentification
├── user/        # utilisateurs et administration
├── project/     # projets
├── task/        # tâches (priorité, statut)
├── security/    # configuration Spring Security, gestion des accès refusés
├── exception/   # gestionnaire global et modèles d'erreur
├── mapper/      # mapper générique MapStruct
├── response/    # enveloppes de réponse communes (pagination, erreurs)
└── config/      # propriétés de l'application
```

## Endpoints principaux

Préfixe : `/{api.prefix}/{api.version}` (ex. `/api/v1`)

| Méthode | Route | Description | Accès |
|---|---|---|---|
| POST | `/auth/register` | Inscription | Public |
| POST | `/auth/login` | Connexion, retourne un JWT | Public |
| POST | `/project` | Créer un projet | Authentifié |
| PUT | `/project/{id}` | Modifier un projet | Authentifié |
| POST | `/task/{projectId}` | Créer une tâche dans un projet | Authentifié |
| GET | `/task` | Lister les tâches | Authentifié |
| GET | `/task/{id}` | Consulter une tâche | Authentifié |
| PUT | `/task/{taskId}/project/{projectId}` | Mettre à jour une tâche | Authentifié |
| PATCH | `/task/{taskId}/project/{projectId}` | Mise à jour partielle d'une tâche | Authentifié |
| DELETE | `/task/{id}` | Supprimer une tâche | Authentifié |
| GET | `/admin/users` | Lister les utilisateurs | Admin |
| DELETE | `/admin/users/{id}` | Supprimer un utilisateur | Admin |

La documentation complète est disponible sur `http://localhost:8080/swagger-ui.html` une fois l'application lancée.

## Lancer le projet

### Prérequis

- Java 21
- MySQL 8 avec une base créée (ex. `taskmanager`)

### Configuration

Le fichier `src/main/resources/application.properties` n'est pas versionné. Créez-le à partir de ce modèle :

```properties
# API
api.prefix=api
api.version=v1

# Compte administrateur créé au démarrage
api.admin.user-name=admin
api.admin.email=admin@example.com
api.admin.password=changeme
api.admin.role=ADMIN

# JWT
jwt.secret=une-cle-secrete-longue-d-au-moins-32-caracteres
jwt.expiration=3600000

# Base de données
spring.datasource.url=jdbc:mysql://localhost:3306/taskmanager
spring.datasource.username=root
spring.datasource.password=changeme
spring.jpa.hibernate.ddl-auto=update
```

### Démarrage

```bash
cd backend
./mvnw spring-boot:run
```

### Tests

```bash
cd backend
./mvnw test
```

## Feuille de route

- [x] Authentification JWT et gestion des rôles
- [x] CRUD projets et tâches, pagination
- [x] Gestion d'erreurs centralisée et documentation Swagger
- [x] Tests unitaires et d'intégration sur l'authentification
- [ ] Conteneurisation : Dockerfile et `docker-compose` avec MySQL
- [ ] Intégration continue avec GitHub Actions
- [ ] Extension des tests aux modules projets et tâches
- [ ] Frontend Angular

## Auteur

**Dimitri Lory** — Développeur Full Stack Java
[GitHub](https://github.com/dimitrilory44)
