do_install_append() {
	sed -i 's:#PermitRootLogin yes:PermitRootLogin without-password:g' ${D}${sysconfdir}/ssh/sshd_config
	sed -i 's:#Banner none:Banner /etc/crimson/issue.net:g' sshd_config ${D}${sysconfdir}/ssh/sshd_config
	if [ "${@bb.utils.contains('DISTRO_FEATURES', 'pam', 'pam', '', d)}" = "pam" ]; then
		chmod ugo-x ${D}${sysconfdir}/pam.d/sshd
	fi
}
