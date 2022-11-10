When running the expand_parti_sd.sh file on the unit, you must be root (sudo -i).

After, just run expand_parti_sd.sh (sh expand_parti_sd.ah , bash expand_parti_sd.sh , etc)

Once the script it run, you must reboot the unit then running
resize2fs /dev/mmcblk0p2
to finish off the formating!

WARNING: this WILL change the partitioning size of the SD card. Please keep a backup in case something
unexpected happens.


