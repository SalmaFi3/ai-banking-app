# Description Détaillée du Système d'Intelligence Artificielle

## 📋 Vue d'ensemble

Le projet **bdcc-ai-agent** utilise un système d'intelligence artificielle basé sur **Spring AI** pour créer un chatbot intelligent intégré à l'application bancaire **CodeQueens**. Le système combine plusieurs technologies avancées pour offrir une expérience conversationnelle fluide et contextuelle.

---

## 🏗️ Architecture du Système AI

### 1. **Framework Principal : Spring AI**

- **Version** : `1.0.0`
- **Framework** : Spring Boot `3.5.4`
- **Langage** : Java `17`

Spring AI est un framework open-source développé par Spring qui simplifie l'intégration de modèles d'intelligence artificielle dans les applications Spring Boot. Il fournit une abstraction unifiée pour interagir avec différents fournisseurs de modèles LLM (Large Language Models).

### 2. **Modèle de Langage : OpenAI GPT**

- **Fournisseur** : OpenAI
- **Modèle configuré** : `gpt-4.1` (à noter : ce modèle pourrait nécessiter une vérification, les modèles standards étant `gpt-4`, `gpt-4-turbo`, `gpt-3.5-turbo`)
- **API Key** : Configurée dans `application.properties`

Le système utilise l'API OpenAI pour générer des réponses intelligentes. OpenAI GPT est un modèle de langage pré-entraîné capable de comprendre le contexte, générer du texte naturel et répondre à des questions complexes.

---

## 🔧 Composants Techniques

### 1. **ChatClient (AIAgent.java)**

Le `ChatClient` est le composant central qui orchestre toutes les interactions avec le modèle AI. Il est configuré avec plusieurs **advisors** (conseillers) et **tools** (outils) :

#### **Advisors (Conseillers)**

- **MessageChatMemoryAdvisor** : 
  - Maintient la mémoire de conversation
  - Permet au chatbot de se souvenir du contexte des messages précédents
  - Crée une expérience conversationnelle continue

- **SimpleLoggerAdvisor** :
  - Enregistre les interactions pour le débogage
  - Facilite le suivi des requêtes et réponses

- **QuestionAnswerAdvisor** :
  - Intègre le système RAG (Retrieval-Augmented Generation)
  - Recherche dans la base de connaissances vectorielle
  - Enrichit les réponses avec des informations contextuelles

#### **Tools (Outils)**

- **AgentTools** :
  - Fournit des fonctions personnalisées que l'AI peut appeler
  - Exemple : `getEmployeeInfo(name)` - récupère les informations d'un employé
  - Permet d'étendre les capacités du chatbot avec des données métier

### 2. **Système RAG (Retrieval-Augmented Generation)**

Le système RAG permet au chatbot d'accéder à une base de connaissances pour enrichir ses réponses.

#### **Composants RAG**

- **VectorStore (SimpleVectorStore)** :
  - Stocke les documents sous forme de vecteurs (embeddings)
  - Permet une recherche sémantique rapide et précise
  - Fichier de stockage : `src/main/resources/store/store.json`

- **EmbeddingModel** :
  - Convertit le texte en vecteurs numériques
  - Utilise le modèle d'embedding d'OpenAI
  - Permet de trouver des documents similaires sémantiquement

- **Document Reader (PagePdfDocumentReader)** :
  - Lit et extrait le contenu des fichiers PDF
  - Source actuelle : `src/main/resources/pdfs/cv.pdf`
  - Peut être étendu pour d'autres types de documents

- **Text Splitter (TokenTextSplitter)** :
  - Divise les documents en chunks (morceaux) de taille optimale
  - Améliore la précision de la recherche vectorielle
  - Gère les limites de tokens des modèles

#### **Processus RAG**

1. **Indexation** (au démarrage) :
   - Lecture du PDF (`cv.pdf`)
   - Découpage en chunks
   - Conversion en embeddings
   - Stockage dans le VectorStore

