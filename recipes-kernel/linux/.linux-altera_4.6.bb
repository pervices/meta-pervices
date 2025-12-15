require recipes-kernel/linux/linux-altera.inc
LINUX_VERSION = "4.6"
KERNEL_REPO = "https://github.com/robseb/meta-intelfpga"
KERNEL_PROT = "https"
KBRANCH = "pv/socfpga-4.6"
KBUILD_DEFCONFIG = "crimson_defconfig"
KERNEL_DEVICETREE = "socfpga_arria5_socdk.dtb"
LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

do_deploy:append() {
        ln -sf ${DTB_NAME}.${DTB_EXT} ${DEPLOYDIR}/socfpga.dtb
}

