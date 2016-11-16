FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SRC_URI += "file://base" 
do_install_append() {
	install -m 0644 -D ${WORKDIR}/base ${D}${sysconfdir}/${BPN}/resolv.conf.d/base
	ln -sf /etc/resolvconf/resolv.conf.d/base ${D}${sysconfdir}/resolv.conf
}
