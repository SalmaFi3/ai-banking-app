# ai-banking-app 🏦

Application de gestion bancaire avec agent IA intégré.

## Description

Application full-stack permettant de gérer des clients, des comptes bancaires (courant et épargne) et des opérations bancaires (débit/crédit), avec un assistant IA conversationnel capable de répondre aux questions des utilisateurs et de fournir des informations sur les comptes et transactions.

## Architecture

* Backend : Spring Boot + Spring Data JPA + Spring AI
* Frontend : Angular 13
* Agent IA : Spring AI + OpenAI GPT-4 + RAG
* Base de données : H2 (développement) / MySQL (production)

## Installation

### Backend

```bash
cd BanckAccount-Management-master
mvn spring-boot:run
```

### Agent IA

```bash
cp application.properties.example application.properties
```

Ajouter votre clé OpenAI dans le fichier `application.properties`.

```bash
cd bdcc-ai-agent
mvn spring-boot:run
```

### Frontend

```bash
cd BanckAccount-Management-FrontEnd-master
npm install
ng serve
```

## Fonctionnalités

* Gestion des clients (CRUD)
* Gestion des comptes bancaires (CRUD)
* Comptes courants et comptes épargne
* Opérations de débit et crédit
* Consultation des comptes
* Historique des opérations
* Agent IA conversationnel en français
* Recherche intelligente via RAG

## Architecture Fonctionnelle

1. L'utilisateur interagit avec l'interface Angular.
2. Les requêtes sont envoyées au backend Spring Boot.
3. Les données sont stockées dans H2 ou MySQL.
4. L'agent IA Spring AI traite les questions des utilisateurs.
5. GPT-4 génère des réponses contextualisées.

## Équipe

* Salma — Workflow & Release Manager
* [Prénom] — Feature & AI Lead
* [Prénom] — Quality & Refactoring Lead
* Aya Kmaiti — Business & Documentation Lead

## Version

Version 1.0
