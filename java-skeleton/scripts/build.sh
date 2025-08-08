#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."

if command -v mvn >/dev/null 2>&1; then
  mvn -q -DskipTests package
  echo "Build complete: target directory contains artifacts"
else
  echo "Maven not found; using javac fallback"
  SRC_DIR="src/main/java"
  OUT_DIR="out"
  rm -rf "$OUT_DIR" && mkdir -p "$OUT_DIR"
  find "$SRC_DIR" -name '*.java' | xargs javac -encoding UTF-8 -d "$OUT_DIR"
  echo "Build complete: classes in $OUT_DIR"
fi