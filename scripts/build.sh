#!/usr/bin/env bash
set -euo pipefail
set -x
shopt -s inherit_errexit || true

# REPO_ROOT is the repository root where this script is checked out
# Prefer GITHUB_WORKSPACE when running on Actions; fallback to current directory
REPO_ROOT="${GITHUB_WORKSPACE:-$(pwd)}"

echo "Starting Zig Android aarch64 build script"
echo "REPO_ROOT=${REPO_ROOT}"
echo "ANDROID_NDK_HOME=${ANDROID_NDK_HOME:-<not-set>}"
ANDROID_API_LEVEL="${ANDROID_API_LEVEL:-21}"
INSTALL_PREFIX="${INSTALL_PREFIX:-$(pwd)/zig-install}"
echo "ANDROID_API_LEVEL=${ANDROID_API_LEVEL}"
echo "INSTALL_PREFIX=${INSTALL_PREFIX}"
WORKDIR="${REPO_ROOT}/work"
ZIG_REPO="https://github.com/ziglang/zig.git"
ZIG_DIR="$WORKDIR/zig-src"
LLVM_BUILD_DIR="$WORKDIR/llvm-build"

# If PREBUILT_LLVM_ROOT is set, use it instead of building LLVM from source.
PREBUILT_LLVM_ROOT="${PREBUILT_LLVM_ROOT:-}"
if [ -n "$PREBUILT_LLVM_ROOT" ]; then
  echo "PREBUILT_LLVM_ROOT is set: $PREBUILT_LLVM_ROOT"
  echo "Will skip building LLVM from source and use prebuilt LLVM for Zig build."
fi

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

# If a prebuilt LLVM root is provided, skip building LLVM and use that instead.
if [ -n "$PREBUILT_LLVM_ROOT" ]; then
  echo "Skipping LLVM build; using prebuilt LLVM at $PREBUILT_LLVM_ROOT"
else
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
    -DLLVM_ENABLE_PROJECTS="clang;lld" \
    -DLLVM_TARGETS_TO_BUILD="AArch64" \
    -DLLVM_ENABLE_TERMINFO=OFF \
    -DLLVM_ENABLE_ZLIB=OFF \
    -DLLVM_USE_LINKER=lld \
    -DCLANG_ENABLE_STATIC_ANALYZER=OFF \
    -DCLANG_PLUGIN_SUPPORT=OFF \
    -DCLANG_TOOLS_EXTRA_BUILD=OFF \
    -DLLVM_BUILD_TOOLS=OFF \
    -DLLVM_BUILD_EXAMPLES=OFF \
    -DLLVM_INCLUDE_EXAMPLES=OFF \
    -DLLVM_INCLUDE_TESTS=OFF \
    -DLLVM_BUILD_LLVM_DYLIB=ON \
    -DLLVM_LINK_LLVM_DYLIB=ON \
    -DLLVM_ENABLE_RTTI=ON \
    -DCLANG_BUILD_TOOLS=OFF \
    -DCMAKE_VERBOSE_MAKEFILE=ON

  echo "Building LLVM (this may take a while)"
  ninja -j$(nproc) install
fi

# Build Zig using the just-built or prebuilt LLVM
cd "$ZIG_DIR"

# If ANDROID_NDK_HOME is set, put the NDK clang wrappers in PATH so Zig build can invoke aarch64-linux-android-clang if needed
if [ -n "${ANDROID_NDK_HOME:-}" ]; then
  export PATH="$ANDROID_NDK_HOME/toolchains/llvm/prebuilt/linux-x86_64/bin:$PATH"
fi

# If PREBUILT_LLVM_ROOT is set, prefer its bin directory so cmake/find_package and llvm-config
# resolve to the prebuilt LLVM we downloaded instead of the NDK's llvm-config.
if [ -n "${PREBUILT_LLVM_ROOT:-}" ]; then
  export PATH="$PREBUILT_LLVM_ROOT/bin:$PATH"
fi

# If PREBUILT_LLVM_ROOT is provided, point Zig build to it. Otherwise use INSTALL_PREFIX where we installed LLVM
if [ -n "$PREBUILT_LLVM_ROOT" ]; then
  echo "Running Zig's build script using prebuilt LLVM at $PREBUILT_LLVM_ROOT"
  # Try common build script names (./build, ./build.py, ./build.sh)
  run_zig_build() {
    local llvm_root_arg="$1"
    for b in ./build ./build.py ./build.sh; do
      if [ -f "$b" ]; then
        echo "Found Zig build script: $b"
        # If executable, run it (honor shebang). Otherwise invoke it with python3.
        if [ -x "$b" ]; then
          "$b" -Drelease-safe --prefix "$INSTALL_PREFIX" --llvm-root "${llvm_root_arg}" && return 0 || {
            echo "Zig build script $b failed; exiting"
            return 1
          }
        else
          python3 "$b" -Drelease-safe --prefix "$INSTALL_PREFIX" --llvm-root "${llvm_root_arg}" && return 0 || {
            echo "Zig build script $b failed; exiting"
            return 1
          }
        fi
      fi
    done
    echo "No Zig build script found in $PWD"
    ls -la . || true
    return 2
  }
  set +e
  run_zig_build "$PREBUILT_LLVM_ROOT"
  rc=$?
  set -e
  if [ "$rc" -ne 0 ]; then
    if [ "$rc" -eq 2 ]; then
      echo "No Zig build script found; attempting cmake-based build fallback"
      # Ensure a clean out-of-source build directory to avoid cached llvm-config from previous runs
      rm -rf build.zig && mkdir -p build.zig && cd build.zig
      # Prefer prebuilt LLVM binaries while keeping NDK clang wrappers available
      export PATH="$PREBUILT_LLVM_ROOT/bin:$ANDROID_NDK_HOME/toolchains/llvm/prebuilt/linux-x86_64/bin:$PATH"
      cmake -G Ninja "$ZIG_DIR" \
        -DCMAKE_BUILD_TYPE=Release \
        -DCMAKE_INSTALL_PREFIX="$INSTALL_PREFIX" \
        -DLLVM_ROOT="$PREBUILT_LLVM_ROOT" \
        -DLLVM_CONFIG_EXECUTABLE="${LLVM_CONFIG_EXECUTABLE:-${PREBUILT_LLVM_ROOT}/bin/llvm-config}" \
        -DCLANG_INCLUDE_DIRS="${CLANG_INCLUDE_DIRS:-/usr/lib/llvm-21/include}" \
        -DLLD_INCLUDE_DIRS="${LLD_INCLUDE_DIRS:-/usr/lib/llvm-21/include}"
      ninja -j$(nproc) install || {
        echo "Alternate Zig cmake build failed. Check logs above for errors."
        exit 1
      }
    else
      echo "Zig build script failed; aborting"
      exit 1
    fi
  fi

else
  echo "Running Zig's build script using built LLVM at $INSTALL_PREFIX"
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
fi

echo "Build finished. Installed to $INSTALL_PREFIX"
ls -la "$INSTALL_PREFIX/bin" || true
