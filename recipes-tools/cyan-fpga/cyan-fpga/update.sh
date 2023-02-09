#!/bin/bash -e
# cyan_16t fpga update script
red="\e[1;31m"
normal="\e[0m"
green="\e[1;32m"
bold="\e[1m"
yellow="\e[1;33m"
ok="[$green   OK   $normal]"
failed="[$red FAILED $normal]"
warning="[$yellow WARNING$normal]"
fpga="/lib/firmware/soc_system.rpd"

case "$1" in
soft)
    echo -e "CYAN soft update currently unsupported"
    ;;
hard)
#   ./$0 soft
# when/if soft update supported, remove print asking user to reboot cyan
    echo -e "$warning DO NOT POWER OFF OR INTERRUPT CYAN DURING QSPI UPDATE"
    echo -e "This will take some time (up to 15 minutes)"
    if test -f "$fpga"; then

        echo -e "[        ] Erasing QSPI slot 1"
        rsu_client -e 1
        result=$?
        if [[ $result != 0 ]]; then
            echo -e "$failed Erase"
            exit $result
        else
            tput cuu1
            tput el
            tput cuu1
            tput el
            echo -e "$ok QSPI slot 1 erased"
        fi

        echo -e "[        ] Writing primary FPGA image to QSPI slot 1"
        rsu_client -s 1 -a $fpga
        result=$?
        if [[ $result != 0 ]]; then
            echo -e "$failed Write"
            exit $result
        else
            tput cuu1
            tput el
            tput cuu1
            tput el
            echo -e "$ok QSPI slot 1 written"
        fi

        echo -e "[        ] Setting QSPI priority"
        rsu_client -E 1
        result=$?
        if [[ $result != 0 ]]; then
            echo -e "$failed Setting QSPI priority"
            exit $result
        else
            tput cuu1
            tput el
            tput cuu1
            tput el
            echo -e "$ok QSPI priority set"
        fi
        echo -e "$ok FPGA hard update complete. Please wait until entire update is complete, then reboot CYAN to use new FPGA image."

    else
        echo -e "$failed to locate fpga rpd to flash to QSPI"
    fi
    ;;
*)
    echo -e "$bold Usage: ./update {soft|hard} $normal"
    echo -e "$bold Soft: Update the FPGA image immediately, will loss it after power off $normal"
    echo -e "$bold Hard: Similar to soft except that the FPGA imgae is updated permanently $normal"
    exit 1
    ;;
esac

exit 0
