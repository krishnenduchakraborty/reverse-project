import r2pipe
import json

def analyze_logic(file_path):
    r2 = r2pipe.open(file_path)
    r2.cmd("aa")
    
    # Function list neya
    functions = json.loads(r2.cmd("aflj"))
    print(f"--- Scanning Binary: {file_path} ---")
    
    for func in functions:
        name = func.get('name', 'unknown')
        # Sudhu amader target function gulo scan korbo
        if "main" in name or "sym." in name:
            disasm_raw = r2.cmd(f"pdfj @ {name}")
            if not disasm_raw:
                continue
            
            disasm = json.loads(disasm_raw)
            print(f"\n[+] Analyzing Function: {name}")
            
            # Ops list-e iterate kora
            for i, ins in enumerate(disasm.get('ops', [])):
                disasm_text = ins.get('disasm', '')
                # offset ba addr check kora
                addr = ins.get('offset') or ins.get('addr')
                
                if not addr or not disasm_text:
                    continue

                # Logic point khunje ber kora
                if "cmp" in disasm_text or "subs" in disasm_text:
                    print(f"    [*] Found Compare: {disasm_text} at {hex(addr)}")
                    
                    # Next instruction check kora (Branching)
                    if i + 1 < len(disasm['ops']):
                        next_ins = disasm['ops'][i+1]
                        next_disasm = next_ins.get('disasm', '')
                        next_addr = next_ins.get('offset') or next_ins.get('addr')
                        
                        if "b." in next_disasm or "j" in next_disasm:
                            print(f"    [!] Logic Bug Trigger Found: {next_disasm} at {hex(next_addr)}")
                            print(f"        Suggestion: Patch {hex(next_addr)} with NOP.")

    r2.quit()

analyze_logic("add your executable file, with it's path.")
