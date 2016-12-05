FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SRC_URI += "file://0001-tempfiles-don-t-create-var-tmp-log-directories.patch"
do_install_append() {
	sed -i 's:#Storage=auto:Storage=persistent:g' ${D}${sysconfdir}/systemd/journald.conf
	sed -i 's:#SystemMaxUse=:SystemMaxUse=2G:g' ${D}${sysconfdir}/systemd/journald.conf
	sed -i 's:#SystemKeepFree=:SystemKeepFree=500M:g' ${D}${sysconfdir}/systemd/journald.conf
	sed -i 's:#SystemMaxFileSize=:SystemMaxFileSize=30M:g' ${D}${sysconfdir}/systemd/journald.conf
}
