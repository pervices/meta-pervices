do_install_append() {
	sed -i 's:d root root 0755 /var/volatile/log none:#d root root 0755 /var/volatile/log none:g' ${D}${sysconfdir}/default/volatiles/00_core
	sed -i 's:d root root 1777 /var/volatile/tmp none:#d root root 1777 /var/volatile/tmp none:g' ${D}${sysconfdir}/default/volatiles/00_core
	sed -i 's:l root root 0755 /var/log /var/volatile/log:#l root root 0755 /var/log /var/volatile/log:g' ${D}${sysconfdir}/default/volatiles/00_core
	sed -i 's:l root root 1777 /var/tmp /var/volatile/tmp:#l root root 1777 /var/tmp /var/volatile/tmp:g' ${D}${sysconfdir}/default/volatiles/00_core
	echo "d root root 0755 /var/log none" >> ${D}${sysconfdir}/default/volatiles/00_core
	echo "d root root 1777 /var/tmp none" >> ${D}${sysconfdir}/default/volatiles/00_core
}
