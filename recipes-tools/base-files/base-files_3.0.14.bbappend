FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}-${PV}:"
SRC_URI += "file://hostname-crimson \
            file://hostname-cyan \
            file://hostname-chestnut \
            file://fstab-ro-crimson \
           "

do_install:append:crimson () {
	install -m 0644 ${WORKDIR}/hostname-crimson ${D}${sysconfdir}/hostname
}

do_install:append:cyan () {
	install -m 0644 ${WORKDIR}/hostname-cyan ${D}${sysconfdir}/hostname
}

do_install:append:chestnut () {
	install -m 0644 ${WORKDIR}/hostname-chestnut ${D}${sysconfdir}/hostname
}

