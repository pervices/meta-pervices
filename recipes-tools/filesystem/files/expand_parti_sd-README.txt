When running the expand_parti_sd.sh script on Crimson or Cyan, you must be in root (sudo -i).

Once the script finished, you must reboot the unit then running
For Cyan
resize2fs /dev/mmcblk0p2
For Crimson
resize2fs /dev/mmcblk0p3
to finish off the formating!

WARNING: this WILL change the partitioning size of the SD card. Please keep a backup in case something
unexpected happens.
