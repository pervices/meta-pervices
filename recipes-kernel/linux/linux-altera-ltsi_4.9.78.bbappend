FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
SRC_URI += "file://defconfig"

do_configure_append() {
            cp ${WORKDIR}/defconfig ${WORKDIR}/linux-stratix10-standard-build/.config
}
