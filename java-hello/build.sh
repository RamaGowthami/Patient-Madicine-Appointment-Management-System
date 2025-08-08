#!/usr/bin/env bash
set -euo pipefail

# Always run from the script directory
cd "$(dirname "$0")"

SRC_DIR="src/main/java"
OUT_DIR="out"

rm -rf "$OUT_DIR"
mkdir -p "$OUT_DIR"

# Compile all Java files
javac -encoding UTF-8 -d "$OUT_DIR" $(find "$SRC_DIR" -type f -name "*.java")

echo "Build successful. Classes in $OUT_DIR"