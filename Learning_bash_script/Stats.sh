#!/bin/bash

my_system_status() {
    echo "---System Status---"
    echo "---Date of the day---"
    date
    echo "-------------------"
    echo "---Current directory---"
    pwd
    echo "-------------------"
    lsd -la
    cp Stats.sh /data/data/com.termux/files/home 
}
my_system_status
#sleep 2
#my_system_status
echo "Program finished!, bye"
