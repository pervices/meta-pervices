FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
SRC_URI += "file://hostname"
do_install_append () {
	install -m 0644 ${WORKDIR}/hostname ${D}${sysconfdir}/hostname
	rm ${D}${localstatedir}/log
	rm ${D}${localstatedir}/tmp
}
