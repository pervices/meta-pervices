# Append custom device tree source include files

FILESEXTRAPATHS:prepend := "${THISDIR}/linux-socfpga-lts:"

DEPENDS = "u-boot-mkimage-native dtc-native"

SRC_URI:append:stratix10_htile = " file://fit_kernel_stratix10_htile.its file://stratix10.patch"

inherit deploy

LINUXDEPLOYDIR = "${WORKDIR}/deploy-${PN}"
DTBDEPLOYDIR = "${DEPLOY_DIR_IMAGE}"

INSANE_SKIP:${PN}-src = "buildpaths"

do_deploy:append() {
    # linux.dtb
    cp ${LINUXDEPLOYDIR}/${LINUX_DTB_FILE} ${B}
    cp ${WORKDIR}/fit_kernel_${MACHINE}.its ${B}

    # Image
    cp ${LINUXDEPLOYDIR}/Image ${B}
    # Compress Image to lzma format
    xz -f --format=lzma ${B}/Image

    # Generate kernel.itb
    mkimage -f ${B}/fit_kernel_stratix10_htile.its ${B}/kernel.itb

    # Deploy kernel.its, kernel.itb and Image.lzma
    install -m 744 ${B}/fit_kernel_stratix10_htile.its ${DEPLOYDIR}
    install -m 744 ${B}/kernel.itb ${DEPLOYDIR}
    install -m 744 ${B}/Image.lzma ${DEPLOYDIR}
}