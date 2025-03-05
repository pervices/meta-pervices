#!/bin/bash 
find /var/volatile/cyan/state -type f -printf '\n\n%p\n' -exec cat {}  \;