#!/bin/bash 
find /var/volatile/chestnut/state -type f -printf '\n\n%p\n' -exec cat {}  \;