#!/bin/bash

/bin/systemctl list-jobs | egrep -q 'reboot.target.*start' && i2cset -y 2 0x70 1 0xF7 || i2cset -y 2 0x70 1 0xFB

