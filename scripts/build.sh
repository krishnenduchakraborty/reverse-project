#!/usr/bin/env bash
set -euo pipefail
shopt -s inherit_errexit || true

# REPO_ROOT is the repository root where this script is checked out
# Prefer GITHUB_WORKSPACE when running on Actions; fallback to current directory
REPO_ROOT="${GITHUB_WORKSPACE:-$(pwd)}"

echo "Starting Zig Android aarch64 build script"
echo "REPO_ROOT=${REPO_ROOT}"
echo "ANDROID_NDK_HOME=${ANDROID_NDK_HOME:-<not-set>}"
echo "ANDROID_API_LEVEL=${ANDROID_API_LEVEL:-21}"
echo "INSTALL_PREFIX=${INSTALL_PREFIX:-$(pwd)/zig-install}"
WORKDIR="${REPO_ROOT}/work"
ZIG_REPO="https://github.com/ziglang/zig.git"
ZIG_DIR="$WORKDIR/zig-src"
LLVM_BUILD_DIR="$WORKDIR/llvm-build"

mkdir -p "$WORKDIR" "$INSTALL_PREFIX"

# Clone Zig (shallow)
if [ -d "$ZIG_DIR" ]; then
  echo "Using existing $ZIG_DIR"
else
  echo "Cloning Zig repo (shallow)"
  git clone --depth 1 "$ZIG_REPO" "$ZIG_DIR"
fi

# Ensure there's an llvm project for building LLVM (zig expects zig-llvm-project/llvm sometimes)
if [ ! -d "$ZIG_DIR/zig-llvm-project" ]; then
  echo "Cloning llvm-project into $ZIG_DIR/zig-llvm-project (shallow)"
  git clone --depth 1 https://github.com/llvm/llvm-project.git "$ZIG_DIR/zig-llvm-project"
fi

# Apply patch if present in the repo root
PATCH_PATH="$REPO_ROOT/patches/avoid-mrs.patch"
echo "Looking for patch at: $PATCH_PATH"
if [ -f "$PATCH_PATH" ]; then
  echo "Applying patch: $PATCH_PATH"
  cd "$ZIG_DIR"
  if git apply "$PATCH_PATH"; then
    echo "Patch applied successfully via git apply"
  else
    echo "git apply failed, attempting fallback python patcher"
    cd "$REPO_ROOT"
    python3 scripts/ci_patch_apply.py || { echo "fallback patcher failed"; exit 1; }
    echo "Fallback patcher succeeded"
  fi
  cd - >/dev/null
else
  echo "No patch found at $PATCH_PATH; continuing without patch"
  echo "Listing repository root files:"; ls -la "$REPO_ROOT" || true
fi

# Build LLVM+Clang for Android using NDK toolchain
mkdir -p "$LLVM_BUILD_DIR"
cd "$LLVM_BUILD_DIR"

# Ensure toolchain file exists
TOOLCHAIN_FILE="$ANDROID_NDK_HOME/build/cmake/android.toolchain.cmake"
if [ ! -f "$TOOLCHAIN_FILE" ]; then
  echo "ERROR: NDK toolchain file not found at $TOOLCHAIN_FILE"
  exit 1
fi

echo "Configuring LLVM (NDK CMake toolchain)"
cmake -G Ninja "$ZIG_DIR/zig-llvm-project/llvm" \
  -DCMAKE_BUILD_TYPE=Release \
  -DCMAKE_INSTALL_PREFIX="$INSTALL_PREFIX" \
  -DCMAKE_TOOLCHAIN_FILE="$TOOLCHAIN_FILE" \
  -DANDROID_ABI=arm64-v8a \
  -DANDROID_NATIVE_API_LEVEL="$ANDROID_API_LEVEL" \
  -DLLVM_ENABLE_PROJECTS="clang;lld;compiler-rt" \
  -DLLVM_TARGETS_TO_BUILD="AArch64" \
  -DLLVM_ENABLE_TERMINFO=OFF \
  -DLLVM_ENABLE_ZLIB=OFF

echo "Building LLVM (this may take a while)"
ninja -j$(nproc) install

# Build Zig using the just-built LLVM
cd "$ZIG_DIR"

# Put the NDK clang wrappers in PATH so Zig build can invoke aarch64-linux-android-clang if needed
export PATH="$ANDROID_NDK_HOME/toolchains/llvm/prebuilt/linux-x86_64/bin:$PATH"

echo "Running Zig's build script"
# Try Zig's build script. If it differs for the Zig version, logs will show required adjustments.
python3 ./build -Drelease-safe --prefix "$INSTALL_PREFIX" --llvm-root "$INSTALL_PREFIX" || {
  echo "Zig build script failed; attempting alternate cmake-based build"

  mkdir -p build.zig && cd build.zig
  cmake -G Ninja "$ZIG_DIR" \
    -DCMAKE_BUILD_TYPE=Release \
    -DCMAKE_INSTALL_PREFIX="$INSTALL_PREFIX" \
    -DLLVM_ROOT="$INSTALL_PREFIX"
  ninja -j$(nproc) install || {
    echo "Alternate Zig build failed. Check logs above for errors."
    exit 1
  }
}

echo "Build finished. Installed to $INSTALL_PREFIX"
ls -la "$INSTALL_PREFIX/bin" || true