2. **Recherche** (lors d'une requête) :
   - Conversion de la question en embedding
   - Recherche des documents les plus similaires
   - Injection du contexte dans le prompt
   - Génération de la réponse enrichie

### 3. **Gestion de la Mémoire (ChatMemory)**

- Maintient l'historique de conversation
- Permet des références contextuelles ("ce dont on parlait avant")
- Améliore la cohérence des réponses

### 4. **Réponses en Streaming**

- **Format** : `Flux<String>` (Reactive Streams)
- **Avantage** : Réponses affichées progressivement, pas besoin d'attendre la réponse complète
- **Expérience utilisateur** : Plus fluide et réactive

---

## 📝 Configuration du Prompt Système

Le système utilise un **prompt système** personnalisé pour guider le comportement du chatbot :

```
Tu es un assistant virtuel pour l'application bancaire **CodeQueens**.
Ton rôle est d'aider les utilisateurs avec les fonctionnalités de l'application bancaire CodeQueens uniquement.
Réponds en français, de façon claire et polie.

IMPORTANT :
- Ne mentionne JAMAIS de CV, de personnes, ou d'informations personnelles.
- Concentre-toi UNIQUEMENT sur l'application CodeQueens : gestion de comptes bancaires, clients, transactions, etc.
- Si l'utilisateur pose une question générale, réponds sur les fonctionnalités de CodeQueens.
- Quand c'est pertinent, propose 2 ou 3 idées de questions que l'utilisateur peut poser sur CodeQueens.
```

Ce prompt définit :
- Le rôle et le contexte du chatbot
- Les règles de comportement
- Les restrictions (ne pas mentionner le CV)
- Les instructions de formatage

---

## 🎯 Fonctionnalités Spécifiques

### 1. **Réponses Personnalisées**

Le système inclut des réponses pré-définies pour certaines interactions :

- **Salutation "bonjour"** :
  - Détection automatique du mot "bonjour"
  - Réponse personnalisée de bienvenue
  - Suggestions de questions possibles

### 2. **Intégration avec l'Application**

- **Endpoint REST** : `GET /askAgent?query={question}`
- **CORS activé** : Permet les requêtes depuis le frontend Angular
- **Format de réponse** : Texte brut (`TEXT_PLAIN`)

### 3. **Extensibilité**

Le système est conçu pour être facilement extensible :

- **Ajout de nouveaux outils** : Créer de nouvelles méthodes dans `AgentTools`
- **Ajout de documents** : Modifier `RagDocumentIndexor` pour indexer d'autres PDFs
- **Modification du prompt** : Ajuster le prompt système dans `AIAgent.java`
- **Changement de modèle** : Modifier `application.properties`

---

## 📦 Dépendances Principales

```xml
<!-- Spring AI OpenAI -->
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-model-openai</artifactId>
</dependency>

<!-- RAG et Vector Store -->
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-advisors-vector-store</artifactId>
</dependency>

<!-- Lecteur PDF -->
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-pdf-document-reader</artifactId>
</dependency>
```

---

## 🔄 Flux de Traitement d'une Requête

1. **Réception** : Le contrôleur `AgentController` reçoit la requête HTTP
2. **Vérification** : `AIAgent.onQuery()` vérifie si c'est une salutation spéciale
3. **Traitement RAG** : Le `QuestionAnswerAdvisor` recherche dans le VectorStore
4. **Construction du Prompt** : Ajout du prompt système et de la question utilisateur
5. **Appel API OpenAI** : Envoi de la requête au modèle GPT
6. **Streaming** : Réception et transmission progressive de la réponse
7. **Affichage** : Le frontend affiche la réponse au fur et à mesure

---

## 🎨 Points Forts du Système

1. **Architecture Modulaire** : Composants séparés et réutilisables
2. **Mémoire Conversationnelle** : Contexte maintenu entre les messages
3. **RAG Intégré** : Accès à une base de connaissances
4. **Streaming** : Réponses en temps réel
5. **Extensibilité** : Facile d'ajouter de nouvelles fonctionnalités
6. **Intégration Spring** : S'intègre naturellement avec Spring Boot

---

## 🔧 Configuration Technique

### Variables d'Environnement

- **Port du serveur** : `8081`
- **Modèle OpenAI** : `gpt-4.1`
- **API Key** : Configurée dans `application.properties`

### Fichiers de Configuration

- `application.properties` : Configuration OpenAI et serveur
- `pom.xml` : Dépendances Maven et versions
- `RagDocumentIndexor.java` : Configuration du VectorStore

---

## 📊 Métriques et Performance

- **Temps de réponse** : Dépend de l'API OpenAI (généralement 1-5 secondes)
- **Streaming** : Réponses affichées progressivement pour une meilleure UX
- **Mémoire** : Gérée automatiquement par `ChatMemory`
- **VectorStore** : Recherche sémantique rapide grâce aux embeddings

---

## 🚀 Améliorations Possibles

1. **Changement de modèle** : Utiliser `gpt-4-turbo` ou `gpt-3.5-turbo` pour de meilleures performances/coûts
2. **Base de connaissances** : Remplacer le CV par de la documentation sur CodeQueens
3. **Nouveaux outils** : Ajouter des outils pour interroger la base de données bancaire
4. **Gestion d'erreurs** : Améliorer la gestion des erreurs API
5. **Cache** : Implémenter un cache pour les questions fréquentes
6. **Analytics** : Ajouter un suivi des questions et réponses

---

## 📚 Ressources

- **Spring AI Documentation** : https://docs.spring.io/spring-ai/reference/
- **OpenAI API Documentation** : https://platform.openai.com/docs
- **RAG Pattern** : https://en.wikipedia.org/wiki/Retrieval-augmented_generation

---

*Document généré le : 2026-01-07*
*Version du système : 0.0.1-SNAPSHOT*
