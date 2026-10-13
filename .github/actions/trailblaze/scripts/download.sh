#!/usr/bin/env bash
# Downloads the release CLI straight from GitHub Releases (no Homebrew), unless the cache restored it.
set -euo pipefail
if [ ! -x "$CLI_DIR/trailblaze" ] || [ ! -f "$CLI_DIR/trailblaze.jar" ]; then
  mkdir -p "$CLI_DIR"
  gh release download "v$VERSION" --repo block/trailblaze -p trailblaze -p trailblaze.jar -D "$CLI_DIR" --clobber
  chmod +x "$CLI_DIR/trailblaze"
fi
echo "$CLI_DIR" >> "$GITHUB_PATH"
