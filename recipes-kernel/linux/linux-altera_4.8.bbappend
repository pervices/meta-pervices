FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-4.8:"
SRC_URI += "file://defconfig"

do_configure_append() {
            cp ${WORKDIR}/defconfig ${WORKDIR}/linux-arria5-standard-build/source/.config
}
