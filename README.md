# 🚀 User Management API

Une API RESTful robuste pour la gestion d'utilisateurs, développée avec **Java 21** et **Spring Boot 3**.
Ce projet démontre une architecture en couches (N-Tiers), la conteneurisation, les bonnes pratiques de validation et les **tests unitaires**.

## 🛠️ Stack Technique

* **Langage :** Java 21
* **Framework :** Spring Boot 3
* **Base de données :** PostgreSQL (via Docker)
* **Architecture :** MVC (Controller, Service, Repository)
* **Testing :** JUnit 5, Mockito
* **Documentation :** Swagger UI / OpenAPI
* **Build Tool :** Maven

## ✨ Fonctionnalités

* ✅ **CRUD Complet** : Gestion totale des utilisateurs via les verbes HTTP standards :
    * **POST** : Création avec validation.
    * **GET** : Lecture (Liste ou par ID).
    * **PUT** : Mise à jour des informations.
    * **DELETE** : Suppression sécurisée.
* 🛡️ **Validation des données** : Utilisation de Jakarta Validation (`@NotBlank`, `@Size`) pour protéger l'intégrité de la BDD.
* 🧪 **Tests Unitaires** : Couverture complète des couches Service et Controller (Create, Read, Update, Delete) avec Mockito & MockMvc.
* 🐋 **Dockerisé** : Base de données PostgreSQL instanciée via Docker Compose.
* 🔍 **Gestion des erreurs** : Retours HTTP cohérents (404 Not Found, 400 Bad Request, 204 No Content).
* 📖 **Documenté** : Interface Swagger UI accessible pour tester l'API visuellement.

## 🚀 Comment lancer le projet

### Pré-requis
* Java 21
* Docker & Docker Compose
* Maven

### Installation

1.  **Cloner le dépôt**
    ```bash
    git clone git@github.com:TonPseudo/first-api-spring.git
    cd first-api-spring
    ```

2.  **Lancer la base de données**
    ```bash
    docker-compose up -d
    ```

3.  **Lancer l'application**
    ```bash
    mvn spring-boot:run
    ```

L'API sera accessible sur : `http://localhost:8080`

## 🧪 Lancer les Tests

Le projet inclut une suite de tests unitaires utilisant **JUnit 5** et **Mockito** pour garantir la non-régression :

* **Service Layer :** Test de la logique métier avec Mock du Repository.
* **Controller Layer :** Test des endpoints HTTP et des codes retours avec `MockMvc`.

Pour exécuter tous les tests :
    ```bash
    mvn test
    ```

## 📚 Documentation API (Swagger)

Une fois l'application lancée, accédez à la documentation interactive ici :
👉 **[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)**

## 🏗️ Architecture

Le projet respecte le pattern **Controller-Service-Repository** :
* `UserController` : Gère les requêtes HTTP et la validation.
* `UserService` : Contient la logique métier.
* `UserRepository` : Interface JPA pour communiquer avec PostgreSQL.

---
*Projet réalisé dans le cadre d'une auto-formation technique.*