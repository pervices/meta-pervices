#!/bin/bash

for i in `seq 0 15`; do
    if [ ! -c "/dev/ttycyan-rfe-$i" ]; then
        ln -s /dev/ttycyan-rfe-4 /dev/ttycyan-rfe-$i
    fi
done
