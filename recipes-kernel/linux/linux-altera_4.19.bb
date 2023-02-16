require recipes-kernel/linux/linux-altera.inc
LINUX_VERSION = "4.19"
KERNEL_REPO = "git://github.com/pervices/linux-socfpga.git"
KERNEL_PROT = "https"
KBRANCH = "pv/socfpga-4.19"
KBUILD_DEFCONFIG_cyan = "s10_devkit_defconfig"
KBUILD_DEFCONFIG_crimson = "crimson_defconfig"
KERNEL_DEVICETREE_cyan = "altera/socfpga_stratix10_socdk.dtb altera/socfpga_stratix10_ovl1.dtb"
KERNEL_DEVICETREE_crimson = "socfpga_arria5_socdk.dtb"
KERNEL_MODULE_AUTOLOAD_cyan += "lm87 ads1015 max31790 max6639"
KERNEL_MODULE_AUTOLOAD_crimson += "ads1015 max31790"
LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

do_deploy_append() {
	ln -sf ${DTB_NAME}.${DTB_EXT} ${DEPLOYDIR}/socfpga_stratix10_socdk.dtb
	ln -sf ${DTB_NAME}.${DTB_EXT} ${DEPLOYDIR}/socfpga.dtb
}

