#!/usr/bin/env bash
set -euo pipefail

# Always run from the script directory
cd "$(dirname "$0")"

if [[ ! -d out ]]; then
  ./build.sh
fi

exec java -cp out com.example.app.Main "$@"