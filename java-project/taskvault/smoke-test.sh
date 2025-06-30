#!/usr/bin/env bash
set -e

echo "→ Health:"
curl -i http://localhost:8080/api/health
echo

echo "→ Kategorien (initial):"
curl -i http://localhost:8080/api/categories
echo

echo "→ MainTasks (initial):"
curl -i http://localhost:8080/api/maintasks
echo

echo "→ MainTask anlegen mit allen Pflichtfeldern:"
curl -i -X POST http://localhost:8080/api/maintasks \
     -H "Content-Type: application/json" \
     -d '{"taskName":"SmokeTest","taskDescription":"Nur ein Test","category":"General","creationDate":"30/06/2025","dueDate":"30/06/2025","priority":"LOW_IMPORTANCE","subTasks":[],"dailyTask":false,"completed":false}'
echo

echo "→ MainTasks (nach Anlage):"
curl -i http://localhost:8080/api/maintasks
echo

