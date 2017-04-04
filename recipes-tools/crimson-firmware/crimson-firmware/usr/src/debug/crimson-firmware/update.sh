#!/bin/bash -e
# Use this script to automatically compile and update server code
systemctl stop crimson-server.service
make clean
make
cp out/bin/* /usr/bin/
sync
systemctl start crimson-website.service


