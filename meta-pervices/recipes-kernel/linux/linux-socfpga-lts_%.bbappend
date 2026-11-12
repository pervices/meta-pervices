# Append custom device tree source include files

FILESEXTRAPATHS:prepend := "${THISDIR}/linux-socfpga-lts:"
FILESEXTRAPATHS:prepend := "${THISDIR}/linux-socfpga-lts/configs:"

DEPENDS = "u-boot-mkimage-native dtc-native"

# Customized device tree
SRC_URI:append:chestnut = " file://fit_kernel_stratix10_htile.its file://stratix10.patch"
SRC_URI:append:cyan = " file://fit_kernel_stratix10_htile.its file://stratix10.patch"

# Enable kernel modules and config options
SRC_URI:append:chestnut = " file://rsu.scc file://pv.scc"
SRC_URI:append:cyan = " file://rsu.scc file://pv.scc"

inherit deploy

LINUXDEPLOYDIR = "${WORKDIR}/deploy-${PN}"
DTBDEPLOYDIR = "${DEPLOY_DIR_IMAGE}"

INSANE_SKIP:${PN}-src = "buildpaths"

COMPATIBLE_MACHINE = "(chestnut|cyan|stratix10_htile)"

do_deploy:append() {
    if [[ "${MACHINE}" == "chestnut" ]] || [[ "${MACHINE}" == "cyan" ]] || [[ "${MACHINE}" == "stratix10_htile" ]]; then
        soc_name="stratix10_htile"
    fi
    # linux.dtb
    cp ${LINUXDEPLOYDIR}/${LINUX_DTB_FILE} ${B}
    cp ${WORKDIR}/fit_kernel_${soc_name}.its ${B}

    # Image
    cp ${LINUXDEPLOYDIR}/Image ${B}
    # Compress Image to lzma format
    xz -f --format=lzma ${B}/Image

    # Generate kernel.itb
    mkimage -f ${B}/fit_kernel_${soc_name}.its ${B}/kernel.itb

    # Deploy kernel.its, kernel.itb and Image.lzma
    install -m 744 ${B}/fit_kernel_${soc_name}.its ${DEPLOYDIR}
    install -m 744 ${B}/kernel.itb ${DEPLOYDIR}
    install -m 744 ${B}/Image.lzma ${DEPLOYDIR}
}