git_automation() {
    echo "[+]Navigating to the directory...!"
    cd /data/data/com.termux/files/home/reverse-project
    echo "[+]Printing directory path"
    pwd
    echo "[+]Listing files and directories!"
    ls
    echo "[+]Waiting for two seconds..!"
    sleep 2 
    echo "[+]Pulling origin main and rebasing!"
    git pull --rebase origin main
    echo "[+]Adding all files!"
    git add .
    echo "[+]Adding commit message!"
    git commit -m 'Updating files!'
    echo "[+]Pushing to the, origin main!"
    git push origin main
}
git_automation
