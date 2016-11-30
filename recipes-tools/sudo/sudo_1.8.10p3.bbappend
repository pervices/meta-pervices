do_install_append() {
	sed -i '/root ALL=(ALL) ALL/ a dev0 ALL=(ALL) ALL' ${D}${sysconfdir}/sudoers
	sed -i '/dev0 ALL=(ALL) ALL/ a client ALL=(ALL) ALL' ${D}${sysconfdir}/sudoers
}
