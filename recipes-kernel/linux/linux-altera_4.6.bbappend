FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
SRC_URI += "file://defconfig"
KERNEL_BRANCH = "master"
KERNEL_TAG = "2dcd0af568b0cf583645c8a317dd12e344b1c72a"

do_configure_append() {
            cp ${WORKDIR}/defconfig ${WORKDIR}/git/.config
}
