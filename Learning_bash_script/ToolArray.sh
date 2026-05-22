#!/bin/bash
set -e
my_tools() {
    tools=("radare2" "nmap" "bash" "python" "java" "c")
    echo "My first tool: ${tools[0]}"
    echo "Name of all tools: ${tools[@]}"
    echo "Total tools: ${#tools[@]}"

        for tool in "${tools[@]}"; do
            echo "I can run: $tool"
        done
}
my_tools
