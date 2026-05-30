#!/bin/bash
set -e
git_pull_origin_main() {
    cd /data/data/com.termux/files/home/reverse-project
    echo "[+]Pulling origin main and rebasing!"
    git pull --rebase origin main
    sleep 5
}
    git_pull_origin_main

git_automation() {
    echo "[+]Navigating to the directory...!"
    cd /data/data/com.termux/files/home/reverse-project
    echo "[+]Printing directory path!"
    pwd
    echo "[+]Listing files and directories!"
    lsd
    echo "[+]Waiting for five seconds..!"
    sleep 5
    echo "[+]Adding all files!"
    git add .
    echo "[+]Adding commit message!"
    git commit -m 'Update, code formatting!'
    echo "[+]Pushing to the, origin main!"
    git push origin main
    echo "[+]Copy done at $(date)" >> /data/data/com.termux/files/home/git_log.txt
}
    git_automation
