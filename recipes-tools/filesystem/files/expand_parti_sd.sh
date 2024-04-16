#!/bin/sh
# Expand rootfs 
# based on https://github.com/FabLabKannai/RaspiStudy/tree/master/2_install/raspi/expand_rootfs
# Per Vices Corp 2021
# Script adapted by; Victoria Sipinkarovski

#Use; 
# $ sudo bash expand_rootfs.sh
yellow="\e[1;33m"
normal='\033[0m'
bold="\e[1m"

get_init_sys() {
    if command -v systemctl > /dev/null && systemctl | grep -q '\-\.mount'; then
        SYSTEMD=1
        echo ${SYSTEMD}
    elif [ -f /etc/init.d/cron ] && [ ! -h /etc/init.d/cron ]; then
        SYSTEMD=0
        echo ${SYSTEMD}
    else
        echo "Unrecognised init system"
        return 1
    fi
}

do_expand_rootfs() {
    get_init_sys
    if [ $SYSTEMD -eq 1 ]; then
        # Getting the root partition
        ROOT_PARTI=$(mount | sed -n 's|^/dev/\(.*\) on / .*|\1|p')
        echo "root_parti is ${ROOT_PARTI}"
    else
        if ! [ -h /dev/root/ ]; then
            whiptail --msgbox "$ROOT_PARTI is not a SD Card. Do not know how to expand" 20 60 2
            return 0
        fi
        ROOT_PARTI=$(readlink /dev/root)
    fi

#NOTE-has sda values change when putting on unit
    #This assumes that your root partition would always be mmcblk0
    PARTI_NUM=${ROOT_PARTI#mmcblk0p}
    #PARTI_NUM=${ROOT_PARTI#sda}
    echo "parti_num is: ${PARTI_NUM}"
    if [ "$PARTI_NUM" = "$ROOT_PARTI" ]; then
        whiptail --msgbox "$ROOT_PARTI is not a SD card. Do not know how to expand" 20 60 2
        return 0
    fi

#Don't need to worry about NOOBS Partition layout
    
#Get the last parition number - will need parted function, make sure this is installed on the unit
#a sanity check
    #LAST_PART_NUM=$(sudo parted /dev/sda -ms unit s p | tail -n 1 | cut -f 1 -d:)
    LAST_PART_NUM=$(parted /dev/mmcblk0 -ms unit s p | tail -n 1 | cut -f 1 -d:)
    echo "LAST_PART_NUM is ${LAST_PART_NUM}"
    if [ $LAST_PART_NUM -ne $PARTI_NUM ]; then
        whiptail --msgbox "$ROOT_PARTI is not the last parition. Do not know how to expand" 20 60 2

        return 0
    fi
    
# Get the starting offset of the root partition 
    #PARTI_START=$(sudo parted /dev/sda -ms unit s p | grep "^${PARTI_NUM}" | cut -f 2 -d: | sed 's/[^0-9]//g')
    PARTI_START=$(parted /dev/mmcblk0 -ms unit s p | grep "^${PARTI_NUM}" | cut -f 2 -d: | sed 's/[^0-9]//g')
    [ "$PARTI_START" ] || return 1
    echo "parti+start is: ${PARTI_START}"
    
#Get the end of the sd card space --------------------------------------------------------
#NOTE Might not need, can use default values
    #PARTI_TOTAL_SIZE=$(sudo parted /dev/sde -ms unit s p | head -n 2 | tail -n 1 | cut -f 2 -d: | sed 's/[^0-9]//g')
    PARTI_TOTAL_SIZE=$(parted /dev/mmcblk0 -ms unit s p | head -n 2 | tail -n 1 | cut -f 2 -d: | sed 's/[^0-9]//g')
    PARTI_TOTAL_SIZE=( ${PARTI_TOTAL_SIZE} - 1 )
    echo "PARTI_TOTAL_SIZE is: ${PARTI_TOTAL_SIZE}"

#sudo fdisk /dev/sde <<EOF
fdisk /dev/mmcblk0 <<EOF

p
d
$PARTI_NUM
n
p
$PARTI_NUM
$PARTI_START



p
w
EOF

    
    
}

seconds=5
echo -e "[${yellow}WARNING${normal}] This program will alter the existing partition table. Hit ${bold}Ctrl+C${normal} to exit if you do not wish to continue."
echo -e ""
while [ $seconds -ge 0 ]; do
    tput cuu1
    tput el
    echo -e "Program will start in $seconds seconds..."
    seconds=$((seconds-1))
    sleep 1
done
tput cuu1
tput el
echo -e "Starting program..."

do_expand_rootfs > /dev/null 2>&1
echo "Done"
echo "Please reboot and run resize2fs /dev/mmcblk0p$PARTI_NUM as root"
