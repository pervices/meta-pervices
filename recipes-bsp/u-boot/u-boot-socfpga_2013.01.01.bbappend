FILESEXTRAPATHS_prepend := "${THISDIR}/u-boot-socfpga_2013.01.01:"
SRC_URI += "file://Enable-nand-command-by-default.patch \
            file://U-BOOT-Boot-from-NAND-flash.patch \
           "
