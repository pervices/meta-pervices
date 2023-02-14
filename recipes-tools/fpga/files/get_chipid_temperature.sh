#!/bin/bash
#This script will test DDR for RTM5
#It is to be run with hps_only image.

echo " Starting Sequence"
mem rw res_rw7 0x01000000
mem rw res_rw7 0x0        #falling edge to start

sleep 1.5s

echo "Reading Errors"
errors_reg=$(mem rr sys1) #reading Errors
if [ $(($errors_reg)) -ne 0 ] ; then
    echo "Something went wrong, error reg = $errors_reg  . Exiting"
    exit 1
else
    echo "No errors"
fi


echo "Reading temperature"
temperature_reg=$(mem rr sys14)
#temperature_reg=0x00002960
decimals=$(($temperature_reg & 0xff))
max_dec=256
temp_dec=$(echo "scale=2; $decimals / $max_dec" | bc)
#echo "rdb temp dec = $temp_dec"

integer=$(($temperature_reg >> 8))
#echo "rdb temp integer = $integer"
if [ $integer -gt 500 ]; then
    echo "Error reading the temperature reg (invalid location, see Mailbox Client UserGuide"
    echo "Exiting"
    exit 6
else
    echo "temperature_reg = $temperature_reg           ; temperature = $integer$temp_dec C"
fi

echo "Reading Chip ID"
chip_id_l=$(mem rr sys16)
chip_id_m=$(mem rr sys17)
echo "Chip ID = $chip_id_m$chip_id_l"
