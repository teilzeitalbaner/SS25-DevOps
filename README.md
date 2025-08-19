# SS25-DevOps TaskVault CI/CD
TaskVault ist ein Java-basiertes Projekt mit JPA/Hibernate, PostgreSQL und einer REST-API.
Dieses Repository enthält zusätzlich eine vollständige CI/CD-Pipeline auf GitHub Actions, inkl. Docker & GHCR Deployment.

## Quickstart
```git clone https://github.com/teilzeitalbaner/SS25-DevOps.git```
```cd SS25-DevOps/java-project/taskvault```
```docker compose -f docker-compose.yaml up -d ```

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
## Build Status
[![CI Build](https://github.com/teilzeitalbaner/SS25-DevOps/actions/workflows/ci-build.yml/badge.svg)](https://github.com/teilzeitalbaner/SS25-DevOps/actions/workflows/ci-build.yml)

## Docker Image Version (GHCR)
[![Docker Image](https://img.shields.io/badge/ghcr.io-taskvault--api-blue)](https://github.com/users/teilzeitalbaner/packages/container/package/taskvault-api)

## Docker Compose Setup

### Lokale Entwicklung
Für lokale Tests und Entwicklung mit Postgres + pgAdmin:

```docker compose -f docker-compose.yaml up -d```
- API erreichbar auf http://localhost:8080
- pgAdmin erreichbar auf http://localhost:5050 (Login siehe docker-compose.yaml)

**Tipp:**
Beim ersten Start oder nach Änderungen am Dockerfile empfiehlt sich:
``` docker compose -f docker-compose.yaml up --build -d ```

### CI/CD Umgebung

In CI/CD wird docker-compose.ci.yml verwendet.
Diese Version ist angepasst für automatisierte Tests und Deployment und wird in den GitHub Actions Workflows genutzt.

```docker compose -f docker-compose.ci.yml up -d```

## Workflow-Übersicht
Alle Workflows liegen unter .github/workflows/.

**1. ci-build.yml**
- Baut das Projekt (Maven)
- Führt alle Unit-/Integrationstests aus
- Wird auf jeden Push & PR ausgeführt

**2. cd-image.yml**
- Baut ein Docker-Image mit der API
- Push nach GHCR (ghcr.io/teilzeitalbaner/taskvault-api)
- Tags: latest + GitHub Run Number
- Startet automatisch, wenn ci-build.yml erfolgreich war

**3. cd-deploy.yml**
- Zieht das Image aus GHCR
- Startet per docker-compose.ci.yml auf GitHub Runner
- Wartet, bis API erreichbar (HTTP 200)
- Optional: prüft DB-Tabellen
- Stoppt & cleaned Container danach wieder

**4. Release Workflow**
- Erstellt Git Tag + GitHub Release
- Retaggt vorhandenes GHCR-Image auf vX.Y.Z, vX.Y und vX
- Nur manuell per Workflow-Dispatch startbar

## Docker Image (GHCR)

Das aktuellste API-Image ist immer im GitHub Container Registry verfügbar:

```docker pull ghcr.io/teilzeitalbaner/taskvault-api:latest```

Alternative Versionen (z. B. von CI Run Nummern) sind ebenfalls verfügbar.

## Projektstruktur

```plaintext
SS25-DevOps/
├── .github/
│   └── workflows/                # GitHub Actions Workflows
│       ├── ci-build.yml          # Build & Test Pipeline
│       ├── cd-image.yml          # Docker Image Build & Push (GHCR)
│       └── cd-deploy.yml         # Deployment mit docker-compose
│
├── java-project/
│   └── taskvault/                # Hauptprojekt (Java, Maven)
│       ├── .dockerignore
│       ├── .gitignore
│       ├── Dockerfile
│       ├── docker-compose.yaml          # Lokale Dev-Umgebung
│       ├── docker-compose.ci.yml        # CI/CD-Compose
│       ├── pom.xml                      # Parent-POM
│       │
│       ├── api/                         # API-Modul
│       │   ├── pom.xml
│       │   └── src/
│       │       ├── main/                # Quellcode
│       │       └── test/                # Tests
│       │
│       ├── storage/                     # Storage-Modul
│       │   ├── pom.xml
│       │   └── src/
│       │       ├── main/
│       │       └── test/
│       │
│       ├── documentation/               # Doku & Präsentationen
│       │   ├── postman/                 # Postman Collections
│       │   ├── presentations/           # Präsentationen
│       │   ├── Projektdokumentation_Task_Vault.pdf
│       │   └── UML_storage.drawio
│       │
│       ├── google_checks.xml            # Checkstyle-Regeln
│       └── README.md                    # Modul-README
│
├── .gitignore
└── README.md                            # Projekt-README
```
## Release Guideline

Dieser Workflow erstellt **Git Tag + GitHub Release** und **re-taggt** das bereits gebaute Image in der GHCR auf die Release-Version.

### 1) Voraussetzungen
- CI/CD ist grün (Build, Tests, Sonar).
- Image ist bereits in GHCR vorhanden (z. B. `latest` oder eine Run-Nummer).
- SemVer-Version wählen: `vMAJOR.MINOR.PATCH` (z. B. `v1.0.0`).

### 2) Release starten
1. **GitHub → Actions → “Taskvault Release Tagging” → Run workflow**
2. **Inputs:**
   - `version`: z. B. `v1.0.0` (**muss mit `v` beginnen**)
   - `source_image_tag` *(optional)*: Standard `latest`  
     (alternativ z. B. eine CI-Run-Nummer)
   - `target_ref` *(optional)*: Standard `main`

### 3) Was passiert?
- Annotated **Git Tag** `vX.Y.Z` wird erstellt & gepusht.
- **GitHub Release** erzeugt.
- GHCR-Image wird re-getaggt (vX.Y.Z, vX.Y, vX)

## Troubleshooting

| Problem | Ursache | Lösung |
| --------| ------- | ------ |
| Dockerfile not found (open Dockerfile: no such file or directory) | Build-Context war falsch gesetzt | context: ./java-project/taskvault und file: ./java-project/taskvault/Dockerfile im Workflow spezifiziert |
| Doppelte/mehrfache CI-Läufe (Push + PR + Merge gleichzeitig) | Trigger in on:-Sektion zu breit, kein concurrency | Pull Requests nur noch auf main → Push nur auf main, develop. Zusätzlich: concurrency.group eingeführt |
| tote Runs (alte Jobs laufen weiter nach neuem Push) | Kein Abbruch der Vorgänger-Runs | cancel-in-progress: true in allen Workflows gesetzt |
| GHCR Images überschrieben | Nur latest wurde getaggt | Tags um Run-Number + Short SHA erweitert |
| CD startet ohne erfolgreiches CI | fehlendes If im cd-image.yml | if: github.event.workflow_run.conclusion == 'success' hinzugefügt |