UBOOT_REPO = "git://github.com/pervices/u-boot-socfpga.git"
UBOOT_BRANCH = "socfpga_${PV}"
UBOOT_PROT = "https"
UBOOT_TAG = "${AUTOREV}"

SRC_URI = "${UBOOT_REPO};protocol=${UBOOT_PROT};branch=${UBOOT_BRANCH}"
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}_${PV}:"
SRC_URI += "file://Enable-nand-command-by-default.patch \
            file://u-boot.cmd \
            file://fix-build-error-under-gcc6.patch \
           "

#SRCREV = "socfpga_${PV}"
DEPENDS = "u-boot-mkimage-native"
FILES_${PN} += "/boot"
UBOOT_SUFFIX ?= "img"
do_compile_prepend() {
	echo ${D} > var
	echo ${DEPLOYDIR} >> var
}
do_compile_append() {
        uboot-mkimage -A arm -O linux -T script -C none -a 0 -e 0 -n "Crimson U-BOOT Script" -d ${WORKDIR}/u-boot.cmd u-boot.scr
}
do_install_append() {
        install -d ${D}/boot/u-boot-scripts
        install -m 0644 u-boot.scr ${D}/boot/u-boot-scripts
	install -m 0644 u-boot.scr ${DEPLOYDIR}
}
do_deploy_append () {
        cd ${DEPLOYDIR}
        ln -sf u-boot.img u-boot-arria5.img
}

