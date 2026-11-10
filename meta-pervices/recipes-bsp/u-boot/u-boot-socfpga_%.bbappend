FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

DEPENDS:append:chestnut = " arm-trusted-firmware bash u-boot-tools-native"
DEPENDS:append:cyan = " arm-trusted-firmware bash u-boot-tools-native"


inherit deploy

SRC_URI:append:chestnut = " file://stratix10.cfg file://stratix10_uboot.patch"
SRC_URI:append:cyan = " file://stratix10.cfg file://stratix10_uboot.patch"

do_compile[deptask] = "do_deploy"

do_compile:prepend() {
    # Copy bl31.bin (ATF) into source and build directories of u-boot
    for file in ${COMPILE_PREPEND_FILES}; do
        if [ "${file}" = "bl31.bin" ]; then
            cp ${DEPLOY_DIR_IMAGE}/${file} ${B}/${config}/${file}
            cp ${DEPLOY_DIR_IMAGE}/${file} ${S}/${file}
        fi
    done
}

do_deploy:append() {
    config_name="${@d.getVarFlag("UBOOT_CONFIG", "${UBOOT_CONFIG}", True)}"

    cp ${B}/${config_name}/spl/u-boot-spl-dtb.bin ${DEPLOYDIR}/u-boot-spl-dtb.bin
    cp ${B}/${config_name}/spl/u-boot-spl.dtb ${DEPLOYDIR}/u-boot-spl.dtb
    cp ${B}/${config_name}/spl/u-boot-spl.map ${DEPLOYDIR}/u-boot-spl.map
    cp ${B}/${config_name}/spl/u-boot-spl ${DEPLOYDIR}/u-boot-spl
    cp ${B}/${config_name}/u-boot ${DEPLOYDIR}/u-boot
    cp ${B}/${config_name}/u-boot.dtb ${DEPLOYDIR}/u-boot.dtb
}