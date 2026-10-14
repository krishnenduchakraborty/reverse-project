#!/usr/bin/env python3
"""
Simple in-place patcher for Zig's lib/std/zig/system/linux.zig
Replaces the getAArch64CpuFeature function with an Android-safe variant.
"""
import sys
from pathlib import Path

repo_root = Path('.')
zig_file = repo_root / 'work' / 'zig-src' / 'lib' / 'std' / 'zig' / 'system' / 'linux.zig'
if not zig_file.exists():
    print(f"ERROR: target file not found: {zig_file}")
    sys.exit(2)

text = zig_file.read_text()
old_sig = 'inline fn getAArch64CpuFeature(comptime feat_reg: []const u8) u64 {'
if old_sig not in text:
    print('ERROR: function signature not found; aborting')
    sys.exit(3)

start = text.find(old_sig)
# Find the matching closing brace for the function by simple brace counting
i = start
brace_count = 0
in_func = False
while i < len(text):
    c = text[i]
    if text[i:i+len(old_sig)] == old_sig:
        in_func = True
        i += len(old_sig)
        brace_count = 1
        continue
    if in_func:
        if c == '{':
            brace_count += 1
        elif c == '}':
            brace_count -= 1
            if brace_count == 0:
                end = i+1
                break
    i += 1
else:
    print('ERROR: could not find end of function')
    sys.exit(4)

new_func = '''inline fn getAArch64CpuFeature(comptime feat_reg: []const u8) u64 {
    // On Android user-mode (EL0) executing MRS to read system registers is privileged
    // and will SIGILL. Avoid inline asm on Android and return 0 so detection falls
    // back to parsing /proc/cpuinfo or conservative defaults.
    if (builtin.os == .android) {
        return 0;
    } else {
        return asm ("mrs %[ret], " ++ feat_reg
            : [ret] "=r" (-> u64),
        );
    }
}
'''

new_text = text[:start] + new_func + text[end:]
zig_file.write_text(new_text)
print('Patched', zig_file)
sys.exit(0)
