#!/bin/bash

systemctl stop crimson-server
mkdir fpga_image
mount /dev/mmcblk0p1 fpga_image
cp soc_system.rbf fpga_image/
sync
umount fpga_image
rm -r fpga_image
