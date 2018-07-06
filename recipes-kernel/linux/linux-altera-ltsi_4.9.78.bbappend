FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${LINUX_VERSION}:"
SRC_URI += "file://defconfig"

do_configure_append() {
            cp ${WORKDIR}/defconfig ${WORKDIR}/linux-stratix10-standard-build/.config
}
