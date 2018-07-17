LINUX_VERSION = "4.17"
LINUX_VERSION_SUFFIX = ""
SRCREV = "103d053ac5948f43806a85c2b991270fa749ec11"

#KBRANCH = "master"
#SRCREV = ""

KBUILD_DEFCONFIG_stratix10 ?= "defconfig"

require ../../../meta-altera/recipes-kernel/linux/linux-altera.inc

LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${LINUX_VERSION}:"
SRC_URI += "file://defconfig"

do_configure_append() {
            cp ${WORKDIR}/defconfig ${WORKDIR}/linux-stratix10-standard-build/.config
}

