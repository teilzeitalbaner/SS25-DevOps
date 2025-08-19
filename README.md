# SS25-DevOps TaskVault CI/CD
## Commit-Konventionen (Kurz)

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