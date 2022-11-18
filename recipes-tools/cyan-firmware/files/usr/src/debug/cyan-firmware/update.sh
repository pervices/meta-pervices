#!/bin/bash -e
# Use this script to automatically compile and update server code
make clean
make
systemctl stop cyan-server.service
cp out/bin/* /usr/bin/
sync
systemctl start cyan-website.service


