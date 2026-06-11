# Guide de contribution - ai-banking-app

## Workflow Git

### Branches
- `main` : code stable, jamais modifié directement
- `develop` : branche de travail commune
- `feature/xxx` : nouvelles fonctionnalités
- `hotfix/xxx` : corrections urgentes

### Convention des commits
feat: ajout d'une nouvelle fonctionnalité
fix: correction d'un bug
refactor: refactoring du code
docs: mise à jour de la documentation
chore: tâches de maintenance

### Processus
1. Créer une branche depuis develop
2. Coder et commiter
3. Ouvrir une Pull Request vers develop
4. Faire reviewer par un coéquipier
5. Merger après approbation

### Exemples de commits
feat: ajout du composant chatbot
fix: correction du typo dans home.component
refactor: simplification du BankAccountService
