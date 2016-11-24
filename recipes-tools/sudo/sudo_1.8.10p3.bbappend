do_install_append() {
	sed -i '/root ALL=(ALL) ALL/ a dev0 ALL=(ALL) ALL' ${D}${sysconfdir}/sudoers
}
