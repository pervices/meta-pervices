require recipes-kernel/linux/linux-altera.inc
LINUX_VERSION = "4.19"
KERNEL_REPO = "git://github.com/pervices/linux-socfpga.git"
KERNEL_PROT = "https"
KBRANCH = "pv/socfpga-4.19"
KBUILD_DEFCONFIG_cyan = "s10_devkit_defconfig"
KBUILD_DEFCONFIG_chestnut = "s10_devkit_defconfig"
KBUILD_DEFCONFIG_crimson = "socfpga_defconfig"
KERNEL_DEVICETREE_cyan = "altera/socfpga_stratix10_ovl1.dtb altera/socfpga_stratix10_socdk.dtb"
KERNEL_DEVICETREE_chestnut = "altera/socfpga_stratix10_ovl1.dtb altera/socfpga_stratix10_socdk.dtb"
KERNEL_DEVICETREE_crimson = "socfpga_arria5_socdk.dtb"
KERNEL_MODULE_AUTOLOAD_cyan += "lm87 ads1015 max31790 max6639 at24"
KERNEL_MODULE_AUTOLOAD_chestnut += "lm87 ads1015 max31790 max6639 at24"
KERNEL_MODULE_AUTOLOAD_crimson += "ads1015 max31790"
LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

#This is to fix a bug when kernel won't be compiled using cyan's configuration during first compile
do_configure_append_cyan() {
    cp ${S}/arch/arm64/configs/s10_devkit_defconfig ${WORKDIR}/linux-${MACHINE}-standard-build/.config
}

#This is to fix a bug when kernel won't be compiled using chestnut's configuration during first compile
do_configure_append_chestnut() {
    cp ${S}/arch/arm64/configs/s10_devkit_defconfig ${WORKDIR}/linux-${MACHINE}-standard-build/.config
}

#This is to fix a bug when kernel won't be compiled using crimson's configuration during first compile
do_configure_append_crimson() {
    cp ${S}/arch/arm/configs/socfpga_defconfig ${WORKDIR}/linux-${MACHINE}-standard-build/.config
}

do_deploy_append() {
    ln -sf ${DTB_NAME}.${DTB_EXT} ${DEPLOYDIR}/socfpga_stratix10_socdk.dtb
    ln -sf ${DTB_NAME}.${DTB_EXT} ${DEPLOYDIR}/socfpga.dtb
}

