FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}_${PV}:"
UBOOT_REPO = "git://github.com/pervices/u-boot-socfpga.git"
UBOOT_BRANCH = "socfpga_${PV}"
UBOOT_PROT = "https"
UBOOT_TAG = "${AUTOREV}"
DEPENDS = "u-boot-mkimage-native"
UBOOT_SUFFIX ?= "img"
SRC_URI += "file://Enable-nand-command-by-default.patch \
            file://u-boot.cmd \
           "
FILES_${PN} += "/boot"

addtask post_deploy after do_install do_deploy before do_package

do_compile_append() {
	uboot-mkimage -A arm -O linux -T script -C none -a 0 -e 0 -d ${WORKDIR}/u-boot.cmd u-boot.scr
}
do_install_append() {
	install -d ${D}/boot/u-boot-scripts
	install -m 0644 u-boot.scr ${D}/boot/u-boot-scripts
}
do_post_deploy() {
	cp ${D}/boot/u-boot-scripts/u-boot.scr ${DEPLOYDIR}
	cd ${DEPLOYDIR}
	ln -sf u-boot.img u-boot-arria5.img
	cp ${D}/boot/u-boot-scripts/u-boot.scr ${DEPLOY_DIR_IMAGE}
	cd ${DEPLOY_DIR_IMAGE}
	ln -sf u-boot-${type}-${PV}-${PR}.${UBOOT_SUFFIX} u-boot-dtb.img
}