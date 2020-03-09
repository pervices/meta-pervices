FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
SRC_URI += "file://hostname \
            file://default.bashrc \
            file://default.bash_profile \
            file://.ssh/authorized_keys \
            file://default.minirc.dfl \
            "
do_install_append () {
	install -m 0644 ${WORKDIR}/hostname ${D}${sysconfdir}/hostname
	rm ${D}${localstatedir}/log
	rm ${D}${localstatedir}/tmp
	install -d -m 1777 ${D}${localstatedir}/tmp
	install -d -m 0755 ${D}${localstatedir}/log
	touch ${D}${localstatedir}/log/lastlog
	chgrp 43 ${D}${localstatedir}/log/lastlog
	chmod 0644 ${D}${localstatedir}/log/lastlog
	install -d -m 0700 ${D}${ROOT_HOME}/.ssh
	install -p -m 0640 default.bashrc ${D}${ROOT_HOME}/.bashrc
	install -p -m 0640 default.bash_profile ${D}${ROOT_HOME}/.bash_profile
	install -p -m 0600 .ssh/authorized_keys ${D}${ROOT_HOME}/.ssh/authorized_keys
	install -p -m 0640 default.minirc.dfl ${D}${ROOT_HOME}/.minirc.dfl
}
