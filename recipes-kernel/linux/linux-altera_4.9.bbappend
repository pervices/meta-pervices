FILESEXTRAPATHS_prepend := "${THISDIR}/linux-altera-4.9:"
SRC_URI += "file://defconfig"

do_configure_append() {
            cp ${WORKDIR}/defconfig ${WORKDIR}/git/.config
}
