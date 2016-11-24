do_install_append() {
	sed -i 's/#PermitRootLogin yes/PermitRootLogin no/g' ${D}${sysconfdir}/ssh/sshd_config
}
