# AI Integration — Spring AI Agent
**Réalisé par : Doha**

## Vue d'ensemble
Ce projet intègre Spring AI pour fournir un assistant bancaire intelligent
capable de répondre aux questions des clients en français.

## Architecture
## Composants principaux

### ChatClient
Envoie les messages de l'utilisateur au modèle IA et retourne la réponse.

### Spring AI
Fait le lien entre l'application Spring Boot et le modèle de langage (LLM).

### RAG (Retrieval Augmented Generation)
Permet à l'agent de chercher dans les documents bancaires pour répondre
avec des informations précises.

### VectorStore
Stocke les embeddings des documents pour la recherche sémantique.

## Tester l'agent en français
Envoyer une requête POST à `/api/chat` :
```json
{ "message": "Quel est le solde de mon compte ?" }
```
L'agent répond en français.

## Technologies utilisées
- Spring Boot 4
- Spring AI
- OpenAI / Ollama
- VectorStore (in-memory ou pgvector)