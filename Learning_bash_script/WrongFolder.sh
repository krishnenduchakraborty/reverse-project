#!/bin/bash

cd /data/data/come.termux/files/home/Learning_rust 2>/dev/null
if [ $? -eq 0 ]; then
    echo '[+]Entering to the directory!'
else
    echo "[+]Directory not found!"
fi
