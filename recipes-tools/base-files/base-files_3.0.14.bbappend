FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
SRC_URI += "file://hostname \
            file://fstab \
           "
do_install_append () {
	install -m 0644 ${WORKDIR}/hostname ${D}${sysconfdir}/hostname
	install -m 0644 ${WORKDIR}/fstab ${D}${sysconfdir}/fstab
	rm ${D}${localstatedir}/log
	rm ${D}${localstatedir}/tmp
	install -d -m 1777 ${D}${localstatedir}/tmp
	install -d -m 0755 ${D}${localstatedir}/log
	touch ${D}${localstatedir}/log/lastlog
	chgrp 43 ${D}${localstatedir}/log/lastlog
	chmod 0644 ${D}${localstatedir}/log/lastlog
}
