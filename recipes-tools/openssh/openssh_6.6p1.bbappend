do_install_append() {
	sed -i 's:#PermitRootLogin yes:PermitRootLogin no:g' ${D}${sysconfdir}/ssh/sshd_config
	sed -i 's:#Banner none:Banner /etc/crimson/issue.net:g' sshd_config ${D}${sysconfdir}/ssh/sshd_config
}
