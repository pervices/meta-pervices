require recipes-kernel/linux/linux-altera.inc
LINUX_VERSION = "4.19"
KERNEL_REPO = "https://github.com/robseb/meta-intelfpga"
KERNEL_PROT = "https"
KBRANCH = "pv/socfpga-4.19"
KBUILD_DEFCONFIG:cyan = "s10_devkit_defconfig"
KBUILD_DEFCONFIG:chestnut = "s10_devkit_defconfig"
KBUILD_DEFCONFIG:crimson = "socfpga_defconfig"
KERNEL_DEVICETREE:cyan = "altera/socfpga_stratix10_ovl1.dtb altera/socfpga_stratix10_socdk.dtb"
KERNEL_DEVICETREE:chestnut = "altera/socfpga_stratix10_ovl1.dtb altera/socfpga_stratix10_socdk.dtb"
KERNEL_DEVICETREE:crimson = "socfpga_arria5_socdk.dtb"
KERNEL_MODULE_AUTOLOAD:cyan += "lm87 ads1015 max31790 max6639 at24"
KERNEL_MODULE_AUTOLOAD:chestnut += "lm87 ads1015 max31790 max6639 at24"
KERNEL_MODULE_AUTOLOAD:crimson += "ads1015 max31790"
LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

#This is to fix a bug when kernel won't be compiled using cyan's configuration during first compile
do_configure:append:cyan() {
    cp ${S}/arch/arm64/configs/s10_devkit_defconfig ${WORKDIR}/linux-${MACHINE}-standard-build/.config
}

#This is to fix a bug when kernel won't be compiled using chestnut's configuration during first compile
do_configure:append:chestnut() {
    cp ${S}/arch/arm64/configs/s10_devkit_defconfig ${WORKDIR}/linux-${MACHINE}-standard-build/.config
}

#This is to fix a bug when kernel won't be compiled using crimson's configuration during first compile
do_configure:append:crimson() {
    cp ${S}/arch/arm/configs/socfpga_defconfig ${WORKDIR}/linux-${MACHINE}-standard-build/.config
}

do_deploy:append() {
    ln -sf ${DTB_NAME}.${DTB_EXT} ${DEPLOYDIR}/socfpga_stratix10_socdk.dtb
    ln -sf ${DTB_NAME}.${DTB_EXT} ${DEPLOYDIR}/socfpga.dtb
}

