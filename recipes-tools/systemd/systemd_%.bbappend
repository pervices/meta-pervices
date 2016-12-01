FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SRC_URI += "file://0001-tempfiles-don-t-create-var-tmp-log-directories.patch"
do_install_append() {
	sed -i 's:#Storage=auto:Storage=persistent:g' ${D}${sysconfdir}/systemd/journald.conf
}
