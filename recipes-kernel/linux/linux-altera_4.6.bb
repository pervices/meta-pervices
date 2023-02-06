require recipes-kernel/linux/linux-altera.inc
LINUX_VERSION = "4.6"
KERNEL_REPO = "git://github.com/pervices/linux-socfpga.git"
KERNEL_PROT = "https"
KBRANCH = "pv/socfpga-4.6"
KBUILD_DEFCONFIG = "socfpga_defconfig"
KERNEL_DEVICETREE = "socfpga_arria5_socdk.dtb"
LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

do_deploy_append() {
        ln -sf ${DTB_NAME}.${DTB_EXT} ${DEPLOYDIR}/${DTB_BASE_NAME}.${DTB_EXT}
        ln -sf ${DTB_NAME}.${DTB_EXT} ${DEPLOYDIR}/socfpga.${DTB_EXT}
}

