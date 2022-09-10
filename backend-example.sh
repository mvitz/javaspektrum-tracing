#!/usr/bin/env bash
set -euo pipefail
IFS=$'\n\t'

echo "Creating new session"

session_id=$(curl \
  --no-progress-meter \
  -X POST localhost:9080/sessions)
#> ef86e474-e70c-4c16-8b65-104ca529eb5e

echo "Session created with ID: ${session_id}"

echo "Adding parameters to session"

curl \
  -X PUT localhost:9080/sessions/${session_id} \
  -H "Content-Type: application/json" \
  -d '{"a": "123", "b": "456"}'

curl \
  -X PUT localhost:9080/sessions/${session_id} \
  -H "Content-Type: application/json" \
  -d '{"a": "Michael", "c": "123"}'

echo "Transmitting session"

session=$(curl \
  --no-progress-meter \
  -X DELETE localhost:9080/sessions/${session_id})
#> {"a":"Michael","b":"456","c":"123"}
echo "Transmitted session ${session_id} with: ${session}"

