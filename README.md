# SS25-DevOps TaskVault CI/CD

## Commit-Konventionen

Wir nutzen ein verkürztes Conventional-Commits-Schema – *einzeilig, kurz, keine Punkte am Ende*:

- `feat: ...` – neue Funktion  
- `fix: ...` – Bugfix  
- `chore: ...` – Build/DevOps/Tools (keine Code-Änderung)  
- `docs: ...` – Doku/README  
- `refactor: ...` – Code-Umstrukturierung ohne Feature/Fix  
- `test: ...` – Tests  
- `ci: ...` – CI/CD-Änderungen  

**Beispiele**  
- `feat: add endpoint GET /api/categories`  
- `fix: initialize JPA with taskvault-unit in container`  
- `ci: add ghcr publish job`  

---

## Docker Compose Setup

### Lokale Entwicklung
Für lokale Tests und Entwicklung mit Postgres + pgAdmin:

```docker compose -f docker-compose.yaml up -d```
- API erreichbar auf http://localhost:8080
- pgAdmin erreichbar auf http://localhost:5050 (Login siehe docker-compose.yaml)

### CI/CD Umgebung

In CI/CD wird docker-compose.ci.yml verwendet.
Diese Version ist angepasst für automatisierte Tests und Deployment und wird in den GitHub Actions Workflows genutzt.

```docker compose -f docker-compose.ci.yml up -d```

## Workflow-Übersicht

In .github/workflows/ gibt es aktuell drei Workflows:
- ci-build.yml → führt Build, Tests, Linting & Sonar aus (Continuous Integration)
- cd-image.yml → baut das API-Docker-Image und pushed es zu GHCR (nur nach erfolgreichem CI)
- cd-deploy.yml → dient für Deployment (Container starten, später erweiterbar)