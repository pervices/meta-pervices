#!/bin/bash

# gracefully powerdown all RFEs
for BOARD in 0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15
do
	rfe_control $BOARD off n
done

# send reboot or poweroff command to power board
/bin/systemctl list-jobs | egrep -q 'reboot.target.*start' && i2cset -y 2 0x70 1 0xF7 || i2cset -y 2 0x70 1 0xFB

# kill all open ssh sessions
pkill sshd