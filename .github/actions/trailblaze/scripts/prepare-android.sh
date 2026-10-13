#!/usr/bin/env bash
# Lets the emulator use KVM, keeps it off Trailblaze's ports, and installs ffmpeg for video.
set -euo pipefail
if [ "$(uname -s)" != Linux ] || [ "$(uname -m)" != x86_64 ]; then
  echo "::error title=Trailblaze::The Android emulator needs an x86_64 Linux runner such as ubuntu-latest (arm64 runners have no KVM)."
  exit 1
fi
echo 'KERNEL=="kvm", GROUP="kvm", MODE="0666", OPTIONS+="static_node=kvm"' \
  | sudo tee /etc/udev/rules.d/99-kvm4all.rules > /dev/null
sudo udevadm control --reload-rules
sudo udevadm trigger --name-match=kvm
sudo sysctl -q -w net.ipv4.ip_local_reserved_ports=52525-52526
if [ "$CAPTURE_VIDEO" = true ] && ! command -v ffmpeg > /dev/null; then
  sudo apt-get update -q && sudo apt-get install -y -q ffmpeg > /dev/null
fi
