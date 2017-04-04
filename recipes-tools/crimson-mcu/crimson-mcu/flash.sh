#!/bin/bash
version=$(tail -1 /etc/crimson/crimson-mcu | cut -d '-' -f2)

if [ $# -lt 1 ]
then
	echo "Usage : $0 rx | tx | synth | all"
	exit
fi

if [ "$1" != 'synth' ] && [ "$1" != 'tx' ] && [ "$1" != 'rx' ] && [ "$1" != 'all' ]
then
	echo "- invalid argument"
	echo "- Usage : $0 rx | tx | synth | all"	
	exit
fi


if [ "$1" = 'synth' ] || [ "$1" = 'all' ]
then
	echo "- Programming the synth board MCU..."
	fpgaprogpin=$(mem rr res_rw3 | head -c 7 | tail -c 1)
	fpgareg=$(mem rr res_rw3)
	state=0
	if [[ $fpgaprogpin != 0 ]]; then
		mem rw res_rw3 0 0x1000
		state=1
	fi
	echo -e "boot -e\r" > /dev/ttycrimson-time
	usleep 6050000
	echo -e "abcdefghijklmnopqrstuvwxyz\r" > /dev/ttycrimson-time
	avrdude -c avr109 -B 8 -p x256a3u -P /dev/ttycrimson-time -b 115200 -e -U flash:w:synth.hex
	echo -e "exit\r" > /dev/ttycrimson-time
	echo "- Done programming."
	echo "installed-${version}-synth" >> /etc/crimson/crimson-mcu
	if [[ $state != 0 ]]; then
		mem rw res_rw3 $fpgareg 0xffffffff
	fi
	usleep 300000
fi

if [ "$1" = 'rx' ] || [ "$1" = 'all' ]
then
	echo "- Programming the rx board MCU..."
	fpgaprogpin=$(mem rr res_rw3 | head -c 7 | tail -c 1)
	fpgareg=$(mem rr res_rw3)
	state=0
	if [[ $fpgaprogpin != 0 ]]; then
		mem rw res_rw3 0 0x1000
		state=1
	fi
	echo -e "boot -e\r" > /dev/ttycrimson-rx
	usleep 6050000
	echo -e "abcdefghijklmnopqrstuvwxyz\r" > /dev/ttycrimson-rx
	avrdude -c avr109 -B 8 -p x256a3u -P /dev/ttycrimson-rx -b 115200 -e -U flash:w:rx.hex
	echo -e "exit\r" > /dev/ttycrimson-rx
	echo "- Done programming."
	echo "installed-${version}-rx" >> /etc/crimson/crimson-mcu
	if [[ $state != 0 ]]; then
		mem rw res_rw3 $fpgareg 0xffffffff
	fi
	usleep 300000
fi

if [ "$1" = 'tx' ] || [ "$1" = 'all' ]
then
	echo "- Programming the tx board MCU..."
	fpgaprogpin=$(mem rr res_rw3 | head -c 7 | tail -c 1)
	fpgareg=$(mem rr res_rw3)
	state=0
	if [[ $fpgaprogpin != 0 ]]; then
		mem rw res_rw3 0 0x1000
		state=1
	fi
	echo -e "boot -e\r" > /dev/ttycrimson-tx
	usleep 6010000
	echo -e "abcdefghijklmnopqrstuvwxyz\r" > /dev/ttycrimson-tx
	avrdude -c avr109 -B 8 -p x256a3u -P /dev/ttycrimson-tx -b 115200 -e -U flash:w:tx.hex
	echo -e "exit\r" > /dev/ttycrimson-tx
	echo "- Done programming."
	echo "installed-${version}-rx" >> /etc/crimson/crimson-mcu
	if [[ $state != 0 ]]; then
		mem rw res_rw3 $fpgareg 0xffffffff
	fi
	usleep 300000
fi
