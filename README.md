# SS25-DevOps TaskVault CI/CD
TaskVault ist ein Java-basiertes Projekt mit JPA/Hibernate, PostgreSQL und einer REST-API.
Dieses Repository enthält zusätzlich eine vollständige CI/CD-Pipeline auf GitHub Actions, inkl. Docker & GHCR Deployment.



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

## Docker Image (GHCR)

Das aktuellste API-Image ist immer im GitHub Container Registry verfügbar:

```docker pull ghcr.io/teilzeitalbaner/taskvault-api:latest```

Alternative Versionen (z. B. von CI Run Nummern) sind ebenfalls verfügbar.

## Projektstruktur

Bild einfügen

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
3. **Run** klicken.

### 3) Was passiert?
- Annotated **Git Tag** `vX.Y.Z` wird erstellt & gepusht.
- **GitHub Release** mit Release Notes wird erzeugt.
- GHCR-Image `ghcr.io/teilzeitalbaner/taskvault-api:<source>` wird **umgetaggt** nach:
  - `ghcr.io/teilzeitalbaner/taskvault-api:vX.Y.Z`
  - zusätzlich (optional): `vX` und `vX.Y` als Floating Tags

### 4) Ergebnis prüfen
- **Releases** Tab: `vX.Y.Z` vorhanden?
- **Packages (GHCR)**: neue Tags `vX.Y.Z`, `vX`, `vX.Y` sichtbar?
- Optional: `docker pull ghcr.io/teilzeitalbaner/taskvault-api:vX.Y.Z`

### 5) Rollback
- Einen **älteren Tag** (z. B. `v1.0.1`) erneut deployen oder den Release-Workflow mit einer älteren `version` & entsprechendem `source_image_tag` erneut ausführen.

### 6) Cron (optional)
Im Workflow ist ein auskommentierter **CRON-Trigger** enthalten (z. B. wöchentliche Releases). Bei Bedarf im YAML einkommentieren.

### Tipps
- **Major/Minor-Hinweise**:  
  - **Major** = breaking changes  
  - **Minor** = neue Features, abwärtskompatibel  
  - **Patch** = Bugfixes  
- **Fehler “Tag exists”**: Version erhöhen (z. B. `v1.0.1`).  
- **Private Repos**: GHCR funktioniert mit `GITHUB_TOKEN` im selben Repo standardmäßig.