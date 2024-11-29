#FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SRC_URI = "git://github.com/systemd/systemd.git;protocol=https"
do_install_append() {
	sed -i 's:#Storage=auto:Storage=volatile:g' ${D}${sysconfdir}/systemd/journald.conf
	sed -i 's:#RateLimitIntervalSec:RateLimitIntervalSec:g' ${D}${sysconfdir}/systemd/journald.conf
	sed -i 's:#RateLimitBurst=1000:RateLimitBurst=10000:g' ${D}${sysconfdir}/systemd/journald.conf
	sed -i 's:#SystemMaxUse=:SystemMaxUse=25M:g' ${D}${sysconfdir}/systemd/journald.conf
	sed -i 's:#SystemKeepFree=:SystemKeepFree=1G:g' ${D}${sysconfdir}/systemd/journald.conf
	sed -i 's:#SystemMaxFileSize=:SystemMaxFileSize=5M:g' ${D}${sysconfdir}/systemd/journald.conf
	sed -i 's:#SystemMaxFiles=100:SystemMaxFiles=100:g' ${D}${sysconfdir}/systemd/journald.conf
	sed -i 's:RuntimeMaxUse=64M:RuntimeMaxUse=50M:g' ${D}${sysconfdir}/systemd/journald.conf
	sed -i 's:#RuntimeKeepFree=:RuntimeKeepFree=1G:g' ${D}${sysconfdir}/systemd/journald.conf
	sed -i 's:#RuntimeMaxFileSize=:RuntimeMaxFileSize=5M:g' ${D}${sysconfdir}/systemd/journald.conf
	sed -i 's:#RuntimeMaxFiles=100:RuntimeMaxFiles=100:g' ${D}${sysconfdir}/systemd/journald.conf
	sed -i 's:#DefaultTimeoutStartSec=90:#DefaultTimeoutStartSec=6:g' ${D}${sysconfdir}/systemd/system.conf
	sed -i 's:#DefaultTimeoutStopSec=90:#DefaultTimeoutStopSec=6:g' ${D}${sysconfdir}/systemd/system.conf
	echo 'TimeoutStartSec=8' >> ${D}${sysconfdir}/systemd/system.conf
	echo 'TimeoutStopSec=8' >> ${D}${sysconfdir}/systemd/system.conf
}
