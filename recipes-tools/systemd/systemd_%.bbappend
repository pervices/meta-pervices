FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SRC_URI += "file://0001-tempfiles-don-t-create-var-tmp-log-directories.patch"
do_install_append() {
	sed -i 's:#Storage=auto:Storage=volatile:g' ${D}${sysconfdir}/systemd/journald.conf
	sed -i 's:#SystemMaxUse=:SystemMaxUse=15M:g' ${D}${sysconfdir}/systemd/journald.conf
	sed -i 's:#SystemKeepFree=:SystemKeepFree=100M:g' ${D}${sysconfdir}/systemd/journald.conf
	sed -i 's:#SystemMaxFileSize=:SystemMaxFileSize=5M:g' ${D}${sysconfdir}/systemd/journald.conf
	echo '#SystemMaxFiles=100' >> ${D}${sysconfdir}/systemd/journald.conf
	echo '#RuntimeMaxUse=15M' >> ${D}${sysconfdir}/systemd/journald.conf
	echo '#RuntimeMaxFiles=100' >> ${D}${sysconfdir}/systemd/journald.conf
	sed -i 's:#DefaultTimeoutStartSec=90:#DefaultTimeoutStartSec=6:g' ${D}${sysconfdir}/systemd/system.conf
	sed -i 's:#DefaultTimeoutStopSec=90:#DefaultTimeoutStopSec=6:g' ${D}${sysconfdir}/systemd/system.conf
	echo 'TimeoutStartSec=8' >> ${D}${sysconfdir}/systemd/system.conf
	echo 'TimeoutStopSec=8' >> ${D}${sysconfdir}/systemd/system.conf
}
