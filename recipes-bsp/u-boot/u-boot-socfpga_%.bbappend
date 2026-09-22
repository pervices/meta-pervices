FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

DEPENDS:append:stratix10_htile = " arm-trusted-firmware bash u-boot-socfpga-scr"

inherit deploy

SRC_URI:append:cyan = " file://stratix10.cfg file://stratix10_uboot_dt.patch"
SRC_URI:append:chestnut = " file://stratix10.cfg file://stratix10_uboot_dt.patch"

do_compile[deptask] = "do_deploy"