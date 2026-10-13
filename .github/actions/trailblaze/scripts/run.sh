#!/usr/bin/env bash
# Runs inside the emulator step. Always exits 0 and leaves the verdict in a file, so the
# export, publish and summary steps still run after a failed trail.
set -uo pipefail
status=0
if [ -n "$INPUT_APP" ]; then
  adb install -r "$INPUT_APP" || status=$?
fi
if [ "$status" = 0 ]; then
  flags=()
  if [ "$CAPTURE_VIDEO" = true ]; then flags+=(--capture-video); fi
  # Both are lists, split on whitespace on purpose.
  read -r -d '' -a trails <<< "$INPUT_TRAILS" || true
  read -r -d '' -a args <<< "$INPUT_ARGS" || true
  trailblaze run "${trails[@]}" -d android/emulator-5554 "${args[@]}" "${flags[@]}" || status=$?
fi
echo "$status" > "$RUNNER_TEMP/trailblaze-exit"
exit 0
