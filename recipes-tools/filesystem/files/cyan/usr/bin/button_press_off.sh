#!/bin/bash

#Setting the STATUS0,1,2 pins to active low (0s, as outputs)
i2cset -y 2 0x70 1 0xFF
i2cset -y 2 0x70 3 0xB3

#Checking if button been pressed cont.
while true; do
	
	temp=$(i2cget -y 2 0x70 0) > /dev/null
	
	#Checking if there is a hex value
	if [[ $temp =~ ^[0-9a-fA-F]  ]]; then

		if [[ $((temp%2)) -eq 0  ]] ; then

			#Asserting STATUS0 and INT pin (through Register 1)
			#i2cset -y 2 0x70 1 0xFB

			systemctl poweroff
		else
			:
		fi

	#If there is a error, will print out the following
	else
		echo "Error, i2c command failed"
	fi
	
	sleep 0.100
done
