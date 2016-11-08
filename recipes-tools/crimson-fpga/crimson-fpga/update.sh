#!/bin/bash -e

red='\e[1;31m'
normal='\e[0m'
green='\e[1;32m'
bold='\e[1m'
fpga="/lib/firmware/soc_system.rbf"
overlay="/lib/firmware/update.dtb"
version=$(tail -1 /etc/version/crimson-fpga| cut -d '-' -f2)
case "$1" in
soft)
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
	rc0=$?
	if [[ $rc0 != 0 ]]; then
		echo -e "[$red FAILED $normal] FPGA manager is not operating"
		exit $rc0
	else
		echo -e "[$green   OK   $normal] FPGA manager is operating"
	fi
	echo "[        ] Stopping server..."
	systemctl stop crimson-server
	rc1=$?
	if [[ $rc1 != 0 ]]; then
		echo -e "[$red FAILED $normal] Failed to stop server, exiting..."
		exit $rc1
	fi
	echo -e "[$green   OK   $normal] Successfully stopped server..."
	echo "[        ] Updating FPGA firmware..."
	mkdir -p /config
	mount -t configfs configfs /config
	if [[ -d /config/device-tree/overlays/update ]]; then
		rmdir /config/device-tree/overlays/update
	fi
	mkdir /config/device-tree/overlays/update
	cd /lib/firmware
	echo update.dtb > /config/device-tree/overlays/update/path
	echo "[        ] Verifying component status"
	grep -Fxq "applied" /config/device-tree/overlays/update/status
	rc2=$?
	grep -Fxq "enabled" /sys/class/fpga_bridge/br0/state
	rc3=$?
	grep -Fxq "enabled" /sys/class/fpga_bridge/br1/state
	rc4=$?
	grep -Fxq "enabled" /sys/class/fpga_bridge/br2/state
	rc5=$?
	grep -Fxq "enabled" /sys/class/fpga_bridge/br3/state
	rc6=$?
	grep -Fxq "operating" /sys/class/fpga_manager/fpga0/state
	rc7=$?
	if [[ $rc2 != 0 ]]; then
		echo -e "[$red FAILED $normal] Update FAILED, device tree overlay is not applied"
	elif [[ $rc3 != 0 ]]; then
		echo -e "[$red FAILED $normal] Update FAILED, lwhps2fpga bridge is not enabled"
	elif [[ $rc4 != 0 ]]; then
		echo -e "[$red FAILED $normal] Update FAILED, hps2fpga bridge is not enabled"
	elif [[ $rc5 != 0 ]]; then
		echo -e "[$red FAILED $normal] Update FAILED, fpga2hps bridge is not enabled"
	elif [[ $rc6 != 0 ]]; then
		echo -e "[$red FAILED $normal] Update FAILED, fpga2sdram bridge is not enabled"
	elif [[ $rc7 != 0 ]]; then
		echo -e "[$red FAILED $normal] Update FAILED, FPGA manager is not operating"
	elif [[ $rc2 != 0 || $rc3 != 0 || $rc4 != 0 || $rc5 != 0 || $rc6 != 0 || $rc7 != 0 ]]; then
		echo -e "[        ] Cleaning up..."
		umount /config
		rc8=$?
		if [[ $rc8 != 0 ]]; then
			echo -e "[$red FAILED $normal] Failed to unmount the configFS"
		else
			echo -e "[$green   OK   $normal] Done Cleaning up"
		fi
		exit 1
	else
		echo -e "[$green   OK   $normal] Updated FPGA firmware"
		echo -e "[        ] Cleaning up..."
		umount /config
		rc9=$?
		if [[ $rc9 != 0 ]]; then
			echo -e "[$red FAILED $normal] Failed to unmount the configFS"
		else
			echo -e "[$green   OK   $normal] Done Cleaning up"
		fi
	fi
	echo "temp-installed-${version}" >> /etc/version/crimson-fpga
	echo "[        ] Restarting service..."
	systemctl start crimson-website
	rc10=$?
	if [[ $rc10 != 0 ]]; then
		echo -e "[$red FAILED $normal] Failed to restart service, exiting..."
		exit $rc10
	else
		echo -e "[$green   OK   $normal] Successfully restarted service"
	fi
	sleep 10
	echo -e "[$green   OK   $normal] Soft update Successful"
	;;
hard)
	./$0 soft
	echo -e "[        ] Updating FPGA image on SD card"
	mkdir -p tmp
	rc11=$?
	mount /dev/mmcblk0p1 tmp
	rc12=$?
	cp tmp/soc_system.rbf soc_system_prev.rbf && sync
	rc13=$?
	cp soc_system.rbf tmp/ && sync && echo "$version" > tmp/curr_version && echo "installed-${version}" >> /etc/version/crimson-fpga
	rc14=$?
	umount tmp
	rc15=$?
	rm -rf tmp
	rc16=$?
	if [[ $rc11 != 0 ]]; then
		echo -e "[$red FAILED $normal] Failed to create tmp directory"
	elif [[ $rc12 != 0 ]]; then
		echo -e "[$red FAILED $normal] Failed to mount the SD card"
	elif [[ $rc13 != 0 ]]; then
		echo -e "[$red FAILED $normal] Failed to backup the old FPGA image"
	elif [[ $rc14 != 0 ]]; then
		echo -e "[$red FAILED $normal] Failed to update the new FPGA image on the SD card"
	elif [[ $rc15 != 0 ]]; then
		echo -e "[$red FAILED $normal] Failed to unmount the SD card"
	elif [[ $rc16 != 0 ]]; then
		echo -e "[$red FAILED $normal] Failed to remove the tmp directory"
	elif [[ $rc11 != 0 || $rc12 != 0 || $rc13 != 0 || $rc14 != 0 || $rc15 != 0 || $rc16 != 0 ]]; then
		exit 1
	else
		echo -e "[$green   OK   $normal] Updated FPGA image on SD card, hard update Successful"
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
