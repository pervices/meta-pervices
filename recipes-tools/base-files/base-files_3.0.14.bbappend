FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
SRC_URI:append = "file://hostname-crimson \
            file://hostname-cyan \
            file://hostname-chestnut \
            file://fstab-ro-crimson \
           "
do_install:append () {
	#install -m 0644 ${WORKDIR}/fstab-ro-crimson ${D}${sysconfdir}/fstab-ro-crimson
	rm ${D}${localstatedir}/log
	rm ${D}${localstatedir}/tmp
	install -d -m 1777 ${D}${localstatedir}/tmp
	install -d -m 0755 ${D}${localstatedir}/log
	touch ${D}${localstatedir}/log/lastlog
	chgrp 43 ${D}${localstatedir}/log/lastlog
	chmod 0644 ${D}${localstatedir}/log/lastlog
}

do_install:append:crimson () {
	install -m 0644 ${WORKDIR}/hostname-crimson ${D}${sysconfdir}/hostname
}

do_install:append:cyan () {
	install -m 0644 ${WORKDIR}/hostname-cyan ${D}${sysconfdir}/hostname
}

do_install:append:chestnut () {
	install -m 0644 ${WORKDIR}/hostname-chestnut ${D}${sysconfdir}/hostname
}
