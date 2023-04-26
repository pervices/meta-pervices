FILESEXTRAPATHS_prepend := "${THISDIR}/u-boot-socfpga_v2017.09:"
##SRC_URI += "file://UBOOT-Enable-nand-in-uboot.patch \
##           "
#SRCREV = "ecab3e9091676588a2ab3178329cf91316073730"
SRCREV = "pv/socfpga_v2017.09"
SRC_URI = "git://github.com/pervices/u-boot-socfpga.git;branch=pv/socfpga_v2017.09;protocol=https"
SRC_URI += "file://u-boot.cmd"
DEPENDS = "u-boot-mkimage-native"
FILES_${PN} += "/boot"
UBOOT_SUFFIX ?= "img"

do_compile_append() {
	uboot-mkimage -A arm64 -O linux -T script -C none -a 0 -e 0 -d ${WORKDIR}/u-boot.cmd u-boot.scr
}
do_install_append() {
	install -d ${D}/boot/u-boot-scripts
	install -m 0644 u-boot.scr ${D}/boot/u-boot-scripts
}
do_deploy_append () {
	cp ${D}/boot/u-boot-scripts/u-boot.scr ${DEPLOYDIR}
	cd ${DEPLOYDIR}
	ln -sf u-boot-${type}-${PV}-${PR}.${UBOOT_SUFFIX} u-boot-dtb.img
}
