#!/bin/bash 
find /var/volatile/crimson/state -type f -printf '\n\n%p\n' -exec cat {}  \;