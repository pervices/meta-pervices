FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
SRC_URI += "file://defconfig"
KERNEL_TAG += "v4.6"

do_configure_append() {
            cp ${WORKDIR}/defconfig ${WORKDIR}/git/.config
}
