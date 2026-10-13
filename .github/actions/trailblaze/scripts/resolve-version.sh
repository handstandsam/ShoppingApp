#!/usr/bin/env bash
# Picks the CLI: a build artifact from this run, else the `version` input, else this action's own
# tag (v2026.10.06 -> 2026.10.06).
set -euo pipefail
if [ -n "${CLI_ARTIFACT:-}" ]; then
  echo "version=artifact" >> "$GITHUB_OUTPUT"
  echo "cli-dir=$RUNNER_TEMP/trailblaze-cli" >> "$GITHUB_OUTPUT"
  echo "Trailblaze from artifact $CLI_ARTIFACT"
  exit 0
fi
version="${INPUT_VERSION:-}"
if [ -z "$version" ] && [[ "${ACTION_REF:-}" =~ ^v?[0-9]{4}\.[0-9]{2}\.[0-9]{2}(\.[0-9]+)?$ ]]; then
  version="$ACTION_REF"
fi
version="${version#v}"
if [ -z "$version" ]; then
  echo "::error title=Trailblaze::Set the action's \`version\` input, or use it from a release tag such as block/trailblaze/github-action@v2026.10.06."
  exit 1
fi
echo "version=$version" >> "$GITHUB_OUTPUT"
echo "cli-dir=$RUNNER_TOOL_CACHE/trailblaze/$version" >> "$GITHUB_OUTPUT"
echo "Trailblaze $version"
