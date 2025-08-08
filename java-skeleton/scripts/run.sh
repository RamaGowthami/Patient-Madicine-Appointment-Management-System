#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."

MAIN_CLASS="com.example.app.App"
ARTIFACT_JAR="target/app-1.0.0-SNAPSHOT.jar"

if [[ -f "$ARTIFACT_JAR" ]]; then
  exec java -jar "$ARTIFACT_JAR" "$@"
fi

if command -v mvn >/dev/null 2>&1; then
  mvn -q -DskipTests package
  exec java -jar "$ARTIFACT_JAR" "$@"
else
  echo "Maven not found; using javac fallback"
  if [[ ! -d out ]]; then
    ./scripts/build.sh
  fi
  exec java -cp out "$MAIN_CLASS" "$@"
fi