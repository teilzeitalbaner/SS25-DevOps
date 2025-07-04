# Taskvault – SS25-DevOps CI/CD Pipeline

Dieses Repository enthält das Java-Projekt **Taskvault** sowie alle zugehörigen CI/CD-Konfigurationen im Rahmen des Moduls Development Operations - SoSe 25.

---

## **Projektüberblick**

- **Technologien:** Java 17, Maven, GitHub Actions, Self-hosted Runner
- **Build-System:** Multi-Modul-Maven-Projekt (`api` und `storage`)
- **CI/CD:** Workflows orientieren sich an den Vorgaben und Beispielen der Vorlesung, angepasst auf ein Java-Projekt

---

## **CI/CD-Workflow**

Der aktuelle Workflow **(siehe [.github/workflows/ci-build.yml])** automatisiert folgende Schritte:

1. **Checkout** des Repository-Codes
2. **Build** und **Test** aller Module im Container (`maven:3.9.7-eclipse-temurin-17`)
3. **Bereitstellung von Artefakten** (JARs und Test-Reports) für beide Module
4. **Trigger:** bei jedem Push und Pull-Request auf `main`, `develop` und allen `feature/*`-Branches

---

## **Checkliste: Erfüllte Anforderungen**

- [x] **Build & Test im Container (Maven, Java 17)**
- [x] **Automatische Ausführung aller Unit-Tests**
- [x] **Bereitstellung und Upload von Build-Artefakten (JARs)**
- [x] **Upload von Test-Reports für beide Module**
- [x] **Self-hosted Runner eingerichtet und genutzt**
- [x] **Workflow triggert auf allen relevanten Branches (main, develop, feature/*)**
- [x] **Branch-Strategie nach Best Practice: main, develop, feature/\***  
- [x] **Pipeline-Status & Artefakte im Actions-Tab sichtbar**
- [x] **Projektstruktur und CI/CD-Logik an Vorlesungsbeispiele angepasst**
- [x] **Integration von Linting (z.B. mit Maven Checkstyle Plugin)**

---

## **Wichtige Dateien & Verzeichnisse**

- `.github/workflows/ci-build.yml`  
  **→ zentrale Pipeline-Definition (Build, Test, Artefakte)**
- `java-project/taskvault/`  
  **→ Multi-Modul-Java-Projekt**
    - `api/target/taskvault-api.jar`
    - `api/target/surefire-reports/`
    - `storage/target/storage-1.0-SNAPSHOT.jar`
    - `storage/target/surefire-reports/`

---

## **Wie läuft die Pipeline ab?**

1. **Build & Test:**  
   Bei jedem Push/PR wird das Projekt im Container gebaut und getestet.

2. **Artefakte:**  
   JARs und Test-Reports werden als Downloads im Actions-Tab bereitgestellt.

3. **Status:**  
   Erfolge und Fehler sind im GitHub Actions-Tab sofort sichtbar.

---

## **Nächste Schritte (geplant / optional):**
- [ ] Coverage-Reporting (z.B. mit JaCoCo)
- [ ] Erweiterung auf Docker-Builds/Compose
- [ ] Deployments, Kubernetes, weitere CI/CD-Features

---

