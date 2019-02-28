FILESEXTRAPATHS_prepend := "${THISDIR}/u-boot-socfpga_v2017.09:"
SRC_URI += "file://UBOOT-Enable-nand-in-uboot.patch \
           "
UBOOT_SUFFIX ?= "img"
do_deploy_append () {
	cd ${DEPLOYDIR}
	ln -sf u-boot-${type}-${PV}-${PR}.${UBOOT_SUFFIX} u-boot-dtb.img
}
