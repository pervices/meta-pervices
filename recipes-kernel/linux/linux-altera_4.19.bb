LINUX_VERSION = "4.19"
LINUX_VERSION_SUFFIX = ""

KERNEL_REPO = "git://github.com/pervices/linux-socfpga.git"
KERNEL_PROT = "https"
KBRANCH = "pv/socfpga-4.19"
#KBUILD_DEFCONFIG_stratix10 = "s10_devkit_defconfig"
KBUILD_DEFCONFIG = "s10_devkit_defconfig"
#KERNEL_DEVICETREE_stratix10 = "altera/socfpga_stratix10_ovl1.dtb altera/socfpga_stratix10_socdk.dtb"
KERNEL_DEVICETREE = "altera/socfpga_stratix10_ovl1.dtb altera/socfpga_stratix10_socdk.dtb"
KERNEL_MODULE_AUTOLOAD += "lm87 ads1015 max31790 max6639"

#require ../../../meta-altera/recipes-kernel/linux/linux-altera.inc
require recipes-kernel/linux/linux-altera.inc

LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"
#FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${LINUX_VERSION}:"
#SRC_URI += "file://defconfig"

#do_configure_append() {
#            cp ${WORKDIR}/defconfig ${WORKDIR}/linux-stratix10-standard-build/.config
#}

do_deploy_append() {
	ln -sf ${DTB_NAME}.${DTB_EXT} ${DEPLOYDIR}/${DTB_BASE_NAME}.${DTB_EXT}
}

