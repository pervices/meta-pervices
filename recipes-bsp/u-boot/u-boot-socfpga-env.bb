SUMMARY = "U-boot boot environment for Per Vices SDR platforms"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

FILESEXTRAPATHS:prepend := "${THISDIR}/env:"

DEPENDS = "u-boot-mkenvimage-native"

PACKAGES:remove = "u-boot-socfpga-env"

inherit deploy nopackages
PACKAGE_ARCH = "${MACHINE_ARCH}"

# Supported boot flow uses stratix10-socdk-atf_u-boot-env.txt, stratix10-socdk_u-boot-env.txt is for the legacy flow.
SRC_URI:cyan = "\
    ${@bb.utils.contains("UBOOT_CONFIG", "stratix10-socdk", "file://stratix10-socdk_u-boot-env.txt", "", d)} \
    ${@bb.utils.contains("UBOOT_CONFIG", "stratix10-socdk-atf", "file://stratix10-socdk-atf_u-boot-env.txt", "", d)} \
    "

SRC_URI:chestnut = "\
    ${@bb.utils.contains("UBOOT_CONFIG", "stratix10-socdk", "file://stratix10-socdk_u-boot-env.txt", "", d)} \
    ${@bb.utils.contains("UBOOT_CONFIG", "stratix10-socdk-atf", "file://stratix10-socdk-atf_u-boot-env.txt", "", d)} \
    "

do_configure[noexec] = "1"
do_install[noexec] = "1"

do_compile() {
    mkenvimage -s 0x2000 -o "${WORKDIR}/uboot.env" ${WORKDIR}/${UBOOT_CONFIG}_u-boot-env.txt
}

do_deploy() {
    install -d ${DEPLOYDIR}
    install -m 0755 ${WORKDIR}/${UBOOT_CONFIG}_u-boot-env.txt ${DEPLOYDIR}/u-boot-env.txt
    install -m 0644 ${WORKDIR}/uboot.env ${DEPLOYDIR}/u-boot-socfpga-${MACHINE}-${PV}-${PR}.env
    ln -sf u-boot-socfpga-${MACHINE}-${PV}-${PR}.env ${DEPLOYDIR}/uboot.env
}

addtask do_deploy after do_compile before do_build

