#!/bin/bash -e
# Use this script to automatically compile and update server code
make clean
make
systemctl stop crimson-server.service
cp out/bin/* /usr/bin/
sync
systemctl start crimson-website.service


