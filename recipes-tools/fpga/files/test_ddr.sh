#!/bin/bash
#This script will test DDR for RTM5
#It is to be run with hps_only image.

echo " Writing 0xDEADBEEF to all ddr locations"
mem mw 0x80000f20 0x08000000 #write res_rw2 -> start writing 0xdeadbeef in all DDR locations
mem mw 0x80000f20 0x0        #falling edge to start
sleep 1.5s
echo "Reading all ddr locations"
mem mw 0x80000f20 0x04000000 #write res_rw2 -> start reading 0xdeadbeef in all DDR locations
mem mw 0x80000f20 0x0        #falling edge to start
sleep 1.5s 

echo " "
echo " Writing its address to all ddr locations"
mem mw 0x80000f20 0x20000000 #write res_rw2 -> start writing ADDRESS in all DDR locations
mem mw 0x80000f20 0x0        #falling edge to start
sleep 1.5s
echo "Reading all ddr locations"
mem mw 0x80000f20 0x02000000 #write res_rw2 -> start reading ADDRESS in all DDR locations
mem mw 0x80000f20 0x0        #falling edge to start
sleep 1.5s

echo " "
echo " Writing the invert of its address to all ddr locations"
mem mw 0x80000f20 0x10000000 #write res_rw2 -> start writing invert ADDRESS in all DDR locations
mem mw 0x80000f20 0x0        #falling edge to start
sleep 1.5s
echo "Reading all ddr locations"
mem mw 0x80000f20 0x01000000 #write res_rw2 -> start reading invert ADDRESS in all DDR locations
mem mw 0x80000f20 0x0        #falling edge to start

sleep 1s
echo "Number of errors locations that were not = 0xdeadbeef:"
error_deadbeef_ddra=$(mem mr 0x8000012C) #reading Errors deadbeef DDRA
error_deadbeef_ddrb=$(mem mr 0x8000013C) #reading Errors deadbeef DDRB
echo "DDRA = $((error_deadbeef_ddra))           ; DDRB = $((error_deadbeef_ddrb))"

echo "Number of errors locations that were not = Address:"
error_addr_ddra=$(mem mr 0x80000124) #reading Errors address DDRA
error_addr_ddrb=$(mem mr 0x80000134) #reading Errors address DDRB
echo "DDRA = $(($error_addr_ddra))           ; DDRB = $(($error_addr_ddrb))"

echo "Number of errors locations that were not = invert(Address):"
error_invert_addr_ddra=$(mem mr 0x80000128) #reading Errors invert_address DDRA
error_invert_addr_ddrb=$(mem mr 0x80000138) #reading Errors invert_address DDRB
echo "DDRA = $(($error_invert_addr_ddra))           ; DDRB = $(($error_invert_addr_ddrb))"

error_deadbeef_fail_A=0
error_address_fail_A=0
error_invert_address_fail_A=0
if [ $(($error_deadbeef_ddra)) -gt 1 ] ; then
    error_deadbeef_fail_A=1
fi

if [ $(($error_addr_ddra)) -gt 1 ] ; then
    error_address_fail_A=1
fi

if [ $(($error_invert_addr_ddra)) -gt 1 ] ; then
    error_invert_address_fail_A=1
fi

error_deadbeef_fail_B=0
error_address_fail_B=0
error_invert_address_fail_B=0
if  [ $(($error_deadbeef_ddrb)) -gt 1 ] ; then
   error_deadbeef_fail_B=1
fi

if [ $(($error_addr_ddrb)) -gt 1 ] ; then
    error_address_fail_B=1
fi

if  [ $(($error_invert_addr_ddrb)) -gt 1 ] ; then
    error_invert_address_fail_B=1
fi

if [ $(($error_deadbeef_fail_A)) -eq 0 ] && [ $(($error_address_fail_A)) -eq 0 ] && [ $(($error_invert_address_fail_A)) == 0 ]; then
    echo "TEST HAS PASSED, DDR4 A is working."
else
    echo "TEST HAS FAILED, DDR4 A has a default"
fi

if [ $(($error_deadbeef_fail_B)) -eq 0 ] && [ $(($error_address_fail_B)) -eq 0 ] && [ $(($error_invert_address_fail_B)) == 0 ]; then
    echo "TEST HAS PASSED, DDR4 B is working."
else
    echo "TEST HAS FAILED, DDR4 B has a default"
fi
