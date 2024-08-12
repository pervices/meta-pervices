FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
SRC_URI += "file://interfaces-crimson \
            file://interfaces-cyan \
            file://networking \
           "
do_install_append () {
	install -m 0755 ${WORKDIR}/networking ${D}${sysconfdir}/init.d/networking
}

do_install_append_crimson () {
	install -m 0644 ${WORKDIR}/interfaces-crimson ${D}${sysconfdir}/network/interfaces
}

do_install_append_cyan () {
	install -m 0644 ${WORKDIR}/interfaces-cyan ${D}${sysconfdir}/network/interfaces
}

do_install_append_chestnut () {
	install -m 0644 ${WORKDIR}/interfaces-cyan ${D}${sysconfdir}/network/interfaces
}
