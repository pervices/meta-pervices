FILESEXTRAPATHS_prepend := "${THISDIR}/u-boot-socfpga_v2017.09:"
##SRC_URI += "file://UBOOT-Enable-nand-in-uboot.patch \
##           "
#SRCREV = "ecab3e9091676588a2ab3178329cf91316073730"
SRCREV = "pv/socfpga_v2017.09"
SRC_URI = "git://github.com/pervices/u-boot-socfpga.git;branch=pv/socfpga_v2017.09;prot=https"

UBOOT_SUFFIX ?= "img"
do_deploy_append () {
	cd ${DEPLOYDIR}
	ln -sf u-boot-${type}-${PV}-${PR}.${UBOOT_SUFFIX} u-boot-dtb.img
}
