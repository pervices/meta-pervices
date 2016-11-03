#!/bin/bash

red='\e[1;31m'
normal='\e[0m'
green='\e[1;32m'
fpga="/lib/firmware/soc_system.rbf"
overlay="/lib/firmware/update.dtb"

echo "[        ] Verifying Source files..."
if [[ -f "$fpga" ]] && [[ -f "$overlay" ]]; then
	echo -e "[$green   OK   $normal] Verified source files"
elif [[ -f "$fpga" ]]; then
	echo -e "[$red FAILED $normal] device tree overlay is missing"
	exit 1
elif [[ -f "$overlay" ]]; then
	echo -e "[$red FAILED $normal] FPGA image is missing"
	exit 1
else
	echo -e "[$red FAILED $normal] Both FPGA image and device tree overlay are missing"
	exit 1
fi
echo "[        ] Verifying FPGA manager's status..."
grep -Fxq "operating" /sys/class/fpga_manager/fpga0/state
rc8=$?
if [[ $rc8 != 0 ]]; then
	echo -e "[$red FAILED $normal] FPGA manager is not operating"
	exit $rc8
else
	echo -e "[$green   OK   $normal] FPGA manager is operating"
fi
echo "[        ] Stopping server..."
systemctl stop crimson-server
rc0=$?
if [ $rc0 != 0 ]; then
	echo -e "[$red FAILED $normal] Failed to stop server, exiting..."
	exit $rc0
fi
echo -e "[$green   OK   $normal] Successfully stopped server..."
echo "[        ] Updating FPGA firmware..."
mkdir -p /config
mount -t configfs configfs /config
mkdir -p /config/device-tree/overlays/update
cd /lib/firmware
echo update.dtb > /config/device-tree/overlays/update/path
echo "[        ] Verifying component status"
grep -Fxq "applied" /config/device-tree/overlays/update/status
rc1=$?
grep -Fxq "enabled" /sys/class/fpga_bridge/br0/state
rc2=$?
grep -Fxq "enabled" /sys/class/fpga_bridge/br1/state
rc3=$?
grep -Fxq "enabled" /sys/class/fpga_bridge/br2/state
rc4=$?
grep -Fxq "enabled" /sys/class/fpga_bridge/br3/state
rc5=$?
grep -Fxq "operating" /sys/class/fpga_manager/fpga0/state
rc6=$?
if [[ $rc1 != 0 ]]; then
	echo -e "[$red FAILED $normal] Update FAILED, device tree overlay is not applied"
elif [[ $rc2 != 0 ]]; then
	echo -e "[$red FAILED $normal] Update FAILED, lwhps2fpga bridge is not enabled"
elif [[ $rc3 != 0 ]]; then
	echo -e "[$red FAILED $normal] Update FAILED, hps2fpga bridge is not enabled"
elif [[ $rc4 != 0 ]]; then
	echo -e "[$red FAILED $normal] Update FAILED, fpga2hps bridge is not enabled"
elif [[ $rc5 != 0 ]]; then
	echo -e "[$red FAILED $normal] Update FAILED, fpga2sdram bridge is not enabled"
elif [[ $rc6 != 0 ]]; then
	echo -e "[$red FAILED $normal] Update FAILED, FPGA manager is not operating"
elif [[ $rc1 != 0 || $rc2 != 0 || $rc3 != 0 || $rc4 != 0 || $rc5 != 0 || $rc6 != 0 ]]; then
	umount /config
	exit 1
else
	umount /config
	echo -e "[$green   OK   $normal] Updated FPGA firmware"
fi
echo "[        ] Restarting service..."
systemctl start crimson-website
rc7=$?
if [ $rc7 != 0 ]; then
	echo -e "[$red FAILED $normal] Failed to restart service, exiting..."
	exit $rc7
else
	echo -e "[$green   OK   $normal] Successfully restarted service"
fi
echo -e "[$green   OK   $normal] Update Successful"
