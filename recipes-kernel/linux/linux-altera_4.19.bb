LINUX_VERSION = "4.19"
LINUX_VERSION_SUFFIX = ""
SRCREV = "e3b47dd63c0f7f7404bb6a368c1d671e0d199745"

#KBRANCH = "master"
#SRCREV = ""

KBUILD_DEFCONFIG_stratix10 ?= "defconfig"

require ../../../meta-altera/recipes-kernel/linux/linux-altera.inc

LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${LINUX_VERSION}:"
SRC_URI += "file://0001-Added-s10_devkit_defconfig-from-4.9.78-ltsi-branch.patch \
            file://0002-Fix-DTS-files-for-stratix-10.patch \
	    file://0003-Final-fixes-for-the-DTS-working-condition.patch \
	    file://0004-Remove-unnecessary-devices-from-I2C-DTS.patch \
            file://0005-S10-DTS-w.i.p-updating-Stratix-10-DTS.patch \
	    file://0006-Updated-the-DTS-with-latest-changes.patch \
            file://0007-Remove-RGMII-RX-pad-skew-in-Ethernet-PHY.patch \
            file://0008-Delay-RGMII-RX-data-by-1.38-ns-relative-to-clock.patch \
            file://0009-DTS-fix-interrupt-for-EMAC0-1-Fix-RGMII-pad-skew.patch"

#do_configure_append() {
#            cp ${WORKDIR}/defconfig ${WORKDIR}/linux-stratix10-standard-build/.config
#}

