LINUX_VERSION = "4.6"

KERNEL_REPO = "git://github.com/pervices/linux-socfpga.git"
KERNEL_PROT = "https"
KBRANCH = "pv/socfpga-4.6"
KBUILD_DEFCONFIG = "socfpga_defconfig"
KERNEL_DEVICETREE = "socfpga_arria5_socdk.dtb"

require recipes-kernel/linux/linux-altera.inc

LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${LINUX_VERSION}:"
#SRC_URI +=

#do_configure_append() {
#            cp ${WORKDIR}/defconfig ${WORKDIR}/linux-stratix10-standard-build/.config
#}

do_deploy_append() {
	#echo ${DTB_NAME} > var
	#echo ${DTB_EXT} >> var
	#echo ${DEPLOYDIR} >> var
	#echo ${DTB_BASE_NAME} >> var
	#ln -sf ${DTB_NAME}.${DTB_EXT} ${DEPLOYDIR}/${DTB_BASE_NAME}.${DTB_EXT}
	ln -sf ${DTB_NAME}.${DTB_EXT} ${DEPLOYDIR}/socfpga.${DTB_EXT}
}

