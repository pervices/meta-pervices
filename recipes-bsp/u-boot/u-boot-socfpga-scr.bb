SUMMARY = "U-boot boot scripts for Per Vices SDR platforms"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

DEPENDS = "u-boot-mkimage-native dtc-native"

inherit deploy nopackages
PACKAGE_ARCH = "${MACHINE_ARCH}"

# SRC_URI:cyan = "file://uboot.txt file://uboot_script.its"
# SRC_URI:chestnut = "file://uboot.txt file://uboot_script.its"


do_configure[noexec] = "1"
do_install[noexec] = "1"

# do_compile:cyan() {
# 	mkimage -f "${WORKDIR}/uboot_script.its" ${WORKDIR}/boot.scr.uimg
# }

# do_compile:chestnut() {
# 	mkimage -f "${WORKDIR}/uboot_script.its" ${WORKDIR}/boot.scr.uimg
# }

do_deploy() {
    install -d ${DEPLOYDIR}
    # if [[ "${MACHINE}" == "cyan" ]] || [[ "${MACHINE}" == "chestnut" ]]; then
    #     install -m 0755 ${WORKDIR}/uboot.txt ${DEPLOYDIR}/u-boot.txt
    #     install -m 0644 ${WORKDIR}/boot.scr.uimg ${DEPLOYDIR}/boot.scr.uimg
    # else
    #     :
    # fi
}

addtask do_deploy after do_compile before do_build
