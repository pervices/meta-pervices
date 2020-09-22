UBOOT_REPO = "git://github.com/pervices/u-boot-socfpga.git"
UBOOT_BRANCH = "socfpga_v${PV}"
UBOOT_PROT = "https"
UBOOT_TAG = "${AUTOREV}"

SRC_URI = "${UBOOT_REPO};protocol=${UBOOT_PROT};branch=${UBOOT_BRANCH}"

FILESEXTRAPATHS_prepend := "${THISDIR}/u-boot-socfpga_2013.01.01:"
SRC_URI += "file://Enable-nand-command-by-default.patch \
           "
