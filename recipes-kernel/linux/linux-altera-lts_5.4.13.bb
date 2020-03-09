LINUX_VERSION = "5.4.13"
LINUX_VERSION_SUFFIX = "-lts"

#KERNEL_REPO = "git://github.com/pervices/linux-socfpga.git"
#KERNEL_PROT = "https"
#KBUILD_DEFCONFIG_stratix10 = "s10_devkit_defconfig"
KERNEL_DEVICETREE_stratix10 = "altera/socfpga_stratix10_socdk.dtb"
KBUILD_DEFCONFIG_stratix10 = "defconfig"
#KERNEL_MODULE_AUTOLOAD += "lm87 ads1015 max31790 max6639"
LINUX_VERSION_EXTENSION = "tate"

require ../../../meta-altera/recipes-kernel/linux/linux-altera.inc

LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${LINUX_VERSION}:"
#SRC_URI += "file://0001-clk-socfpga-stratix10-add-additional-clocks-needed-f.patch \
#	    file://0001-clk-socfpga-stratix10-fix-divider-entry-for-the-emac.patch \
#	    file://test.patch \
#	   "

do_configure_append() {
            cp ${WORKDIR}/defconfig ${WORKDIR}/linux-stratix10-standard-build/.config
}

