#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."

if command -v mvn >/dev/null 2>&1; then
  mvn -q test
else
  echo "Maven not found; test execution requires Maven. Install Maven or use your IDE's test runner."
fi